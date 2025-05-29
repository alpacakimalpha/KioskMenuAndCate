package dev.qf.server;

import common.util.KioskLoggerFactory;
import common.network.Connection;
import common.network.handler.factory.PacketListenerFactory;
import common.util.Container;
import dev.qf.server.network.KioskNettyServer;
import dev.qf.server.network.ServerPacketListenerFactory;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.slf4j.Logger;

public class Main {
    public static Connection INSTANCE = new KioskNettyServer();
    private static final Logger LOGGER = KioskLoggerFactory.getLogger();
    public static void main(String[] args) {
        OptionParser optionParser = new OptionParser();
        OptionSpec<Void> optionSpec = optionParser.accepts("debuggingItems");

        OptionSet optionSet = optionParser.parse(args);
        if(optionSet.has(optionSpec)) {
            LOGGER.info("Debugging items enabled");
            DebugData data = new DebugData();
            data.generateDebugData();
        }

        KioskLoggerFactory.getLogger().info("Server started");
        Container.put(PacketListenerFactory.class, new ServerPacketListenerFactory());

        INSTANCE.run();
    }

}
