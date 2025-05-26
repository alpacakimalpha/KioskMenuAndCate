package common.network.handler;

import common.KioskLoggerFactory;
import common.network.Connection;
import common.network.handler.factory.PacketListenerFactory;
import common.network.packet.SidedPacket;
import common.util.Container;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class SerializableHandler extends SimpleChannelInboundHandler<SidedPacket> {
    private static final Logger LOGGER = KioskLoggerFactory.getLogger();
    @Nullable
    private volatile PacketListener packetListener;
    public SerializableHandler(SidedPacket.Side side) {
        this.side = side;
    }

    private final SidedPacket.Side side;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SidedPacket s) throws Exception {
        LOGGER.info(s.toString());
        if (s.getSide() == this.side) { // 이게 그렇게 쓸모 있는 코드가 아닌 것 같지만 아무튼.
            s.apply(packetListener);
        } else {
            LOGGER.warn("received a packet that is not a side packet : {}", s);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error(cause.getMessage(), cause);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Container.get(Connection.class).setChannel(ctx.channel());
        this.packetListener = Container.get(PacketListenerFactory.class).getPacketListener(ctx.channel());

    }
}
