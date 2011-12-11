package aeroport.sgbag.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import lombok.Getter;
import lombok.Setter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;

import aeroport.sgbag.controler.ViewSelector;
import aeroport.sgbag.kernel.Circuit;
import aeroport.sgbag.kernel.ConnexionCircuit;
import aeroport.sgbag.kernel.ElementCircuit;
import aeroport.sgbag.kernel.Hall;
import aeroport.sgbag.kernel.Noeud;
import aeroport.sgbag.kernel.Rail;
import aeroport.sgbag.kernel.Toboggan;
import aeroport.sgbag.views.Viewable;
import aeroport.sgbag.views.VueEmbranchement;
import aeroport.sgbag.views.VueHall;
import aeroport.sgbag.views.VueRail;
import aeroport.sgbag.views.VueToboggan;
import aeroport.sgbag.utils.PointNoeud;

public class CircuitGenerator {
	
	@Getter
	@Setter
	private static VueHall vueHall;
	
	@Getter
	private static Hall hall = new Hall();
	
	private static HashMap<Point, Noeud> listePointsNoeuds = new HashMap<Point, Noeud>();
	private static ArrayList<ElementCircuit> simpleList = new ArrayList<ElementCircuit>();
	private static Circuit circuit = new Circuit();
	
	private CircuitGenerator(){
	}
	
	public static void UpdateCircuit(){
		circuit.setElements(simpleList);
		hall.setCircuit(circuit);
	}

	public static void createSegment(Point pointEntree, Point pointSortie){//creer les noeuds si existent pas
		Noeud noeudDebut = listePointsNoeuds.get(pointEntree);
		Noeud noeudFin = listePointsNoeuds.get(pointSortie);

		if(noeudDebut == null){
			noeudDebut = createNode(pointEntree).getNoeud();
		}
		if(noeudFin == null){
			noeudFin = createNode(pointSortie).getNoeud();
		}
		
		VueRail vueR = new VueRail(vueHall);
		vueHall.ajouterVue(vueR, 0);
		
		//On genère le rail entre les 2 points
		Geom geom = new Geom();
		vueR.setAngle((float) (geom.getAngleSegment(pointEntree, pointSortie) * 180 / Math.PI));
		Point centre = new Point((pointSortie.x + pointEntree.x) / 2, (pointSortie.y + pointEntree.y) / 2);
		vueR.setX(centre.x);
		vueR.setY(centre.y);
		vueR.setWidth((int) (Math.sqrt(Math.pow(centre.x - pointEntree.x, 2)
				+ Math.pow(centre.y - pointEntree.y, 2))) * 2);

		//Création du rail associé
		Rail railAssocie = new Rail();
		simpleList.add(railAssocie);
		vueR.setRail(railAssocie);
		
		// Pour que la methode update view ne casse pas tout, on met la meme longueur
		railAssocie.setLength((int) (Math.sqrt(Math.pow(centre.x - pointEntree.x, 2)
				+ Math.pow(centre.y - pointEntree.y, 2))) * 4);
		
		noeudDebut.addRailSortie(railAssocie);
		
		ViewSelector.getInstance().setKernelView(railAssocie, vueR);
			
	}
	
	public static VueEmbranchement createNode(Point point){
		Noeud noeud = listePointsNoeuds.get(point);
		VueEmbranchement vueEmbranchement = null;
		
		if(noeud == null){
			//Création du noeud
			noeud = new Noeud(circuit);
			listePointsNoeuds.put(point, noeud);
			
			//Ajout dans la liste des éléments noyaux
			simpleList.add(noeud);
			
			//Création de la vue associée au noeud
			vueEmbranchement = new VueEmbranchement(vueHall);			
			vueEmbranchement.setNoeud(noeud);
			vueEmbranchement.setX(point.x);
			vueEmbranchement.setY(point.y);
			
			vueHall.ajouterVue(vueEmbranchement,1);
			ViewSelector.getInstance().setKernelView(noeud, vueEmbranchement);
		}
		return vueEmbranchement;
	}
	
	public static void createEntry(Point point){
		
	}
	
	public static void createExit(Point point){
		Noeud noeud = listePointsNoeuds.get(point);
		if(noeud == null){
			createNode(point);
		}
		
		// Creation Toboggan
		Toboggan tobo = new Toboggan();
		hall.addFileBagage(tobo);
		
		//On créé un noeud associé à un toboggan
		ConnexionCircuit noeudExit = new ConnexionCircuit(tobo, circuit);
		tobo.setConnexionCircuit(noeudExit);
		
		noeudExit.setRailsSortie(noeud.getRailsSortie());
		//Attention bagages non copiés!!!
		
		replaceNode(point,noeudExit);
		
		
		VueToboggan vTobo = new VueToboggan(vueHall, tobo);
		vTobo.setX(200);
		vTobo.setY(100);
		vueHall.ajouterVue(vTobo, 3);
	}
	
	private static void replaceNode(Point point, Noeud noeud){
		
		Noeud ancienNoeud = listePointsNoeuds.get(point);
		Viewable vue = ViewSelector.getInstance().getViewForKernelObject(ancienNoeud);
		((VueEmbranchement)vue).setNoeud(noeud);
		
		//On copie les rails de sorties
		noeud.setRailsSortie(ancienNoeud.getRailsSortie());
		
		listePointsNoeuds.remove(point);
		listePointsNoeuds.put(point, noeud);
		
		simpleList.remove(ancienNoeud);	
		simpleList.add(noeud);
		
		//Mise à jour du ViewSelector
		ViewSelector.getInstance().removeKeyValue(ancienNoeud);
		ViewSelector.getInstance().setKernelView(noeud, vue);
	}

	
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
	public void generateChemin(VueHall vHall,
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
	
	/** 
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

}
