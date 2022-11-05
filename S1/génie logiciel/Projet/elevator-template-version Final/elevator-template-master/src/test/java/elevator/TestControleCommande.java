package elevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestControleCommande {

    int nbEtage = 8;
    private Scheduler sc = new Scheduler(nbEtage);
    private ElevatorSimulator e = new ElevatorSimulator(nbEtage, true);
    private ControlCommand cc = new ControlCommand(sc, e);

    @Test
    void addGoRequest() {
        assertFalse(cc.getGoRequest(5));
        cc.addGoRequest(5);
        assertTrue(cc.getGoRequest(5));
    }

    @Test
    void addUpRequest() {
        assertFalse(cc.getUpRequest(4));
        cc.addUpRequest(4);
        assertTrue(cc.getUpRequest(4));
    }

    @Test
    void addDownRequest() {
        assertFalse(cc.getDownRequest(4));
        cc.addDownRequest(4);
//        assertTrue(controlCommand.getDownRequest(3));
    }

    @Test
    void geNextFloor() {
        assertEquals(-1, cc.getNextFloor(3, Scheduler.Direction.UP));
        cc.addGoRequest(4);
        cc.addUpRequest(1);
        cc.addDownRequest(6);
        assertEquals(6, cc.getNextFloor(5, Scheduler.Direction.UP));
        assertEquals(4, cc.getNextFloor(2, Scheduler.Direction.UP));
        assertEquals(1, cc.getNextFloor(0, Scheduler.Direction.UP));
        assertEquals(1, cc.getNextFloor(3, Scheduler.Direction.DOWN));
        assertEquals(4, cc.getNextFloor(5, Scheduler.Direction.DOWN));
        assertEquals(6, cc.getNextFloor(7, Scheduler.Direction.DOWN));
    }

    @Test
    void checkAndProcess(){

            cc.addGoRequest(4);
            cc.addUpRequest(0);
            cc.checkAndProcess();
            assertEquals("-S0-U0-U1-U2-U3-O4-S4", e.getEvents());
            e.clearEvents();
            cc.addDownRequest(6);
            cc.addDownRequest(3);
            cc.addGoRequest(3);
            cc.addGoRequest(1);
            cc.checkAndProcess();
            assertEquals("-U4-U5-O6-S6-D6-D5-D4-O3-S3-D3-D2-O1-S1", e.getEvents());
    }
}