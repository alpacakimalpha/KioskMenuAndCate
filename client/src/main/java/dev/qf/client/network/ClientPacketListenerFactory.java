package dev.qf.client.network;

import common.network.handler.PacketListener;
import common.network.handler.client.ClientPacketListener;
import common.network.handler.factory.PacketListenerFactory;
import io.netty.channel.Channel;

public class ClientPacketListenerFactory implements PacketListenerFactory {
    @Override
    public PacketListener getPacketListener(Channel channel) {
        return new ClientPacketListenerImpl(channel);
    }
}
