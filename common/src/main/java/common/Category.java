package common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import common.network.SynchronizeData;

import java.util.List;


public record Category(String cateId, String cateName, List<Menu> menus) implements SynchronizeData<Category> {
    public static final Codec<Category> SYNC_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("cateId").forGetter(Category::cateId),
                    Codec.STRING.fieldOf("cateName").forGetter(Category::cateName),
                    Menu.CODEC.listOf().fieldOf("categories").forGetter(Category::menus)
            ).apply(instance, Category::new)
    );

    @Override
    public Codec<Category> getSyncCodec() {
        return SYNC_CODEC;
    }

    @Override
    public String getRegistryElementId() {
        return this.cateId;
    }

}
