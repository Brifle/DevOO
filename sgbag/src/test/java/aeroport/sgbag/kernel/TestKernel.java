package aeroport.sgbag.kernel;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.LinkedList;

import lombok.extern.log4j.Log4j;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@Log4j
public class TestKernel {

	private Hall hall;
	private ArrayList<ElementCircuit> simpleList;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
	}

	@Before
	public void setUpBefore() throws Exception {

		// Tapis roulants
		TapisRoulant tapis1 = new TapisRoulant(200, 5, 30, true);

		// Toboggans
		Toboggan toboggan1 = new Toboggan();
		toboggan1.setAutoDeleteBagages(true);

		// Circuit
		final int longeurRails = 200;
		Rail r1 = new Rail(longeurRails);
		Rail r2 = new Rail(longeurRails);

		ConnexionCircuit n1 = new ConnexionCircuit(tapis1);
		LinkedList<Rail> n1sortie = new LinkedList<Rail>();
		n1sortie.add(r1);
		n1.setRailsSortie(n1sortie);

		ConnexionCircuit n2 = new ConnexionCircuit(toboggan1);
//		LinkedList<Rail> n2sortie = new LinkedList<Rail>();
//		n2sortie.add(r2);
//		n2.setRailsSortie(n2sortie);

		Noeud n3 = new Noeud();
		LinkedList<Rail> n3sortie = new LinkedList<Rail>();
		n3sortie.add(r2);
		n3.setRailsSortie(n3sortie);

		r1.setNoeudSuivant(n3);
		r2.setNoeudSuivant(n2);

		simpleList = new ArrayList<ElementCircuit>();
		simpleList.add(n1);
		simpleList.add(n2);
		simpleList.add(n3);

		simpleList.add(r1);
		simpleList.add(r2);

		Circuit circuit = new Circuit(simpleList);

		hall = new Hall();
		hall.addFileBagage(tapis1);
		hall.addFileBagage(toboggan1);
		hall.setCircuit(circuit);
		hall.init();

		LinkedList<ElementCircuit> chemin = null;
		chemin = circuit.calculChemin(n1, n2);
		Chariot c = new Chariot(5, n2, chemin);
		c.setLength(30);
		n1.registerChariot(c);
	}

	@Test
	public void test() {
		for (int i = 0; i < 200; i++) {
			hall.update();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
