package common.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class KioskNetworkHandler {
    private Channel channel;

    public void sendImmediately(Serializable<?> serializable) {
        ChannelFuture future = channel.writeAndFlush(serializable);

        future.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
}
