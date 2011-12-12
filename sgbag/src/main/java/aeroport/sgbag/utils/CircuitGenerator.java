package aeroport.sgbag.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.eclipse.swt.graphics.Point;

import aeroport.sgbag.controler.ViewSelector;
import aeroport.sgbag.kernel.Bagage;
import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.Circuit;
import aeroport.sgbag.kernel.ConnexionCircuit;
import aeroport.sgbag.kernel.ElementCircuit;
import aeroport.sgbag.kernel.Hall;
import aeroport.sgbag.kernel.Noeud;
import aeroport.sgbag.kernel.Rail;
import aeroport.sgbag.kernel.TapisRoulant;
import aeroport.sgbag.kernel.Toboggan;
import aeroport.sgbag.views.Viewable;
import aeroport.sgbag.views.VueChariot;
import aeroport.sgbag.views.VueEmbranchement;
import aeroport.sgbag.views.VueHall;
import aeroport.sgbag.views.VueRail;
import aeroport.sgbag.views.VueTapisRoulant;
import aeroport.sgbag.views.VueToboggan;

public class CircuitGenerator {

	@AllArgsConstructor
	@NoArgsConstructor
	private class PointBinder {
		@Getter
		@Setter
		Point p1;
		@Getter
		@Setter
		Point p2;
	}

	@AllArgsConstructor
	private class Entry {
		Point point;
		int length;
		int vitesse;
		int distanceEntreBagage;
		Boolean autoGeneration;
	}
	
	@AllArgsConstructor
	private class NoeudChariot {
		Noeud node;
		Chariot ch;
	}

	@Getter
	@Setter
	private VueHall vueHall;

	@Getter
	private Hall hall;

	@Setter
	private HashMap<Point, Noeud> listePointsNoeuds;
	@Setter
	private Circuit circuit;

	private List<PointBinder> listOfSegement;
	private List<Entry> listOfEntrys;
	private List<Point> listOfExits;
	private List<Chariot> listOfChariot;

	public CircuitGenerator(VueHall vh) {
		vueHall = vh;
		listePointsNoeuds = new HashMap<Point, Noeud>();
		circuit = new Circuit();
		hall = new Hall();
		hall.setCircuit(circuit);
		vueHall.setHall(hall);
		listOfSegement = new LinkedList<CircuitGenerator.PointBinder>();
		listOfEntrys = new LinkedList<CircuitGenerator.Entry>();
		listOfExits = new LinkedList<Point>();
		listOfChariot = new LinkedList<Chariot>();
	}

	public VueRail createSegment(Point pointEntree, Point pointSortie) {
		listOfSegement.add(new PointBinder(pointEntree, pointSortie));
		return generateSegment(pointEntree, pointSortie);
	}

	public VueRail generateSegment(Point pointEntree, Point pointSortie) {
		Noeud noeudDebut = listePointsNoeuds.get(pointEntree);
		Noeud noeudFin = listePointsNoeuds.get(pointSortie);

		if (noeudDebut == null) {
			noeudDebut = createNode(pointEntree).getNoeud();
		}
		if (noeudFin == null) {
			noeudFin = createNode(pointSortie).getNoeud();
		}

		VueRail vueR = new VueRail(vueHall);
		vueHall.ajouterVue(vueR, 0);

		// On genère le rail entre les 2 points
		Geom geom = new Geom();
		vueR.setAngle((float) (geom.getAngleSegment(pointEntree, pointSortie) * 180 / Math.PI));
		Point centre = new Point((pointSortie.x + pointEntree.x) / 2,
				(pointSortie.y + pointEntree.y) / 2);
		vueR.setX(centre.x);
		vueR.setY(centre.y);
		vueR.setWidth((int) (Math.sqrt(Math.pow(centre.x - pointEntree.x, 2)
				+ Math.pow(centre.y - pointEntree.y, 2))) * 2);

		// Création du rail associé
		Rail railAssocie = new Rail();

		railAssocie.setNoeudPrecedent(noeudDebut);
		railAssocie.setNoeudSuivant(noeudFin);
		
		circuit.getElements().add(railAssocie);
		vueR.setRail(railAssocie);

		// Pour que la methode update view ne casse pas tout, on met la meme
		// longueur
		railAssocie.setLength((int) (Math.sqrt(Math.pow(centre.x
				- pointEntree.x, 2)
				+ Math.pow(centre.y - pointEntree.y, 2))) * 4);

		noeudDebut.addRailSortie(railAssocie);

		ViewSelector.getInstance().setKernelView(railAssocie, vueR);
		return vueR;
	}

	private VueEmbranchement createNode(Point point) {
		Noeud noeud = listePointsNoeuds.get(point);
		VueEmbranchement vueEmbranchement = null;

		if (noeud == null) {
			// Création du noeud
			noeud = new Noeud(circuit);
			listePointsNoeuds.put(point, noeud);

			// Ajout dans la liste des éléments noyaux
			circuit.getElements().add(noeud);

			// Création de la vue associée au noeud
			vueEmbranchement = new VueEmbranchement(vueHall);
			vueEmbranchement.setNoeud(noeud);
			vueEmbranchement.setX(point.x);
			vueEmbranchement.setY(point.y);

			vueHall.ajouterVue(vueEmbranchement, 1);
			ViewSelector.getInstance().setKernelView(noeud, vueEmbranchement);
		}
		return vueEmbranchement;
	}

	public TapisRoulant createEntry(Point point, int length, int vitesse,
			int distanceEntreBagage, Boolean autoGeneration) {
		listOfEntrys.add(new Entry(point, length, vitesse, distanceEntreBagage,
				autoGeneration));

		return generateEntry(point, length, vitesse, distanceEntreBagage,
				autoGeneration);
	}

	public TapisRoulant generateEntry(Point point, int length, int vitesse,
			int distanceEntreBagage, Boolean autoGeneration) {
		Noeud noeud = listePointsNoeuds.get(point);
		if (noeud == null) {
			createNode(point);
			noeud = listePointsNoeuds.get(point);
		}

		// Creation Toboggan
		TapisRoulant tapis = new TapisRoulant(length, vitesse,
				distanceEntreBagage, autoGeneration);
		hall.addFileBagage(tapis);

		// On créé un noeud associé à un toboggan
		ConnexionCircuit noeudExit = new ConnexionCircuit(tapis, circuit);
		tapis.setConnexionCircuit(noeudExit);

		noeudExit.setRailsSortie(noeud.getRailsSortie());
		// Attention bagages non copiés!!!

		replaceNode(point, noeudExit);

		VueTapisRoulant vTapis = new VueTapisRoulant(vueHall, tapis);
		vTapis.setX(point.x);
		vTapis.setY(point.y);
		vueHall.ajouterVue(vTapis, 3);
		ViewSelector.getInstance().setKernelView(tapis, vTapis);

		return tapis;
	}

	public Toboggan createExit(Point point) {
		listOfExits.add(point);
		
		return generateExit(point);
	}

	public Toboggan generateExit(Point point) {
		Noeud noeud = listePointsNoeuds.get(point);
		if (noeud == null) {
			createNode(point);
			noeud = listePointsNoeuds.get(point);
		}

		// Creation Toboggan
		Toboggan tobo = new Toboggan();
		hall.addFileBagage(tobo);

		// On créé un noeud associé à un toboggan
		ConnexionCircuit noeudExit = new ConnexionCircuit(tobo, circuit);
		tobo.setConnexionCircuit(noeudExit);

		noeudExit.setRailsSortie(noeud.getRailsSortie());
		// Attention bagages non copiés!!!

		replaceNode(point, noeudExit);

		VueToboggan vTobo = new VueToboggan(vueHall, tobo);
		vTobo.setX(point.x);
		vTobo.setY(point.y);
		vueHall.ajouterVue(vTobo, 3);
		ViewSelector.getInstance().setKernelView(tobo, vTobo);

		return tobo;
	}
	
	public VueChariot addChariot(Noeud noeud, int maxMoveDistance, int length,
			Noeud destination, Bagage bagage,
			LinkedList<ElementCircuit> cheminPrevu) {
		VueChariot vc = generateChariot(noeud, maxMoveDistance, length,destination, bagage,cheminPrevu);

		listOfChariot.add(vc.getChariot());
		return vc;
	}
	
	public VueChariot addChariot(Rail rail, int maxMoveDistance, int length,
			int position, Noeud destination, Bagage bagage,
			LinkedList<ElementCircuit> cheminPrevu) {
		VueChariot vc = generateChariot(rail, maxMoveDistance, length, 0, destination, bagage, cheminPrevu);

		listOfChariot.add(vc.getChariot());
		return vc;
	}

	public VueChariot generateChariot(Noeud noeud, int maxMoveDistance, int length,
			Noeud destination, Bagage bagage,
			LinkedList<ElementCircuit> cheminPrevu) {
		
		Chariot chariot = new Chariot(maxMoveDistance, length, 0, destination,
				bagage, cheminPrevu);
		
		// On place les chariots
		noeud.registerChariot(chariot);
		chariot.setParent(noeud);

		// On ajoute les vues
		VueChariot vueChariot = new VueChariot(vueHall, chariot);
		vueHall.ajouterVue(vueChariot, 3);
		hall.getChariotList().add(chariot);
		ViewSelector.getInstance().setKernelView(chariot, vueChariot);
		return vueChariot;
	}

	public VueChariot generateChariot(Rail rail, int maxMoveDistance, int length,
			int position, Noeud destination, Bagage bagage,
			LinkedList<ElementCircuit> cheminPrevu) {
		Chariot chariot = new Chariot(maxMoveDistance, length, position,
				destination, bagage, cheminPrevu);

		rail.registerChariot(chariot);
		chariot.setParent(rail);

		// On ajoute les vues
		VueChariot vueChariot = new VueChariot(vueHall, chariot);
		vueHall.ajouterVue(vueChariot, 3);

		// On place les chariots
		rail.registerChariot(chariot);
		chariot.setParent(rail);

		// On ajoute les vues
		hall.getChariotList().add(chariot);
		ViewSelector.getInstance().setKernelView(chariot, vueChariot);
		
		return vueChariot;
	}

	private void replaceNode(Point point, Noeud noeud) {

		Noeud ancienNoeud = listePointsNoeuds.get(point);
		Viewable vue = ViewSelector.getInstance().getViewForKernelObject(
				ancienNoeud);
		((VueEmbranchement) vue).setNoeud(noeud);

		// On copie les rails de sorties
		noeud.setRailsSortie(ancienNoeud.getRailsSortie());
		for(int i=0; i<ancienNoeud.getRailsSortie().size(); i++) {
			ancienNoeud.getRailsSortie().get(i).setNoeudPrecedent(noeud);
		}
		
		boolean trouve = false;
		for(int i=0; i<circuit.getElements().size() && !trouve; i++) {
			ElementCircuit e = circuit.getElements().get(i);
			if(e instanceof Rail && ((Rail)e).getNoeudSuivant().equals(ancienNoeud)) {
				trouve = true;
				((Rail)e).setNoeudSuivant(noeud);
			}
		}

		listePointsNoeuds.remove(point);
		listePointsNoeuds.put(point, noeud);

		circuit.getElements().remove(ancienNoeud);
		circuit.getElements().add(noeud);

		// Mise à jour du ViewSelector
		ViewSelector.getInstance().removeKeyValue(ancienNoeud);
		ViewSelector.getInstance().setKernelView(noeud, vue);
	}
}
