package dev.qf.server;

import com.google.common.base.Suppliers;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import common.KioskLoggerFactory;
import common.network.SerializableManager;
import common.network.handler.SerializableDecoder;
import common.network.handler.SerializableEncoder;
import common.network.handler.SerializableHandler;
import common.network.packet.SidedPacket;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class KioskNettyServer {
    //TODO MOVE TO CONFIG
    private static final int port = 8192;
    private static final Supplier<MultiThreadIoEventLoopGroup> CHANNEL = Suppliers.memoize(() -> new MultiThreadIoEventLoopGroup(0, new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Netty IO #%d").build(), NioIoHandler.newFactory()));
    public static final Logger LOGGER = KioskLoggerFactory.getLogger();

    public static void run() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(CHANNEL.get());
            serverBootstrap.channel(NioServerSocketChannel.class);
            SerializableManager.initialize();

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) {
                    try {
                        channel.config().setOption(ChannelOption.TCP_NODELAY, true);
                        channel.config().setOption(ChannelOption.SO_KEEPALIVE, true);
                    } catch (ChannelException ignored) {
                        // NOP
                    }

                    ChannelPipeline pipeline = channel.pipeline()
                            .addLast("timeout", new ReadTimeoutHandler(30))
                            ;

//                    pipeline.addLast(new LineBasedFrameDecoder(1024));
                    pipeline.addLast("logger", new LoggingHandler(LogLevel.INFO));
                    pipeline.addLast("encoder", new SerializableEncoder());
                    pipeline.addLast("handler", new SerializableHandler(SidedPacket.Side.SERVER));
                    pipeline.addLast("serializable_decoder", new SerializableDecoder());

                }


            });
            serverBootstrap.bind(port).syncUninterruptibly().channel().closeFuture().sync();
        } catch (Exception e) {
            LOGGER.error("Failed to start server.");
            LOGGER.error(e.getMessage());
        }
    }


}
