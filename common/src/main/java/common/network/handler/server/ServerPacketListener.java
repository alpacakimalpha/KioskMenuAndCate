package common.network.handler.server;

import common.network.handler.PacketListener;
import common.network.packet.HandShakeC2SInfo;
import common.network.packet.UpdateDataPacket;

public interface ServerPacketListener extends PacketListener {
    void onHandShake(HandShakeC2SInfo packet);
    void onRequestData(UpdateDataPacket.RequestDataC2SPacket packet);
}
