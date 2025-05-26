package common.network.handler;

import common.network.packet.SidedPacket;
import io.netty.channel.Channel;

public interface PacketListener {
    SidedPacket.Side getSide();
    Channel getChannel();
}
