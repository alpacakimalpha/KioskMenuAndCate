package common.network.packet;

import com.mojang.serialization.Codec;
import common.network.Serializable;

public record HandShakePacket(HandShakeS2CInfo info) implements Serializable<HandShakeS2CInfo> {

    @Override
    public String getPacketId() {
        return "handshake";
    }

    @Override
    public HandShakeS2CInfo getValue() {
        return info;
    }

    @Override
    public Codec<HandShakeS2CInfo> getCodec() {
        return HandShakeS2CInfo.CODEC;
    }
}
