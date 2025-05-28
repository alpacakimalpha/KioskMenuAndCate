package dev.qf.server.network;

import common.KioskLoggerFactory;
import common.network.handler.SerializableHandler;
import common.network.handler.listener.ServerPacketListener;
import common.network.packet.HandShakeC2SInfo;
import common.network.packet.SidedPacket;
import common.network.packet.UpdateDataPacket;
import org.slf4j.Logger;

public class ServerPacketListenerImpl implements ServerPacketListener {
    private SerializableHandler handler;
    private final Logger logger = KioskLoggerFactory.getLogger();

    public ServerPacketListenerImpl(SerializableHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onHandShake(HandShakeC2SInfo packet) {
        handler.setId(packet.id());
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
    public SerializableHandler getHandler() {
        return this.handler;
    }
}
