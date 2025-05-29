package common.registry;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import common.network.SynchronizeData;
import common.util.KioskLoggerFactory;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleRegistry<T extends SynchronizeData<?>> implements Registry<T> {
    private static final Logger LOGGER = KioskLoggerFactory.getLogger();
    private final Map<String, T> idToEntry = new Object2ObjectOpenHashMap<>();
    private final Map<T, String> entryToId = new Object2ObjectOpenHashMap<>();
    private final Set<T> ITEMS = new ObjectLinkedOpenHashSet<>();
    private final Reference2IntMap<T> entryToRawIndex = new Reference2IntOpenHashMap<>();
    private final Int2ReferenceMap<T> rawIndexToEntry = new Int2ReferenceOpenHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final AtomicBoolean frozen = new AtomicBoolean(false);
    private final Class<T> clazz;
    @NotNull
    private final Codec<T> codec;
    @NotNull
    private final String registryId;

    public SimpleRegistry(@NotNull String registryId, @NotNull Codec<T> codec, Class<T> clazz) {
        this.registryId = registryId;
        this.codec = codec;
        this.clazz = clazz;
    }

    @Override
    public String getRegistryId() {
        return this.registryId;
    }

    @Override
    public Optional<T> getById(String id) {
        synchronized (lock) {
            return Optional.ofNullable(idToEntry.get(id));
        }
    }

    @Override
    public boolean isFrozen() {
        return this.frozen.get();
    }

    @Override
    public void freeze() {
        this.frozen.set(true);
    }

    @Override
    public void unfreeze() {
        this.frozen.set(false);
    }

    @Override
    public T add(String id, SynchronizeData<?> entry) {
        if (!clazz.isAssignableFrom(entry.getClass())) {
            throw new IllegalArgumentException("Entry is not of type " + clazz.getName());
        }
        try {
            this.lock.lock();
            this.ITEMS.add((T) entry);
            this.entryToId.put((T) entry, id);
            this.idToEntry.put(id, (T) entry);
            this.rawIndexToEntry.put(size(), (T) entry);
            this.entryToRawIndex.put((T) entry, size());
        } finally {
            this.lock.unlock();
        }
        LOGGER.info("Registered {} with id {}", entry, id);
        return (T) entry;
    }

    @Override
    public @NotNull Codec<T> getCodec() {
        return this.codec;
    }

    @Override
    public List<T> getAll() {
        return ImmutableList.copyOf(this.ITEMS);
    }

    @Override
    public Class<T> getClazz() {
        return this.clazz;
    }

    @Override
    public int getRawId(T var1) {
        synchronized (lock) {
            return entryToRawIndex.getOrDefault(var1, ABSENT_LOW_INDEX);
        }
    }

    @Override
    public @Nullable T get(int index) {
        synchronized (lock) {
            return rawIndexToEntry.get(index);
        }
    }

    @Override
    public int size() {
        return this.ITEMS.size();
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return ITEMS.iterator();
    }
}
