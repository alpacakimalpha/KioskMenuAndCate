package dev.qf.server;

import com.google.common.base.Suppliers;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import common.KioskLoggerFactory;
import dev.qf.server.handler.JsonHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
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

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) {
                    try {
                        channel.config().setOption(ChannelOption.TCP_NODELAY, true);
                    } catch (ChannelException ignored) {
                        // NOP
                    }

                    ChannelPipeline pipeline = channel.pipeline()
//                            .addLast("timeout", new ReadTimeoutHandler(30))
                            ;

//                    pipeline.addLast(new LineBasedFrameDecoder(1024));
//                    pipeline.addLast("logger", new LoggingHandler(LogLevel.INFO));
                    pipeline.addLast("decoder", new JsonObjectDecoder());
                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast("encoder", new StringEncoder());
                    pipeline.addLast("handler", new JsonHandler());

                }


            });
            serverBootstrap.bind(port).syncUninterruptibly().channel().closeFuture().sync();
        } catch (Exception e) {
            LOGGER.error("Failed to start server.");
            LOGGER.error(e.getMessage());
        }
    }
}
