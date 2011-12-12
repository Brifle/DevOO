package aeroport.sgbag.kernel;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class HallTest {

	@Test
	public void test() {
		TapisRoulant t = new TapisRoulant(30, 5, 5, false);
		Bagage b1 = new Bagage();
		Bagage b2 = new Bagage();
		Bagage b3 = new Bagage();

		t.addBagage(b1);
		t.addBagage(b2);
		t.addBagage(b3);
		
		TapisRoulant t2 = new TapisRoulant(30, 5, 5, false);
		Bagage b4 = new Bagage();
		Bagage b5 = new Bagage();

		t2.addBagage(b4);
		t2.addBagage(b5);
		
		ConnexionCircuit cc1 = new ConnexionCircuit(t);
		ConnexionCircuit cc2 = new ConnexionCircuit(t2);
		
		ArrayList<ElementCircuit> simpleList = new ArrayList<ElementCircuit>();

		simpleList.add(cc1);
		simpleList.add(cc2);
		
		Circuit c = new Circuit(simpleList);		
		
		Hall h = new Hall();
		h.setCircuit(c);

		assertEquals(cc1, h.getOptimalNextTapisRoulant());

		t.chariotIncoming();

		assertEquals(cc2, h.getOptimalNextTapisRoulant());
	}

}
