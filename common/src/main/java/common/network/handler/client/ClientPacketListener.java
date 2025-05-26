package common.network.handler.client;

import common.network.handler.PacketListener;
import common.network.packet.UpdateDataPacket;

public interface ClientPacketListener extends PacketListener {
    void onReceivedData(UpdateDataPacket.ResponseDataS2CPacket packet);
}
