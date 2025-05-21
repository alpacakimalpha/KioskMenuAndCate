package common.network.packet;

public interface SidedPacket {
    Side getSide();
    void apply();
    enum Side {
        SERVER, CLIENT
    }
}
