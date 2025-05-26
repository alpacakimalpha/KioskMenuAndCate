package dev.qf.server.network;

import common.KioskLoggerFactory;
import common.network.handler.server.ServerPacketListener;
import common.network.packet.HandShakeC2SInfo;
import common.network.packet.SidedPacket;
import common.network.packet.UpdateDataPacket;
import io.netty.channel.Channel;
import org.slf4j.Logger;

public class ServerPacketListenerImpl implements ServerPacketListener {
    private Channel channel;
    private final Logger logger = KioskLoggerFactory.getLogger();

    public ServerPacketListenerImpl(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void onHandShake(HandShakeC2SInfo packet) {
        logger.info("HandShake received");
    }

    @Override
    public void onRequestData(UpdateDataPacket.RequestDataC2SPacket packet) {

    }

    @Override
    public SidedPacket.Side getSide() {
        return SidedPacket.Side.SERVER;
    }

    @Override
    public Channel getChannel() {
        return this.channel;
    }
}
