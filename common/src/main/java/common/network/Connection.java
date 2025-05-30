package common.network;

import common.network.handler.SerializableDecoder;
import common.network.handler.SerializableEncoder;
import common.network.handler.SerializableHandler;
import common.network.packet.SidedPacket;
import io.netty.channel.*;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

/**
 * 각 사이드의 네트워크 구현체가 상속하는 인터페이스이다. 해당 인터페이스에는 실행, 셧다운, 연결된 핸들러 획득, 채널 파이프라인 설정 메소드가 정의되어 있다.
 */
@ApiStatus.NonExtendable
public interface Connection {
    void run();
    void shutdown();
    @ApiStatus.Internal
    default ChannelInitializer<Channel> initializeChannelInitializer(SidedPacket.Side side) {
        return new ChannelInitializer<>() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                try {
                    ch.setOption(ChannelOption.TCP_NODELAY, true);
                    ch.setOption(ChannelOption.SO_KEEPALIVE, true);
                } catch (Exception ignored) {
                    // NOP
                }

                ChannelPipeline pipeline = ch.pipeline();

                pipeline.addLast("decoder", new SerializableDecoder());
                pipeline.addLast("encoder", new SerializableEncoder());
                pipeline.addLast("handler", new SerializableHandler(side));
            }
        };
    }
    ChannelFuture sendSerializable(String id, Serializable<?> serializable);
    void handleDisconnect(ChannelHandlerContext ctx, SerializableHandler handler);
    void onEstablishedChannel(ChannelHandlerContext ctx, SerializableHandler handler);
    List<SerializableHandler> getHandlers();
}
