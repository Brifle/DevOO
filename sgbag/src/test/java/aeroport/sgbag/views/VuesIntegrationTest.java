package aeroport.sgbag.views;

import java.util.ArrayList;
import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import aeroport.sgbag.controler.Clock;
import aeroport.sgbag.controler.ViewSelector;
import aeroport.sgbag.kernel.Bagage;
import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.Circuit;
import aeroport.sgbag.kernel.ConnexionCircuit;
import aeroport.sgbag.kernel.ElementCircuit;
import aeroport.sgbag.kernel.FileBagage;
import aeroport.sgbag.kernel.Hall;
import aeroport.sgbag.kernel.Noeud;
import aeroport.sgbag.kernel.Rail;
import aeroport.sgbag.kernel.TapisRoulant;
import aeroport.sgbag.kernel.Toboggan;
import aeroport.sgbag.utils.UtilsCircuit;
import aeroport.sgbag.utils.Geom;

public class VuesIntegrationTest {

	private Shell shell;
	private Display display;
	private VueHall vueHall;
	private Hall hall;
	private ArrayList<ElementCircuit> simpleList;
	private Circuit circuit;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		display = new Display();
		shell = new Shell(display);
		shell.setText("VueTest");
		shell.setLayout(new FillLayout());
		shell.setSize(800, 800);
		vueHall = new VueHall(shell, SWT.NONE);
		vueHall.setSize(300, 300);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		vueHall.dispose();
		shell.dispose();
		display.dispose();
	}

	public void initializeKernel(ArrayList<ElementCircuit> listeElem,
			ArrayList<FileBagage> files, ArrayList<Bagage> bagages) {
		if (simpleList == null) {
			simpleList = new ArrayList<ElementCircuit>();
		} else {
			simpleList.clear();
		}
		
		if(listeElem != null){
			for (int i = 0; i < listeElem.size(); i++) {
				simpleList.add(listeElem.get(i));
			}			
		}

		if (circuit == null) {
			circuit = new Circuit(simpleList);
		} else {
			circuit.setElements(simpleList);
		}

		if (hall == null) {
			hall = new Hall();
		}

		hall.setCircuit(circuit);
		hall.getFileBagageList().clear();

		if(files != null){
			for (int j = 0; j < files.size(); j++) {
				hall.addFileBagage(files.get(j));
			}
		}

		hall.setBagagesList(bagages);
		
		
		UtilsCircuit.getUtilsCircuit().setCircuit(circuit);
	}

	@Test
	public void testRailEtEmbranchements() {
		try {
			// Création du chemin principal
			ArrayList<PointNoeud> listePoints = new ArrayList<VuesIntegrationTest.PointNoeud>();
			listePoints.add(new PointNoeud(20, 20, null));
			listePoints.add(new PointNoeud(100, 20, null));
			listePoints.add(new PointNoeud(200, 100, null));
			listePoints.add(new PointNoeud(500, 500, null));
			listePoints.add(new PointNoeud(40, 300, null));
			listePoints.add(new PointNoeud(40, 400, null));

			ArrayList<VueRail> listeVuesRail = new ArrayList<VueRail>();
			ArrayList<VueEmbranchement> listeVuesEmbranchements = new ArrayList<VueEmbranchement>();
			generateVuesChemin(vueHall, listePoints, listeVuesRail,
					listeVuesEmbranchements, true, true);

			// Création d'une branche alternative sur le chemin principal
			ArrayList<PointNoeud> listePointsBranche1 = new ArrayList<VuesIntegrationTest.PointNoeud>();

			listePointsBranche1.add(new PointNoeud(40, 300,
					listePoints.get(4).noeud));
			listePointsBranche1.add(new PointNoeud(150, 200, null));
			listePointsBranche1.add(new PointNoeud(200, 100,
					listePoints.get(2).noeud));

			generateVuesChemin(vueHall, listePointsBranche1, listeVuesRail,
					listeVuesEmbranchements, false, false);

			// Création d'une feuille sur la branche alternative
			ArrayList<PointNoeud> listePointsFeuille1 = new ArrayList<VuesIntegrationTest.PointNoeud>();

			listePointsFeuille1.add(new PointNoeud(150, 200,
					listePointsBranche1.get(1).noeud));
			listePointsFeuille1.add(new PointNoeud(100, 150, null));

			generateVuesChemin(vueHall, listePointsFeuille1, listeVuesRail,
					listeVuesEmbranchements, false, true);

			int indice = 0;

			for (; indice < listeVuesRail.size(); indice++) {
				vueHall.ajouterVue(listeVuesRail.get(indice), 1);
			}
			for (indice = 0; indice < listeVuesEmbranchements.size(); indice++) {
				VueEmbranchement a = listeVuesEmbranchements.get(indice);
				a.getAngle();
				vueHall.ajouterVue(listeVuesEmbranchements.get(indice), 2);
			}

			ArrayList<ElementCircuit> listeElem = new ArrayList<ElementCircuit>();
			
			//On recupere tous les elements
			for (int i = 0; i < listeVuesRail.size(); i++) {
				listeElem.add(listeVuesRail.get(i).getRail());
			}
			for (int i = 0; i < listeVuesEmbranchements.size(); i++) {
				listeElem.add(listeVuesEmbranchements.get(i).getNoeud());
			}
			
			initializeKernel(listeElem, null, null);
			InitializeViewSelectorEmbranchements(listeVuesEmbranchements);
			InitializeViewSelectorRails(listeVuesRail);

			shell.open();
			vueHall.draw();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}

		} catch (Exception e) {
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	@Test
	// TODO avec les tics
	public void testCircuitAvecChariot() throws InterruptedException {

		// Création du chemin principal
		ArrayList<PointNoeud> listePoints = new ArrayList<VuesIntegrationTest.PointNoeud>();
		listePoints.add(new PointNoeud(200, 100, null));
		listePoints.add(new PointNoeud(500, 500, null));
		listePoints.add(new PointNoeud(40, 300, null));
		listePoints.add(new PointNoeud(40, 400, null));

		ArrayList<VueRail> listeVuesRail = new ArrayList<VueRail>();
		ArrayList<VueEmbranchement> listeVuesEmbranchements = new ArrayList<VueEmbranchement>();
		generateVuesChemin(vueHall, listePoints, listeVuesRail,
				listeVuesEmbranchements, true, true);

		// Création d'une branche alternative sur le chemin principal
		ArrayList<PointNoeud> listePointsBranche1 = new ArrayList<VuesIntegrationTest.PointNoeud>();

		listePointsBranche1.add(new PointNoeud(40, 300,
				listePoints.get(2).noeud));
		listePointsBranche1.add(new PointNoeud(150, 200, null));
		listePointsBranche1.add(new PointNoeud(200, 100,
				listePoints.get(0).noeud));

		generateVuesChemin(vueHall, listePointsBranche1, listeVuesRail,
				listeVuesEmbranchements, false, false);

		int indice = 0;

		for (; indice < listeVuesRail.size(); indice++) {
			vueHall.ajouterVue(listeVuesRail.get(indice), 1);
		}
		for (indice = 0; indice < listeVuesEmbranchements.size(); indice++) {
			VueEmbranchement a = listeVuesEmbranchements.get(indice);
			a.getAngle();
			vueHall.ajouterVue(listeVuesEmbranchements.get(indice), 2);
		}

		// Création du chemin des chariots
		LinkedList<ElementCircuit> cheminPrevu1 = new LinkedList<ElementCircuit>();
		cheminPrevu1.add(listeVuesRail.get(0).getRail());
		cheminPrevu1.add(listeVuesEmbranchements.get(1).getNoeud());

		LinkedList<ElementCircuit> cheminPrevu2 = new LinkedList<ElementCircuit>();
		cheminPrevu2.add(listeVuesEmbranchements.get(1).getNoeud());
		cheminPrevu2.add(listeVuesRail.get(1).getRail());

		Chariot chariot1 = new Chariot(200, 20, 0, listeVuesEmbranchements
				.get(1).getNoeud(), null, cheminPrevu1);
		Chariot chariot2 = new Chariot(200, 20, 200, listeVuesEmbranchements
				.get(1).getNoeud(), null, cheminPrevu2);

		// On place les chariots
		listeVuesEmbranchements.get(0).getNoeud().registerChariot(chariot1);
		chariot1.setParent(listeVuesEmbranchements.get(0).getNoeud());
		

		listeVuesRail.get(0).getRail().registerChariot(chariot2);
		chariot2.setParent(listeVuesRail.get(0).getRail());

		// On ajoute les vues
		VueChariot vueChariot1 = new VueChariot(vueHall, chariot1);
		VueChariot vueChariot2 = new VueChariot(vueHall, chariot2);
		
		vueHall.ajouterVue(vueChariot1, 3);
		vueHall.ajouterVue(vueChariot2, 3);
		ArrayList<VueChariot> vuesChariots = new ArrayList<VueChariot>();
		vuesChariots.add(vueChariot1);
		vuesChariots.add(vueChariot2);

		//ViewSelector.getInstance().clear();
		ArrayList<ElementCircuit> listeElem = new ArrayList<ElementCircuit>();
		
		//On recupere tous les elements
		for (int i = 0; i < listeVuesRail.size(); i++) {
			listeElem.add(listeVuesRail.get(i).getRail());
		}
		for (int i = 0; i < listeVuesEmbranchements.size(); i++) {
			listeElem.add(listeVuesEmbranchements.get(i).getNoeud());
		}

		
		initializeKernel(listeElem, null, null);
		InitializeViewSelectorEmbranchements(listeVuesEmbranchements);
		InitializeViewSelectorRails(listeVuesRail);
		
		InitializeViewSelectorEmbranchements(listeVuesEmbranchements);
		InitializeViewSelectorRails(listeVuesRail);
		InitializeViewSelectorChariot(vuesChariots);
		
		//TODO prblem update
		hall.init();
		shell.open();
		// for(int i = 0; i<10; i++){
		// System.out.println("Clock!!");
		vueHall.updateView();
		vueHall.draw();
		// Thread.sleep(2000);
		// }
		initializeKernel(listeElem, null, null);
		
		Clock clock = new Clock(200, hall, vueHall, true);
		clock.run();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	@Test
	public void testEmbranchementsToboggansTapis() {// TODO methode pr affecter
													// un tapis/tobo à un
													// embranchemant
		try {

			// Creation Toboggan
			Toboggan tobo = new Toboggan(1, 2, true);
			ConnexionCircuit conex = new ConnexionCircuit(tobo, circuit);
			tobo.setConnexionCircuit(conex);
			VueToboggan vTobo = new VueToboggan(vueHall, tobo);
			vTobo.setX(200);
			vTobo.setY(100);
			vueHall.ajouterVue(vTobo, 3);

			// Creation Tapis
			TapisRoulant tapis = new TapisRoulant(100, 10, 5, true);
			ConnexionCircuit conex2 = new ConnexionCircuit(tobo);
			tobo.setConnexionCircuit(conex2);
			VueTapisRoulant vTapis = new VueTapisRoulant(vueHall, tapis);
			vTapis.setX(500);
			vTapis.setY(500);
			vueHall.ajouterVue(vTapis, 3);

			// Création du chemin principal
			ArrayList<PointNoeud> listePoints = new ArrayList<VuesIntegrationTest.PointNoeud>();
			listePoints.add(new PointNoeud(20, 20, null));
			listePoints.add(new PointNoeud(100, 20, null));
			listePoints.add(new PointNoeud(200, 100, conex));
			listePoints.add(new PointNoeud(500, 500, null));
			listePoints.add(new PointNoeud(40, 300, null));
			listePoints.add(new PointNoeud(40, 400, null));

			ArrayList<VueRail> listeVuesRail = new ArrayList<VueRail>();
			ArrayList<VueEmbranchement> listeVuesEmbranchements = new ArrayList<VueEmbranchement>();
			generateVuesChemin(vueHall, listePoints, listeVuesRail,
					listeVuesEmbranchements, true, true);

			// Création d'une branche alternative sur le chemin principal
			ArrayList<PointNoeud> listePointsBranche1 = new ArrayList<VuesIntegrationTest.PointNoeud>();

			listePointsBranche1.add(new PointNoeud(40, 300,
					listePoints.get(4).noeud));
			listePointsBranche1.add(new PointNoeud(150, 200, null));
			listePointsBranche1.add(new PointNoeud(200, 100,
					listePoints.get(2).noeud));

			generateVuesChemin(vueHall, listePointsBranche1, listeVuesRail,
					listeVuesEmbranchements, false, false);

			// Création d'une feuille sur la branche alternative
			ArrayList<PointNoeud> listePointsFeuille1 = new ArrayList<VuesIntegrationTest.PointNoeud>();

			listePointsFeuille1.add(new PointNoeud(150, 200,
					listePointsBranche1.get(1).noeud));
			listePointsFeuille1.add(new PointNoeud(100, 150, null));

			generateVuesChemin(vueHall, listePointsFeuille1, listeVuesRail,
					listeVuesEmbranchements, false, true);

			int indice = 0;

			for (; indice < listeVuesRail.size(); indice++) {
				vueHall.ajouterVue(listeVuesRail.get(indice), 1);
			}
			for (indice = 0; indice < listeVuesEmbranchements.size(); indice++) {
				VueEmbranchement a = listeVuesEmbranchements.get(indice);
				a.getAngle();
				vueHall.ajouterVue(listeVuesEmbranchements.get(indice), 2);
			}

			shell.open();
			vueHall.draw();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}

		} catch (Exception e) {
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	// TODO autres tests

	/**
	 * Construit une suite de rails et d'embranchements (il n'y a pas
	 * d'embranchement "multiple" ).
	 * 
	 * @param vHall
	 *            = VueHall où doivent être dessinés les rails et les
	 *            embranchement
	 * @param listePoints
	 *            = Liste des points ordonnée entre lesquels doivent se trouver
	 *            les rails et où se trouvent les noeuds (à chaque point on peut
	 *            associer un noeud (toboggan, tapis ...). Par defaut c'est un
	 *            noeud basique qui est créé.
	 * @param listeVuesRail
	 *            = Liste des VueRail générée. Si cette liste contient deja des
	 *            VueRail, alors les nouvelles seront ajoutée à la fin.
	 * @param listeVuesEmbranchements
	 *            = Liste des VueEmbranchement générée selon les parametres. Si
	 *            cette liste contient deja des vues, alors les nouvelles seront
	 *            ajoutées à la fin.
	 * @param commenceParNoeud
	 *            = permet de préciser si le chemin doit où non commencer par un
	 *            noeud
	 * @param finiParNoeud
	 *            = permet de préciser si le chemin doit où non se terminer par
	 *            un noeud
	 */
	public void generateVuesChemin(VueHall vHall,
			ArrayList<PointNoeud> listePoints,
			ArrayList<VueRail> listeVuesRail,
			ArrayList<VueEmbranchement> listeVuesEmbranchements,
			Boolean commenceParNoeud, Boolean finiParNoeud) {
		// Boolean stop = false;

		if (listePoints.size() >= 2) {

			int index;
			int indexDeb = 0;
			if (!commenceParNoeud) {
				if (listePoints.get(0).noeud == null) {
					System.out.println("Il faut instancier un noeud de départ");
					return;// stop = true;
				}
				indexDeb = 1;

			}
			int indexFin;
			if (!finiParNoeud) {
				if (listePoints.get(listePoints.size() - 1).noeud == null) {
					System.out.println("Il faut instancier un noeud de fin");
					return;// stop = true;
				}
				indexFin = listePoints.size() - 1;

			} else {
				indexFin = listePoints.size();
			}

			// Création des VueEmbranchement
			for (index = indexDeb; index < indexFin; index++) {
				VueEmbranchement vueEmbranchement = generateVueEmbranchement(
						listePoints.get(index).pt,
						listePoints.get(index).noeud, vHall);
				listeVuesEmbranchements.add(vueEmbranchement);

				// Si il n'y avait pas de noeud dans listePoint, on le rajoute
				if (listePoints.get(index).noeud == null) {
					listePoints.get(index).noeud = vueEmbranchement.getNoeud();
				}
			}

			// Création des vueRail
			for (index = 0; index < listePoints.size() - 1; index++) {
				PointNoeud noeudDebut = listePoints.get(index);
				PointNoeud noeudFin = listePoints.get(index + 1);
				VueRail vueRail = generateVueRail(noeudDebut.pt, noeudFin.pt,
						vHall);

				// On attache le rail
				vueRail.getRail().setNoeudPrecedent(noeudDebut.noeud);
				vueRail.getRail().setNoeudSuivant(noeudFin.noeud);
				noeudDebut.noeud.addRailSortie(vueRail.getRail());

				listeVuesRail.add(vueRail);
			}
		}
	}

	/**
	 * 
	 * @param p1
	 *            Extrémité du rail
	 * @param p2
	 *            Extrémité du rail
	 * @param rail
	 *            Rail à associer à la VueRail générée (peut être null)
	 * @param vHall
	 * @return VueRail associée aux paramètres ci-dessus
	 */
	public VueRail generateVueRail(Point p1, Point p2, VueHall vHall) {

		VueRail vueR;
		vueR = new VueRail(vHall);
		Rail railAssocie = new Rail();
		vueR.setRail(railAssocie);

		Geom geom = new Geom();
		vueR.setAngle((float) (geom.getAngleSegment(p1, p2) * 180 / Math.PI));
		Point centre = new Point((p2.x + p1.x) / 2, (p2.y + p1.y) / 2);
		vueR.setX(centre.x);
		vueR.setY(centre.y);
		vueR.setWidth((int) (Math.sqrt(Math.pow(centre.x - p1.x, 2)
				+ Math.pow(centre.y - p1.y, 2))) * 2);

		// Pour que la methode update view ne casse pas tout
		railAssocie.setLength((int) (Math.sqrt(Math.pow(centre.x - p1.x, 2)
				+ Math.pow(centre.y - p1.y, 2))) * 4);

		return vueR;
	}

	/**
	 * 
	 * @param point
	 *            point ou se situe la VueNoeud
	 * @param noeud
	 *            Noeud à associer à la VueNoeud (peut être nulle)
	 * @param vHall
	 * @return VueEmbranchement associée aux paramètres ci-dessus
	 */
	public VueEmbranchement generateVueEmbranchement(Point point, Noeud noeud,
			VueHall vHall) {

		VueEmbranchement VueEmbranchement;
		VueEmbranchement = new VueEmbranchement(vHall);
		if (noeud == null) {
			VueEmbranchement.setNoeud(new Noeud());
		} else {
			VueEmbranchement.setNoeud(noeud);
		}

		VueEmbranchement.setX(point.x);
		VueEmbranchement.setY(point.y);

		return VueEmbranchement;
	}

	public void InitializeViewSelectorEmbranchements(
			ArrayList<VueEmbranchement> listeVuesEmbranchements) {
		ViewSelector viewSelector = ViewSelector.getInstance();
		for (int i = 0; i < listeVuesEmbranchements.size(); i++) {
			viewSelector.setKernelView(listeVuesEmbranchements.get(i)
					.getNoeud(), listeVuesEmbranchements.get(i));
		}
	}

	public void InitializeViewSelectorRails(ArrayList<VueRail> listeVuesRails) {
		ViewSelector viewSelector = ViewSelector.getInstance();
		for (int i = 0; i < listeVuesRails.size(); i++) {
			viewSelector.setKernelView(listeVuesRails.get(i).getRail(),
					listeVuesRails.get(i));
		}
	}

	public void InitializeViewSelectorBagages(
			ArrayList<VueBagage> listeVuesBagages) {
		ViewSelector viewSelector = ViewSelector.getInstance();
		for (int i = 0; i < listeVuesBagages.size(); i++) {
			viewSelector.setKernelView(listeVuesBagages.get(i).getBagage(),
					listeVuesBagages.get(i));
		}
	}
	
	public void InitializeViewSelectorChariot(
			ArrayList<VueChariot> listeVueschariots) {
		ViewSelector viewSelector = ViewSelector.getInstance();
		for (int i = 0; i < listeVueschariots.size(); i++) {
			viewSelector.setKernelView(listeVueschariots.get(i).getChariot(),
					listeVueschariots.get(i));
		}
	}

	 class PointNoeud {
		public PointNoeud(Point p, Noeud n) {
			pt = p;
			noeud = n;
		}

		public PointNoeud(int x, int y, Noeud n) {
			pt = new Point(x, y);
			noeud = n;
		}

		public Point pt;
		public Noeud noeud;
	}

}
