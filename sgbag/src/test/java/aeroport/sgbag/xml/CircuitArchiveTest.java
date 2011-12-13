package aeroport.sgbag.xml;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import lombok.NoArgsConstructor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.utils.CircuitGenerator;
import aeroport.sgbag.xml.CircuitArchive.NoeudSaved;
import aeroport.sgbag.xml.CircuitArchive.RailSaved;
import aeroport.sgbag.xml.CircuitArchive.TapisRoulantSaved;
import aeroport.sgbag.xml.CircuitArchive.TobogganSaved;

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
	public void test() throws IOException {
		// Création de l'élément de sauvegarde
		CircuitArchive ca = new CircuitArchive();
		
		// Création des éléments sauvegardables
		NoeudSaved n1 = new NoeudSaved(100, 100);
		NoeudSaved n2 = new NoeudSaved(100, 300);
		NoeudSaved n3 = new NoeudSaved(300, 300);
		NoeudSaved n4 = new NoeudSaved(300, 100);
		
		TobogganSaved x1 = new TobogganSaved(400, 100, 90);
		TapisRoulantSaved t1 = new TapisRoulantSaved(200, 300, 100, 30, 20, 90, false);
		
		RailSaved r1 = new RailSaved(n1, n2);
		RailSaved r2 = new RailSaved(n2, t1);
		RailSaved r3 = new RailSaved(t1, n3);
		RailSaved r4 = new RailSaved(n3, x1);
		RailSaved r5 = new RailSaved(x1, n4);
		RailSaved r6 = new RailSaved(n4, n1);

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
		
		ca.getToboggans().push(x1);
		ca.getTapisRoulants().push(t1);
		
		// Sortie du fichier
		
		FileWriter fstream = new FileWriter("src/test/java/aeroport/sgbag/xml/archive1.xml");
		BufferedWriter out = new BufferedWriter(fstream);

		String str = xStream.toXML(ca);
		out.write(str);
		out.flush();
	}

}
