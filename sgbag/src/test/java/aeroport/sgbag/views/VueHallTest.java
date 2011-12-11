package aeroport.sgbag.views;
import static org.junit.Assert.*;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.widgets.*;
import org.junit.*;

public class VueHallTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
	}

	@Test
	public void testLayouts() {
		VueHall vueHall = new VueHall(new Shell(new Display()), 0);
		VueElem v1 = new VueElem() {
			public void updateView() {}
			public void draw() {}
		};
		vueHall.ajouterVue(v1, 0);
		assertEquals(vueHall.getCalques().get(0).size(), 1);
		vueHall.retirerVue(v1);
		assertEquals(vueHall.getCalques().get(0).size(), 0);
		VueElem v2 = new VueElem() {
			public void updateView() {}
			public void draw() {}
		};
		vueHall.ajouterVue(v1, 1);
		vueHall.ajouterVue(v2, 5);
		assertEquals(vueHall.getCalques().get(0).size(), 0);
		assertEquals(vueHall.getCalques().get(1).size(), 1);
		assertEquals(vueHall.getCalques().get(5).size(), 1);
		assertEquals(vueHall.getCalques().get(2), null);
		vueHall.retirerVue(v2);
		assertEquals(vueHall.getCalques().get(0).size(), 0);
		assertEquals(vueHall.getCalques().get(1).size(), 1);
		assertEquals(vueHall.getCalques().get(5).size(), 0);
		vueHall.ajouterVue(v1, 1);
		assertEquals(vueHall.getCalques().get(1).size(), 2);
		vueHall.retirerVue(v1);
		assertEquals(vueHall.getCalques().get(1).size(), 1);
		vueHall.retirerVue(v1);
		assertEquals(vueHall.getCalques().get(1).size(), 0);
	}

}
