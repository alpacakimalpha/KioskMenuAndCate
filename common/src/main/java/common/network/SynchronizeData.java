package common.network;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;

public interface SynchronizeData<T extends SynchronizeData<?>> {
    Codec<T> getSyncCodec();
    default JsonElement toJson() {
       return getSyncCodec().encodeStart(JsonOps.INSTANCE, (T) this).getOrThrow();
    }
}
