package common.network;

import io.netty.channel.Channel;

public interface Connection {
    void run();
    void setChannel(Channel channel);
    Channel getChannel();
    void shutdown();
}
