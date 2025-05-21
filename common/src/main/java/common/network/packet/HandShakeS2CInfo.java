package common.network.packet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record HandShakeS2CInfo(String id) implements SidedPacket {
    public static final Codec<HandShakeS2CInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("id").forGetter(HandShakeS2CInfo::id)
            ).apply(instance, HandShakeS2CInfo::new)
    );

    @Override
    public Side getSide() {
        return Side.SERVER;
    }

    @Override
    public  void apply() {
        // TODO : IMPELEMENT HANDSHAKE LOGIC
    }
}
