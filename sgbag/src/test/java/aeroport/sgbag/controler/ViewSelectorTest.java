package aeroport.sgbag.controler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.Rail;
import aeroport.sgbag.views.VueChariot;
import aeroport.sgbag.views.VueRail;

public class ViewSelectorTest {

	@Test
	public void test() {
		Rail n = new Rail();
		VueRail vn = new VueRail();
		
		Chariot c1 = new Chariot();
		VueChariot vc1 = new VueChariot();
		
		Chariot c2 = new Chariot();
		VueChariot vc2 = new VueChariot();
		
		ViewSelector vs = ViewSelector.getInstance();
		
		vs.setKernelView(n, vn);
		vs.setKernelView(c1, vc1);
		vs.setKernelView(c2, vc2);
		
		assertFalse(c1 == c2);
		assertTrue(vs.getViewForKernelObject(n) == vn);
		assertTrue(vs.getViewForKernelObject(c1) == vc1);
		assertTrue(vs.getViewForKernelObject(c2) == vc2);
		assertFalse(vs.getViewForKernelObject(c1) == vc2);
	}

}
