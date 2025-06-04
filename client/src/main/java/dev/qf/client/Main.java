package dev.qf.client;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import common.network.packet.HandShakeC2SInfo;
import common.registry.RegistryManager;
import common.util.KioskLoggerFactory;
import dev.qf.client.network.KioskNettyClient;
import io.netty.channel.ChannelFuture;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final KioskNettyClient INSTANCE = new KioskNettyClient();
    private static final ScheduledExecutorService REGISTRY_REFRESH_EXECUTOR = Executors.newSingleThreadScheduledExecutor(
            new ThreadFactoryBuilder()
                    .setDaemon(true)
                    .setNameFormat("RegistryRefreshThread")
                    .setUncaughtExceptionHandler((t, e) -> KioskLoggerFactory.getLogger().error("Registry refresh thread error", e))
                    .build()
    );
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        ChannelFuture future = INSTANCE.run();

        INSTANCE.sendSerializable(new HandShakeC2SInfo("test"));
        REGISTRY_REFRESH_EXECUTOR.scheduleAtFixedRate(INSTANCE::sendSyncPacket, 5,5, TimeUnit.MINUTES);
        while (RegistryManager.CATEGORIES.size() == 0) {
            Thread.sleep(1000);
        }
        SwingUtilities.invokeAndWait(() -> {new CategoryManagementUI().setVisible(true);});
    }

}
