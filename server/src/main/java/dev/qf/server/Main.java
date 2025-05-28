package dev.qf.server;

import common.KioskLoggerFactory;
import common.network.Connection;
import common.network.handler.factory.PacketListenerFactory;
import common.util.Container;
import dev.qf.server.network.KioskNettyServer;
import dev.qf.server.network.ServerPacketListenerFactory;

public class Main {
    public static Connection INSTANCE = new KioskNettyServer();
    public static void main(String[] args) {
        KioskLoggerFactory.getLogger().info("Server started");
        Container.put(PacketListenerFactory.class, new ServerPacketListenerFactory());

        INSTANCE.run();
    }

}
