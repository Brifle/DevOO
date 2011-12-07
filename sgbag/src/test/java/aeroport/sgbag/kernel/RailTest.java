package aeroport.sgbag.kernel;

import static org.junit.Assert.*;

import org.junit.Test;

public class RailTest {

	@Test
	public void testUpdate() {
		Chariot c1 = new Chariot();
		Chariot c2 = new Chariot();
		Chariot c3 = new Chariot();

		Noeud n = new Noeud();

		Rail r = new Rail();

		c1.setMaxMoveDistance(10);
		c1.setLength(10);

		c2.setMaxMoveDistance(20);
		c2.setLength(10);

		c3.setMaxMoveDistance(4);
		c3.setLength(10);

		r.setLength(35);
		r.setNoeudSuivant(n);

		r.registerChariot(c1);
		r.update();

		r.registerChariot(c2);
		r.update();

		r.registerChariot(c3);
		r.update();

		assertTrue("c1 : " + c1.getPosition(), c1.getPosition() == 30);
		assertTrue("c2 : " + c2.getPosition(), c2.getPosition() == 22);
		assertTrue("c3 : " + c3.getPosition(), c3.getPosition() == 4);

		r.update();

		assertTrue("c1 should be gone", r.listeChariot.size() == 2);
		assertTrue(n.hasChariot());
		assertTrue("c2 : " + c2.getPosition(), c2.getPosition() == 30);
		assertTrue("c3 : " + c3.getPosition(), c3.getPosition() == 8);
	}

	@Test
	public void chariotMordantAvecNoeudVide() {
		Chariot c = new Chariot();
		Noeud n = new Noeud();
		Rail r = new Rail();
		
		c.setMaxMoveDistance(10);
		c.setPosition(8);
		c.setLength(10);

		r.setLength(20);
		r.setNoeudSuivant(n);

		r.registerChariot(c);
		r.update();
		
		assertTrue(n.hasChariot());
		assertFalse(r.hasChariot());
	}
	
	@Test
	public void chariotMordantAvecNoeudPlein() {
		Chariot c = new Chariot();
		Chariot c2 = new Chariot();
		Noeud n = new Noeud();
		Rail r = new Rail();
		
		c.setMaxMoveDistance(10);
		c.setPosition(8);
		c.setLength(10);

		r.setLength(20);
		r.setNoeudSuivant(n);

		n.registerChariot(c2);
		r.registerChariot(c);
		r.update();
		
		//Le noeud contient un chariot
		assertTrue(n.hasChariot());
		//C'est le chariot c2
		assertTrue(n.getListeChariot().getFirst() == c2);
		
		//Le rail contient un chariot
		assertTrue(r.hasChariot());
		//C'est le chariot c
		assertTrue(r.getListeChariot().getFirst() == c);
		//Le chariot c est bien placé
		assertTrue(c.getPosition() == 15);
	}	

}
