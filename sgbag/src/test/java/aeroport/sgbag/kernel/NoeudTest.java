package aeroport.sgbag.kernel;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

public class NoeudTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
	}

	@Test
	public void testMoveToNextRail() {
		Rail r2 = new Rail();
		Rail r3 = new Rail();
		Noeud n = new Noeud();
		Chariot c = new Chariot();

		LinkedList<ElementCircuit> cheminPrevu = new LinkedList<ElementCircuit>();
		cheminPrevu.add(r3);
		c.setCheminPrevu(cheminPrevu);

		// Sortie
		n.addRailSortie(r2);
		n.addRailSortie(r3);
		// Chariot
		n.registerChariot(c);

		n.moveToNextRail();

		assertTrue(r3.hasChariot());
		assertFalse(n.hasChariot());
	}

	@Test
	public void testUpdate() {
		Rail r2 = new Rail();
		Rail r3 = new Rail();
		Noeud n = new Noeud();
		Chariot c = new Chariot();

		LinkedList<ElementCircuit> cheminPrevu = new LinkedList<ElementCircuit>();
		cheminPrevu.add(r3);
		c.setCheminPrevu(cheminPrevu);

		// Sortie
		n.addRailSortie(r2);
		n.addRailSortie(r3);
		// Chariot
		n.registerChariot(c);

		for (int i = 0; i < 9; i++) {
			n.update();
		}

		assertFalse(r3.hasChariot());
		assertTrue(n.hasChariot());

		// Au bon nombre de ticks
		n.update();

		assertTrue(r3.hasChariot());
		assertFalse(n.hasChariot());
	}
}
