package be.ecam.basics.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataPacketTest {

    @Test
    void equalPayloadsShouldBeEqualPackets() {
        DataPacket p1 = new DataPacket(new byte[]{1, 2, 3});
        DataPacket p2 = new DataPacket(new byte[]{1, 2, 3});
        assertEquals(p1, p2);
    }

    @Test
    void differentPayloadsAreNotEqual() {
        DataPacket p1 = new DataPacket(new byte[]{1, 2, 3});
        DataPacket p2 = new DataPacket(new byte[]{1, 2, 4});
        assertNotEquals(p1, p2);
    }

    @Test
    void sameInstanceIsEqual() {
        DataPacket p = new DataPacket(new byte[]{1, 2});
        assertEquals(p, p);
    }
}
