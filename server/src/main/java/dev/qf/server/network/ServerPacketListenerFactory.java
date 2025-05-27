package dev.qf.server.network;

import common.network.handler.PacketListener;
import common.network.handler.factory.PacketListenerFactory;
import common.network.handler.server.ServerPacketListener;
import io.netty.channel.Channel;

public class ServerPacketListenerFactory implements PacketListenerFactory {
    @Override
    public PacketListener getPacketListener(Channel channel) {
        return new ServerPacketListenerImpl(channel);
    }
}
