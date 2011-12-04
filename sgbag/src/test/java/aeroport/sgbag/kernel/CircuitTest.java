package aeroport.sgbag.kernel;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Test de la classe Circuit
 */
public class CircuitTest {

	private ArrayList<ElementCircuit> simpleList;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Rail r1 = new Rail();
		Rail r2 = new Rail();
		Rail r3 = new Rail();
		Rail r4 = new Rail();

		Noeud n1 = new Noeud();
		ArrayList<Rail> n1sortie = new ArrayList<>();
		n1sortie.add(r1);
		n1sortie.add(r2);
		n1.setRailsSortie(n1sortie);

		Noeud n2 = new Noeud();
		ArrayList<Rail> n2sortie = new ArrayList<>();
		n2sortie.add(r3);
		n2.setRailsSortie(n2sortie);

		Noeud n3 = new Noeud();
		ArrayList<Rail> n3sortie = new ArrayList<>();
		n3sortie.add(r4);
		n3.setRailsSortie(n3sortie);

		Noeud n4 = new Noeud();

		r1.setNoeudSuivant(n2);
		r2.setNoeudSuivant(n3);
		r3.setNoeudSuivant(n4);
		r4.setNoeudSuivant(n4);

		simpleList = new ArrayList<>();
		simpleList.add(r1);
		simpleList.add(r2);
		simpleList.add(r3);
		simpleList.add(r4);

		simpleList.add(n1);
		simpleList.add(n2);
		simpleList.add(n3);
		simpleList.add(n4);
	}

	/**
	 * Test method for {@link aeroport.sgbag.kernel.Circuit#update()}.
	 */
	@Test
	public void testUpdate() {
		ArrayList<ElementCircuit> updateList = new ArrayList<>();
		updateList.add(new ElementCircuit() {
			public Boolean update() {
				return true;
			}
		});
		Circuit c = new Circuit(updateList);
		assertTrue(c.update());
		updateList.add(new ElementCircuit() {
			public Boolean update() {
				return false;
			}
		});
		assertFalse(c.update());
	}

	/**
	 * Test method for {@link aeroport.sgbag.kernel.Circuit#calculChemin(aeroport.sgbag.kernel.Noeud, aeroport.sgbag.kernel.Noeud)}.
	 */
	@Test
	public void testCalculChemin() {
		fail("Not yet implemented");
	}

}
