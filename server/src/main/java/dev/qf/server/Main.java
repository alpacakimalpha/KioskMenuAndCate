package dev.qf.server;

import common.KioskLoggerFactory;

public class Main {
    public static void main(String[] args) {
        KioskNettyServer.run();
        KioskLoggerFactory.getLogger().info("Server started");
    }
}
