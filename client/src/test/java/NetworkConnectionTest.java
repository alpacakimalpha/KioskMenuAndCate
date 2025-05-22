import common.network.packet.HandShakePacket;
import common.network.packet.HandShakeS2CInfo;
import dev.qf.client.Main;
import dev.qf.client.network.KioskNettyClient;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

public class NetworkConnectionTest {
    @Test
    public void testNetworkConnection() throws InterruptedException {
        Main.INSTANCE.run();
        var future = Main.INSTANCE.getChannel().writeAndFlush(new HandShakePacket(new HandShakeS2CInfo("test"))).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);

        while(!future.isSuccess()) {

        }
        //        ChannelFuture future = KioskNettyClient.connect("127.0.0.1", 8192);
//        future.channel().writeAndFlush("Hello World");

        Main.INSTANCE.shutdown();
    }
}
