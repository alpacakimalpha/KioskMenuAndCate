package common.network.handler.listener;

import common.network.packet.HelloS2CPacket;
import common.network.packet.UpdateDataPacket;

public interface ClientPacketListener extends PacketListener {
    void onHello(HelloS2CPacket packet);
    void onReceivedData(UpdateDataPacket.ResponseDataS2CPacket packet);
}
