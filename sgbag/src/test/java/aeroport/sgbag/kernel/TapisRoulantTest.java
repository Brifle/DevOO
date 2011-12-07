package aeroport.sgbag.kernel;

import static org.junit.Assert.*;

import org.junit.Test;

public class TapisRoulantTest {

	@Test
	public void test() {
		TapisRoulant t = new TapisRoulant(30, 5, 5, false);
		Bagage b1 = new Bagage();
		Bagage b2 = new Bagage();
		Bagage b3 = new Bagage();
		
		t.addBagage(b1);
		t.addBagage(b2);
		t.addBagage(b3);
		
		assertEquals(3, t.getNoteBesoinBagages());
		
		t.chariotIncoming();
		t.chariotIncoming();
		
		assertEquals(1, t.getNoteBesoinBagages());
	}

}
