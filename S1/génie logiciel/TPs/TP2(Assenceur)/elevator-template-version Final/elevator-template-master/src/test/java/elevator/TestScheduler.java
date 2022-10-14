package elevator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


import org.junit.jupiter.api.Test;

public class TestScheduler {


    @Test
    public void testSAddGo() {
        Scheduler scheduler = new Scheduler(5);
        scheduler.addGoRequest(1);
        assertThat(scheduler.getGoRequest(1)).isTrue();
        assertThat(scheduler.getGoRequest(2)).isFalse();
    }

    @Test
    public void testUpRequest() {
        Scheduler scheduler = new Scheduler(5);
        scheduler.addUpRequest(2);
        assertTrue(scheduler.getUpRequest(2));
        assertFalse(scheduler.getUpRequest(3));
        System.out.println("test passé");
    }

    @Test
    public void testDownRequest() {
        Scheduler scheduler = new Scheduler(5);
        scheduler.addDownRequest(4);
        assertTrue(scheduler.getDownRequest(4));
        assertFalse(scheduler.getDownRequest(2));
        assertFalse(scheduler.getDownRequest(3));
    }
}

