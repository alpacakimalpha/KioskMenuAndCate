package dev.qf.client.network;

import common.network.handler.client.ClientPacketListener;
import common.network.packet.SidedPacket;
import common.network.packet.UpdateDataPacket;
import io.netty.channel.Channel;

public class ClientPacketListenerImpl implements ClientPacketListener {
    private Channel channel;

    public ClientPacketListenerImpl(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void onReceivedData(UpdateDataPacket.ResponseDataS2CPacket packet) {

    }

    @Override
    public SidedPacket.Side getSide() {
        return SidedPacket.Side.CLIENT;
    }

    @Override
    public Channel getChannel() {
        return this.channel;
    }
}
