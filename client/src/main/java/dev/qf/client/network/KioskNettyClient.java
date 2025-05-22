package dev.qf.client.network;

import common.KioskLoggerFactory;
import common.network.Connection;
import common.network.SerializableManager;
import common.network.handler.SerializableDecoder;
import common.network.handler.SerializableEncoder;
import common.network.handler.SerializableHandler;
import common.network.packet.SidedPacket;
import common.util.Container;
import dev.qf.client.Main;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;

public class KioskNettyClient implements Connection {
    private static final int port = 8192;
    private final MultiThreadIoEventLoopGroup CHANNEL = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

    private Bootstrap bootstrap;
    public static final Logger LOGGER = KioskLoggerFactory.getLogger();
    private Channel channel;

    public KioskNettyClient() {
        if (Main.INSTANCE != null) {
            throw new IllegalStateException("KioskNettyClient already initialized");
        }
        Container.put(Connection.class, this);
    }

    @Override
    public void run() {

            bootstrap = new Bootstrap();
            bootstrap.group(CHANNEL);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            SerializableManager.initialize();

            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    try {
                        ch.config().setOption(ChannelOption.TCP_NODELAY, true);

                    } catch (ChannelException ignored) {
                        // NOP
                    }

                    ChannelPipeline pipeline = ch.pipeline()
                            .addLast("timeout", new ReadTimeoutHandler(30));

                    pipeline.addLast("decoder", new SerializableDecoder());
//                    pipeline.addLast("logger", new LoggingHandler(LogLevel.DEBUG));
                    pipeline.addLast("encoder", new SerializableEncoder());
                    pipeline.addLast("handler", new SerializableHandler(SidedPacket.Side.CLIENT));

//                    pipeline.addLast("encoder", new StringEncoder());
//                    pipeline.addLast("handler", new ChannelInboundHandlerAdapter() {
//                        @Override
//                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                            super.channelRead(ctx, msg);
//                            LOGGER.info(msg.toString());
//                        }
//                    });

                }
            });

            this.setChannel(bootstrap.connect("localhost", port).syncUninterruptibly().channel());


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

    public ChannelFuture connect(String host, int port) {
        return bootstrap.connect(host, port).syncUninterruptibly();
    }

}
