
package aeroport.sgbag.kernel;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConnexionCircuitTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
	}

	@Test
	public void testUpdateTakingBagage() {
		Rail r2 = new Rail();
		Rail r3 = new Rail();
		TapisRoulant tapisRoulant = new TapisRoulant(5,5,1,false);
		ConnexionCircuit n = new ConnexionCircuit(tapisRoulant);
		Chariot c = new Chariot();
		Bagage b = new Bagage();

		LinkedList<ElementCircuit> cheminPrevu = new LinkedList<ElementCircuit>();
		cheminPrevu.add(r3);
		c.setCheminPrevu(cheminPrevu);
		c.setDestination(n);

		// Sortie
		n.addRailSortie(r2);
		n.addRailSortie(r3);
		// Chariot
		n.registerChariot(c);
		
		ArrayList<ElementCircuit> listeElems = new ArrayList<ElementCircuit>();
		listeElems.add(r2);
		listeElems.add(n);
		listeElems.add(r3);
		Circuit circuit = new Circuit(listeElems);
		
		r3.setParent(circuit);
		n.setParent(circuit);
		r2.setParent(circuit);
		
		//Tapis
		tapisRoulant.setConnexionCircuit(n);
		tapisRoulant.addBagage(b);
		
		//Le bagage devient pret
		tapisRoulant.update();

		for (int i = 0; i < 9; i++) {
			n.update();
		}

		assertFalse(r3.hasChariot());
		assertTrue(n.hasChariot());
		assertTrue(n.getListeChariot().getFirst().getBagage() == null);

		// Au bon nombre de ticks
		n.update();

		assertTrue(r3.hasChariot());
		assertTrue(r3.getListeChariot().getFirst().getBagage() == b);
		assertFalse(n.hasChariot());
	}
	
	@Test
	public void testUpdatePuttingBagage() {
		Rail r2 = new Rail();
		Rail r3 = new Rail();
		Toboggan toboggan = new Toboggan();
		ConnexionCircuit n = new ConnexionCircuit(toboggan);
		Chariot c = new Chariot();
		Bagage b = new Bagage();
		c.setBagage(b);
		
		LinkedList<ElementCircuit> cheminPrevu = new LinkedList<ElementCircuit>();
		cheminPrevu.add(r3);
		c.setCheminPrevu(cheminPrevu);
		c.setDestination(n);

		// Sortie
		n.addRailSortie(r2);
		n.addRailSortie(r3);
		// Chariot
		n.registerChariot(c);
		
		//Tapis
		toboggan.setConnexionCircuit(n);
		

		for (int i = 0; i < 9; i++) {
			n.update();
		}

		assertFalse(r3.hasChariot());
		assertTrue(n.hasChariot());
		assertTrue(n.getListeChariot().getFirst().getBagage() == b);

		// Au bon nombre de ticks
		n.update();

		assertTrue(r3.hasChariot());
		assertTrue(r3.getListeChariot().getFirst().hasBagage() == false);
		assertFalse(n.hasChariot());
	}
	
	@Test
	public void testUpdateWhithoutStop() {
		Rail r2 = new Rail();
		Rail r3 = new Rail();
		ConnexionCircuit n = new ConnexionCircuit(null);
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
