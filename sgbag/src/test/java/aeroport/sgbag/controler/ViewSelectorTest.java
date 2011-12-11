package aeroport.sgbag.controler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.Rail;
import aeroport.sgbag.views.VueChariot;
import aeroport.sgbag.views.VueRail;

public class ViewSelectorTest {

	private Rail n;
	private VueRail vn;
	private Chariot c1;
	private VueChariot vc1;
	private Chariot c2;
	private VueChariot vc2;
	private ViewSelector vs;

	@Before
	public void setUp() {
		n = new Rail();
		vn = new VueRail();
		
		c1 = new Chariot();
		vc1 = new VueChariot();
		
		c2 = new Chariot();
		vc2 = new VueChariot();
		
		vs = ViewSelector.getInstance();
		
		vs.setKernelView(n, vn);
		vs.setKernelView(c1, vc1);
		vs.setKernelView(c2, vc2);
	}
	
	@Test
	public void test() {		
		assertFalse(c1 == c2);
		assertTrue(vs.getViewForKernelObject(n) == vn);
		assertTrue(vs.getViewForKernelObject(c1) == vc1);
		assertTrue(vs.getViewForKernelObject(c2) == vc2);
		assertFalse(vs.getViewForKernelObject(c1) == vc2);
	}
	
	@Test
	public void testRemoveValue() {
		assertNotNull(vs.getViewForKernelObject(c1));
		vs.removeByView(vc1);
		assertNull(vs.getViewForKernelObject(c1));
	}

}
