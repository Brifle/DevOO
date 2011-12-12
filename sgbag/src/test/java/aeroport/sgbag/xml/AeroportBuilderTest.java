package aeroport.sgbag.xml;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.KeyStore.Builder;

import javax.xml.parsers.ParserConfigurationException;

import lombok.Setter;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.Rail;
import aeroport.sgbag.views.VueEmbranchement;
import aeroport.sgbag.xml.AeroportBuilder.Pair;

public class AeroportBuilderTest {
	
	private AeroportBuilder aBuilder;
	
	@BeforeClass
	public static void beforeClass() {
		PropertyConfigurator.configure("log4j.properties");
	}

	@Before
	public void before() throws ParserConfigurationException, SAXException, IOException {
		aBuilder = new AeroportBuilder(
				"src/test/java/aeroport/sgbag/xml/test.xml");
	}

	@Test
	public void testGetNextKernelObject() {

		Chariot c1 = (Chariot) aBuilder.getNextKernelObject().getValue();
		assertTrue(c1.getLength() == 10);
		assertTrue(c1.getMaxMoveDistance() == 20);

		Rail r = (Rail) aBuilder.getNextKernelObject().getValue();
		assertTrue(r.getLength() == 50);

		Chariot c2 = (Chariot) aBuilder.getNextKernelObject().getValue();
		assertTrue("c2: " + c2.getLength(), c2.getLength() == 11);
		assertTrue("c2: " + c2.getMaxMoveDistance(),
				c2.getMaxMoveDistance() == 21);

		Chariot c3 = (Chariot) aBuilder.getNextKernelObject().getValue();
		assertTrue("c3: " + c3.getLength(), c3.getLength() == 12);
		assertTrue("c3: " + c3.getMaxMoveDistance(),
				c3.getMaxMoveDistance() == 22);

		Chariot c4 = (Chariot) aBuilder.getNextKernelObject().getValue();
		assertTrue("c4: " + c4.getLength(), c4.getLength() == 11);
		assertTrue("c4: " + c4.getMaxMoveDistance(),
				c4.getMaxMoveDistance() == 22);

		Object o = aBuilder.getNextKernelObject().getValue();
		assertTrue(o instanceof Rail);

		Rail r2 = (Rail) o;
		assertTrue(r2.getLength() == 53);

		o = aBuilder.getNextKernelObject();
		assertEquals(null, o);
	}

	@Test
	public void testGetNextViewObject() {
		Object o = aBuilder.getNextViewObject();
		
		assertNotNull(o);
		o = ((Pair)o).getValue();
		assertTrue(o instanceof VueEmbranchement);
	}
}
