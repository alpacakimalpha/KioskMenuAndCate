package common.network.handler.listener;

import common.network.handler.SerializableHandler;
import common.network.packet.SidedPacket;

public interface PacketListener {
    SidedPacket.Side getSide();
    SerializableHandler getHandler();
}
