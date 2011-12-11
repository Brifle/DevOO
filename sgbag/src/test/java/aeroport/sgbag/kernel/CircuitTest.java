package aeroport.sgbag.kernel;

import static org.junit.Assert.*;

import java.util.*;

import org.apache.log4j.PropertyConfigurator;
import org.junit.*;

/**
 * Test de la classe Circuit
 */
public class CircuitTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
	}

	/**
	 * Test method for {@link aeroport.sgbag.kernel.Circuit#update()}.
	 */
	@Test
	public void testUpdate() {
		ArrayList<ElementCircuit> updateList = new ArrayList<ElementCircuit>();
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
		ArrayList<ElementCircuit> simpleList;
		LinkedList<ElementCircuit> simpleChemin1;
		LinkedList<ElementCircuit> simpleChemin2;
		Rail r1 = new Rail();
		Rail r2 = new Rail();
		Rail r3 = new Rail();
		Rail r4 = new Rail();

		Noeud n1 = new Noeud();
		LinkedList<Rail> n1sortie = new LinkedList<Rail>();
		n1sortie.add(r1);
		n1sortie.add(r2);
		n1.setRailsSortie(n1sortie);

		Noeud n2 = new Noeud();
		LinkedList<Rail> n2sortie = new LinkedList<Rail>();
		n2sortie.add(r3);
		n2.setRailsSortie(n2sortie);

		Noeud n3 = new Noeud();
		LinkedList<Rail> n3sortie = new LinkedList<Rail>();
		n3sortie.add(r4);
		n3.setRailsSortie(n3sortie);

		Noeud n4 = new Noeud();
		
		Noeud n5 = new Noeud();

		r1.setNoeudSuivant(n2);
		r2.setNoeudSuivant(n3);
		r3.setNoeudSuivant(n4);
		r4.setNoeudSuivant(n5);

		simpleList = new ArrayList<ElementCircuit>();
		simpleList.add(n1);
		simpleList.add(n2);
		simpleList.add(n3);
		simpleList.add(n4);
		simpleList.add(n5);
		
		simpleList.add(r1);
		simpleList.add(r2);
		simpleList.add(r3);
		simpleList.add(r4);
		
		simpleChemin1 = new LinkedList<ElementCircuit>();
		simpleChemin1.add(n1);
		simpleChemin1.add(r1);
		simpleChemin1.add(n2);
		simpleChemin1.add(r3);
		simpleChemin1.add(n4);
		
		simpleChemin2 = new LinkedList<ElementCircuit>();
		simpleChemin2.add(n1);
		simpleChemin2.add(r2);
		simpleChemin2.add(n3);
		simpleChemin2.add(r4);
		simpleChemin2.add(n5);
		
		Circuit c = new Circuit(simpleList);
		assertNull(c.calculChemin(n1, n1));
		
		LinkedList<ElementCircuit> chemin1 = c.calculChemin(n1,n4);
		assertEquals(chemin1.size(), simpleChemin1.size());
		for(int i=0; i<chemin1.size(); i++) {
			assertEquals(chemin1.get(i), simpleChemin1.get(i));
		}
		
		LinkedList<ElementCircuit> chemin2 = c.calculChemin(n1,n5);
		assertEquals(chemin2.size(), simpleChemin2.size());
		for(int i=0; i<chemin2.size(); i++) {
			assertEquals(chemin2.get(i), simpleChemin2.get(i));
		}
	}

}
