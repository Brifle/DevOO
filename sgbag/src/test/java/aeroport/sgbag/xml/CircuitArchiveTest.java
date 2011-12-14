package aeroport.sgbag.xml;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import aeroport.sgbag.views.VueHall;
import aeroport.sgbag.xml.CircuitArchive.ChariotSaved;
import aeroport.sgbag.xml.CircuitArchive.NoeudSaved;
import aeroport.sgbag.xml.CircuitArchive.RailSaved;
import aeroport.sgbag.xml.CircuitArchive.TapisRoulantSaved;
import aeroport.sgbag.xml.CircuitArchive.TobogganSaved;

import com.thoughtworks.xstream.XStream;

public class CircuitArchiveTest {

	private XStream xStream;

	@Before
	public void setUp() throws Exception {
		xStream = new XStream();
		
		xStream.processAnnotations(CircuitArchive.class);
	
		xStream.setMode(XStream.ID_REFERENCES);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSerialize() throws IOException {
		// Création de l'élément de sauvegarde
		CircuitArchive ca = new CircuitArchive();
		
		// Création des éléments sauvegardables
		NoeudSaved n1 = new NoeudSaved(100, 100);
		NoeudSaved n2 = new NoeudSaved(100, 300);
		NoeudSaved n3 = new NoeudSaved(300, 300);
		NoeudSaved n4 = new NoeudSaved(300, 100);
		
		TobogganSaved x1 = new TobogganSaved(400, 200, 270);
		TapisRoulantSaved t1 = new TapisRoulantSaved(200, 300, 100, 2, 20, 3*90, false);
		
		RailSaved r1 = new RailSaved(n1, n2);
		RailSaved r2 = new RailSaved(n2, t1);
		RailSaved r3 = new RailSaved(t1, n3);
		RailSaved r4 = new RailSaved(n3, x1);
		RailSaved r5 = new RailSaved(x1, n4);
		RailSaved r6 = new RailSaved(n4, n1);
		RailSaved r7 = new RailSaved(n4, n2);
		
		//ChariotSaved chr1 = new ChariotSaved(r1, 50, 0, n3, 80, 10);
		ChariotSaved chr1 = new ChariotSaved(n2, 0, 10, n3);

		// Remplissage de l'élément de sauvegarde par les éléments
		// à sauvegarder (dans leur forme sauvegardable)
		ca.getNoeuds().push(n1);
		ca.getNoeuds().push(n2);
		ca.getNoeuds().push(n3);
		ca.getNoeuds().push(n4);
		
		ca.getRails().push(r1);
		ca.getRails().push(r2);
		ca.getRails().push(r3);
		ca.getRails().push(r4);
		ca.getRails().push(r5);
		ca.getRails().push(r6);
		ca.getRails().push(r7);
		
		ca.getToboggans().push(x1);
		ca.getTapisRoulants().push(t1);
		
		ca.getChariots().push(chr1);
		
		// Sortie du fichier
		
		FileWriter fstream = new FileWriter("data/circuit-test.xml");
		BufferedWriter out = new BufferedWriter(fstream);

		String str = xStream.toXML(ca);
		out.write(str);
		out.flush();
	}
	
	@Test
	public void testUnserialize() {
		CircuitArchive ca = null;
		try {
			ca = CircuitArchive.readFromXML("data/circuit-test.xml");
			assertTrue(ca != null);
		} catch (MalformedCircuitArchiveException e) {
			assertTrue(false);
		} catch (FileNotFoundException e1) {
			// Do nothing
		} catch (IOException e1) {
			// Do nothing
		}
		
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("VueTest");
		shell.setLayout(new FillLayout());
		shell.setSize(800, 800);
		VueHall vueHall = new VueHall(shell, SWT.NONE);
		vueHall.setSize(600, 600);
		
		ca.unpackTo(vueHall);
		
		//shell.open();
		
		/*while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}*/
		
		vueHall.dispose();
		shell.dispose();
		display.dispose();
	}
}
