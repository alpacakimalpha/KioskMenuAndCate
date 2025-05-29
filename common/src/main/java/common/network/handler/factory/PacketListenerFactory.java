package common.network.handler.factory;

import common.network.handler.listener.PacketListener;
import common.network.handler.SerializableHandler;

public interface PacketListenerFactory {
    PacketListener getPacketListener(SerializableHandler handler);
}
