package common.network.handler.factory;

import common.network.handler.PacketListener;
import io.netty.channel.Channel;

public interface PacketListenerFactory {
    PacketListener getPacketListener(Channel channel);
}
