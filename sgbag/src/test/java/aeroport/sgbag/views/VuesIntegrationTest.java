package aeroport.sgbag.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import aeroport.sgbag.kernel.ConnexionCircuit;
import aeroport.sgbag.kernel.Noeud;
import aeroport.sgbag.kernel.Rail;
import aeroport.sgbag.utils.Geom;

public class VuesIntegrationTest {

	private Shell shell;
	private Display display;
	private VueRail vueRail;
	private VueHall vueHall;

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

	@Test
	public void testRailEtEmbranchements() {
		try {
			//Création du chemin principal
			ArrayList<Point> listePoints = new ArrayList<Point>();
			listePoints.add(new Point(20, 20));
			listePoints.add(new Point(100, 20));
			listePoints.add(new Point(200, 100));
			listePoints.add(new Point(500, 500));
			listePoints.add(new Point(40, 300));
			listePoints.add(new Point(40, 400));

			ArrayList<VueRail> listeVuesRail= new ArrayList<VueRail>();
			ArrayList<VueEmbranchement> listeVuesEmbranchements= new ArrayList<VueEmbranchement>(); 
			generateVuesChemin( vueHall, listePoints, null, null, listeVuesRail,listeVuesEmbranchements, true, true); 
			
			//Création d'une branche alternative sur le chemin principal
			ArrayList<Point> listePointsBranche1 = new ArrayList<Point>();
			
			listePointsBranche1.add(new Point(40, 300));
			listePointsBranche1.add(new Point(150, 200));
			listePointsBranche1.add(new Point(200, 100));

			generateVuesChemin( vueHall, listePointsBranche1, null, null, listeVuesRail,listeVuesEmbranchements, false, false); 
			
			//Création d'une feuille  sur la branche alternative
			ArrayList<Point> listePointsFeuille1 = new ArrayList<Point>();
			
			listePointsFeuille1.add(new Point(150, 200));
			listePointsFeuille1.add(new Point(100, 150));

			generateVuesChemin( vueHall, listePointsFeuille1, null, null, listeVuesRail,listeVuesEmbranchements, false, true); 
			
			int indice = 0;

			for (; indice < listeVuesRail.size(); indice++) {
				vueHall.ajouterVue(listeVuesRail.get(indice), 1);
			}
			for (indice = 0; indice < listeVuesEmbranchements.size(); indice++) {
				VueEmbranchement a = listeVuesEmbranchements.get(indice);a.getAngle();
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
	
	//TODO autres tests
	
	/**
	 * Construit une suite de rails et d'embranchements (il n'y a pas d'embranchement "multiple" ).
	 * 
	 * @param vHall = VueHall où doivent être dessinés les rails et les embranchement
	 * @param listePoints = Liste des points ordonnée entre lesquels doivent se trouver les rails et où se trouvent les noeuds
	 * @param listeRail = Liste ordonnée de Rail que l'on veut associer aux vueRails (peut etre null)
	 * @param listeNoeuds =  Liste ordonnée de Noeud que l'on veut associer aux vueEmbranchement (peut etre null)
	 * @param listeVuesRail = Liste des VueRail générée. Si cette liste contient deja des VueRail, alors les nouvelles seront ajoutée à la fin.
	 * @param listeVuesEmbranchements =  Liste des VueEmbranchement générée selon les parametres. Si cette liste contient deja des vues, alors les nouvelles seront ajoutées à la fin.
	 * @param commenceParNoeud = permet de préciser si le chemin doit où non commencer par un noeud
	 * @param finiParNoeud = permet de préciser si le chemin doit où non se terminer par un noeud
	 */
	public void generateVuesChemin(VueHall vHall, ArrayList<Point> listePoints,  ArrayList<Rail> listeRail, ArrayList<Noeud> listeNoeuds, ArrayList<VueRail> listeVuesRail, ArrayList<VueEmbranchement> listeVuesEmbranchements, Boolean commenceParNoeud, Boolean finiParNoeud) {

		if (listePoints.size() >= 2) {
			
			int index = 0;

			//Création des vueRail
			for (; index < listePoints.size() - 1; index++) {
				if(listeRail == null || index > listeRail.size()){
					listeVuesRail.add(generateVueRail(listePoints.get(index),
							listePoints.get(index + 1), null, vHall));
				}else{
					listeVuesRail.add(generateVueRail(listePoints.get(index),
							listePoints.get(index + 1), listeRail.get(index), vHall));
				}
			}
			
			index = 0;			
			if(!commenceParNoeud){
				index = 1;
			}
			int indexFin;
			if(!finiParNoeud){
				indexFin = listePoints.size()-1;
			}else{
				indexFin = listePoints.size();
			}
			
			//Création des VueEmbranchement
			for(;index < indexFin; index++){
				
				if(listeNoeuds == null || index > listeNoeuds.size()){
					listeVuesEmbranchements.add(generateVueEmbranchement(listePoints.get(index), null, vHall));
				}else{
					listeVuesEmbranchements.add(generateVueEmbranchement(listePoints.get(index), listeNoeuds.get(index), vHall));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param p1 Extrémité du rail
	 * @param p2 Extrémité du rail
	 * @param rail Rail à associer à la VueRail générée (peut être null)
	 * @param vHall 
	 * @return VueRail associée aux paramètres ci-dessus
	 */
	public VueRail generateVueRail(Point p1, Point p2, Rail rail, VueHall vHall) {

		VueRail vueR;
		vueR = new VueRail(vHall);
		if (rail == null) {
			vueR.setRail(new Rail());
		} else {
			vueR.setRail(rail);
		}
		Geom geom = new Geom();
		vueR.setAngle((float) (geom.getAngleSegment(p1, p2) * 180 / Math.PI));
		Point centre = new Point((p2.x + p1.x) / 2, (p2.y + p1.y) / 2);
		vueR.setX(centre.x);
		vueR.setY(centre.y);
		vueR.setWidth((int) (Math.sqrt(Math.pow(centre.x - p1.x, 2)
				+ Math.pow(centre.y - p1.y, 2))) * 2);
		return vueR;
	}
	
	/**
	 * 
	 * @param point point ou se situe la VueNoeud
	 * @param noeud Noeud à associer à la VueNoeud (peut être nulle)
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
}
