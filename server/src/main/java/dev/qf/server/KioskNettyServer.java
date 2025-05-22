package dev.qf.server;

import common.KioskLoggerFactory;
import common.network.Connection;
import common.network.SerializableManager;
import common.network.handler.SerializableDecoder;
import common.network.handler.SerializableEncoder;
import common.network.handler.SerializableHandler;
import common.network.packet.SidedPacket;
import common.util.Container;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;

public class KioskNettyServer implements Connection {
    //TODO MOVE TO CONFIG
    private static final int port = 8192;
    private final MultiThreadIoEventLoopGroup CHANNEL = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
    private final MultiThreadIoEventLoopGroup WORKER = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
    public static final Logger LOGGER = KioskLoggerFactory.getLogger();
    private Channel channel;

    KioskNettyServer() {
        if (Main.INSTANCE != null) {
            throw new IllegalStateException("Server is already started");
        }
        Container.put(Connection.class, this);
    }

    @Override
    public void run() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(CHANNEL, WORKER);
            serverBootstrap.channel(NioServerSocketChannel.class);
            SerializableManager.initialize();

            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
            serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel channel) {
                    try {
                        channel.config().setOption(ChannelOption.TCP_NODELAY, true);
                        channel.config().setOption(ChannelOption.SO_KEEPALIVE, true);
                    } catch (ChannelException ignored) {
                        // NOP
                    }

                    ChannelPipeline pipeline = channel.pipeline()
//                            .addLast("timeout", new ReadTimeoutHandler(30))
                            ;

                    pipeline.addLast("serializable_decoder", new SerializableDecoder());
                    pipeline.addLast("encoder", new SerializableEncoder());
                    pipeline.addLast("handler", new SerializableHandler(SidedPacket.Side.SERVER));
                }


            });
            serverBootstrap.bind(port).syncUninterruptibly().channel().closeFuture().sync();
        } catch (Exception e) {
            LOGGER.error("Failed to start server.");
            LOGGER.error(e.getMessage());
        } finally {
        }
    }

    public void shutdown() {
        LOGGER.info("Shutting down client...");
        if (channel != null && channel.isOpen()) {
            try {
                channel.close().syncUninterruptibly();
            } catch (Exception e) {
                LOGGER.warn("Exception while closing client channel", e);
            }
        }
        if (!CHANNEL.isShuttingDown() && !CHANNEL.isShutdown()) {
            CHANNEL.shutdownGracefully().syncUninterruptibly();
        }
        if (!WORKER.isShuttingDown() && !WORKER.isShutdown()) {
            WORKER.shutdownGracefully().syncUninterruptibly();
        }
        LOGGER.info("Client shutdown complete.");
    }

    @Override
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public Channel getChannel() {
        return this.channel;
    }
}
