package dev.qf.server.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import common.KioskLoggerFactory;
import dev.qf.server.KioskNettyServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JsonHandler extends SimpleChannelInboundHandler<String> {
    private static final Gson gson = new Gson();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        KioskNettyServer.LOGGER.info(s);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
