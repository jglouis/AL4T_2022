package be.ecam.basics.exercises;

public class DataPacket {
    private final byte[] payload;

    public DataPacket(byte[] payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DataPacket other = (DataPacket) obj;
        return this.payload.equals(other.payload);
    }
}
