package elevator;

import static elevator.IElevator.State.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class TestAutomaticElevator {
/* 
	@Test
	public void testAutomaticElevator() throws Exception {
		// 3 étages en mode automatique
		var e = new ElevatorSimulator(3, false);
		// activer la montée
		e.up();
		// surveiller l'évolution de l'ascenseur
		while (e.getState() == UP) {
			System.out.printf("level = %3.2f\n", e.getLevel());
			Thread.sleep(100);
		}
		e.stopSimulator();

		// l'ascenseur est au 3ème
		assertEquals(3.0, e.getLevel());
		// l'ascenseur est en erreur
		assertEquals(ERROR, e.getState());
		// les étapes
		assertEquals("-S0-U0-U1-U2-U3-E3", e.getEvents());
	}
*/
	
/* 	
//private static final Object DOOR = null;

	@Test
	public void testAutomaticElevator1() throws Exception {
		// 3 étages en mode automatique
		var e = new ElevatorSimulator(3, true);
		// activer la montée
		e.up();
		// surveiller l'évolution de l'ascenseur
		String events = e.getEvents();
		while (e.getState() == UP) {
			//System.out.printf("level = %3.2f\n", e.getLevel());
			e.oneStep();
			Thread.sleep(100);

			if (e.getLevel() == 2.00) {
				e.stopNext();
			} 

			if (!events.equals(e.getEvents())) {
				char[] eventchar = e.getEvents().toCharArray();
				System.out.println(eventchar[eventchar.length-1]);

			}
		}

		
		e.stopSimulator();

		// l'ascenseur est au 3ème
		assertEquals(3.0, e.getLevel());
		// l'ascenseur est en erreur
		//e.getState();
		assertEquals(DOOR, e.getState());
		// les étapes
		assertEquals("-S0-U0-U1-U2-O3", e.getEvents());
		// verfifcation de l'ouverture des ports.
		System.out.println(e.getState());
		
	}
	 */

	@Test
	public void testAutomaticElevator2() throws Exception {
		// 3 étages en mode automatique
		var e = new ElevatorSimulator(3, true);
		// activer la montée
		e.up();
		// surveiller l'évolution de l'ascenseur
		while (e.getState() == UP) {
			System.out.printf("level = %3.2f\n", e.getLevel());
			e.oneStep();
			Thread.sleep(100);

			e.openDoor();
		//	e.getLevel() == 1.00;
		}
		e.stopSimulator();
		

		// l'ascenseur est au 3ème
		assertEquals(3.0, e.getLevel());
		// l'ascenseur est en erreur
		assertEquals(STOP, e.getState());
		// les étapes
		assertEquals("-S0-U0-U1-U2-U3-E3", e.getEvents());

		System.out.println(e.getState());
		
	}

}
