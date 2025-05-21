package common.network.handler;

import com.google.gson.Gson;
import common.KioskLoggerFactory;
import common.network.Connection;
import common.network.Serializable;
import common.network.packet.SidedPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;

public class SerializableHandler extends SimpleChannelInboundHandler<SidedPacket> {
    private static final Logger LOGGER = KioskLoggerFactory.getLogger();
    private static final Gson gson = new Gson();

    public SerializableHandler(SidedPacket.Side side) {
        this.side = side;
    }

    private final SidedPacket.Side side;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SidedPacket s) throws Exception {
        if (s.getSide() == this.side) { // 이게 그렇게 쓸모 있는 코드가 아닌 것 같지만 아무튼.
            s.apply();
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
    }
}
