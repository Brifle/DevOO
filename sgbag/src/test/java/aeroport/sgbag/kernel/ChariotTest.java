package aeroport.sgbag.kernel;

import static org.junit.Assert.*;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChariotTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
	}

	@Test
	public void testCollision() {
		Chariot c1 = new Chariot();
		Chariot c2 = new Chariot();
		Chariot c3 = new Chariot();

		c1.setPosition(5);
		c1.setLength(10);

		c2.setPosition(10);
		c2.setLength(10);

		c3.setPosition(30);
		c3.setLength(10);

		assertTrue("Should be in collision", c1.isColliding(c2));
		assertFalse("Should not be colliding", c1.isColliding(c3));
	}

	@Test
	public void testWillCollide() {
		Chariot c1 = new Chariot();
		Chariot c2 = new Chariot();
		Chariot c3 = new Chariot();

		c1.setLength(10);

		c2.setPosition(10);
		c2.setLength(10);

		c3.setPosition(30);
		c3.setLength(10);

		assertTrue("Should be in collision", c1.willCollide(10, c2));
		assertFalse("Should not be colliding", c1.willCollide(10, c3));
	}

}
