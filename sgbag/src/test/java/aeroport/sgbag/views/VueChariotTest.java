package aeroport.sgbag.views;

import static org.junit.Assert.*;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import aeroport.sgbag.controler.ViewSelector;
import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.Rail;

public class VueChariotTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
	}

	@Test
	public void testUpdateView() {
		
		Rail rail = new Rail(100);
		Chariot chariot = new Chariot(10, 5, 10, null, null, null);
		rail.registerChariot(chariot);
		chariot.setParent(rail);
		
		VueRail vueRail = new VueRail();
		vueRail.setRail(rail);
		vueRail.setAngle(0);
		vueRail.setWidth(100);
		vueRail.setX(50);
		vueRail.setY(0);
		ViewSelector.getInstance().setKernelView(rail, vueRail);
		
		VueChariot vueChariot = new VueChariot();
		vueChariot.setChariot(chariot);
		ViewSelector.getInstance().setKernelView(chariot, vueChariot);
		
		vueChariot.updateView();
		
		assertEquals(10, chariot.getPosition());
		assertEquals(10, vueChariot.x);
		assertEquals(0, vueChariot.y);
	}

}
