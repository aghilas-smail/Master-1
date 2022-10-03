package elevator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.*;

public class PanelSimulatorTest {
    PanelSimulator panel = new PanelSimulator(3);
    @Test
    public void testDownButton(){
        var panel = new PanelSimulator(5);
        panel.pressDownButton(2);
        assertThat(panel.getAndResetButtonsSensor()).isTrue();
        assertThat(panel.getAndResetDownButton(2)).isTrue();
        assertThat(panel.getAndResetDownButton(3)).isFalse();
        panel.pressDownButton(3);
        panel.pressDownButton(4);
        assertThat(panel.getAndResetDownButton(3)).isTrue();
        assertThat(panel.getAndResetDownButton(4)).isTrue();
        assertThat(panel.getAndResetDownButton(3)).isFalse();
    }
    @Test
    public void testUpButton(){
        var panel = new PanelSimulator(5);
        panel.pressUpButton(2);
        assertThat(panel.getAndResetButtonsSensor()).isTrue();
        assertThat(panel.getAndResetUpButton(2)).isTrue();
        assertThat(panel.getAndResetUpButton(3)).isFalse();
        panel.pressUpButton(3);
        panel.pressUpButton(4);
        assertThat(panel.getAndResetUpButton(3)).isTrue();
        assertThat(panel.getAndResetUpButton(4)).isTrue();
        assertThat(panel.getAndResetUpButton(3)).isFalse();
    }

    @Test
    public void testStopButton(){
        var panel = new PanelSimulator(5);
        assertThat(panel.getAndResetStopButton()).isFalse();
        panel.pressStopButton();
        assertThat(panel.getAndResetStopButton()).isTrue();
        assertThat(panel.getAndResetStopButton()).isFalse();
    }

    @Test
    public void testReinitButton(){
        var panel = new PanelSimulator(5);
        assertThat(panel.getAndResetInitButton()).isFalse();
        panel.pressInitButton();
        assertThat(panel.getAndResetInitButton()).isTrue();
        assertThat(panel.getAndResetInitButton()).isFalse();
    }

    @Test
    public void testFloorButton(){
        var panel = new PanelSimulator(5);
        assertThat(panel.getAndResetButtonsSensor()).isFalse();
        panel.pressFloorButton(4);
        assertThat(panel.getAndResetButtonsSensor()).isTrue();
        assertThat(panel.getAndResetFloorButton(2)).isFalse();
        assertThat(panel.getAndResetFloorButton(4)).isTrue();
        panel.pressFloorButton(2);
        panel.pressFloorButton(3);
        assertThat(panel.getAndResetFloorButton(2)).isTrue();
        assertThat(panel.getAndResetFloorButton(3)).isTrue();
    }

    @Test
    public void testDownButtonLight(){
        var panel = new PanelSimulator(5);
        panel.setDownLight(2,true);
        assertThat(panel.getDownLight(2)).isTrue();
        assertThat(panel.getDownLight(3)).isFalse();
        panel.setDownLight(3,true);
        panel.setDownLight(4,true);
        assertThat(panel.getDownLight(3)).isTrue();
        assertThat(panel.getDownLight(4)).isTrue();
        assertThat(panel.getDownLight(5)).isFalse();
    }

    @Test
    public void testUpButtonLight(){
        var panel = new PanelSimulator(5);
        assertThat(panel.getUpLight(2)).isFalse();
        panel.setUpLight(2,true);
        assertThat(panel.getUpLight(2)).isTrue();
        assertThat(panel.getUpLight(3)).isFalse();
        panel.setUpLight(3,true);
        panel.setUpLight(4,true);
        assertThat(panel.getUpLight(3)).isTrue();
        assertThat(panel.getUpLight(4)).isTrue();
        assertThat(panel.getUpLight(5)).isFalse();
    }

    @Test
    public void testFloorButtonLight(){

        var panel = new PanelSimulator(5);
        assertThat(panel.getFloorLight(2)).isFalse();
        panel.setFloorLight(2,true);
        assertThat(panel.getFloorLight(2)).isTrue();
        assertThat(panel.getFloorLight(3)).isFalse();
        panel.setFloorLight(3,true);
        panel.setFloorLight(4,true);
        assertThat(panel.getFloorLight(3)).isTrue();
        assertThat(panel.getFloorLight(4)).isTrue();
        assertThat(panel.getFloorLight(5)).isFalse();

    }


    /* 
    @Test
    public void testSetUpLight(){
        assertFalse(panel.getUpLight(3));

        panel.setUpLight(3, true);
        assertTrue(panel.getUpLight(3));
    }
    @Test
    public void testgetFloorLight(){
        panel.setFloorLight(3, true);
        assertTrue(panel.getFloorLight(3));
    }
    @Test
    public void testDownLight(){
        panel.setUpLight(3, true);
        assertFalse(panel.getDownLight(3));
    }

    @Test
    public void testgetAndResetStopButton(){
        panel.getAndResetStopButton();
        assertFalse(panel.getAndResetStopButton());
    }

    @Test
    public void testgetAndUpButton(){
        panel.getAndResetUpButton(3);
        assertFalse(panel.getAndResetUpButton(3));
    }
    @Test
    public void getAndRestButtonsSensor() {
        panel.getAndResetButtonsSensor();
        assertFalse(panel.getAndResetButtonsSensor());
    }

    @Test
    public void getAndRestDownButton() {
        panel.getAndResetUpButton(3);
        assertFalse(panel.getAndResetStopButton());
    }

    @Test
    public void getAndRestStopButton() {
        panel.getAndResetUpButton(3);
        assertFalse(panel.getAndResetStopButton());
    }*/
}
