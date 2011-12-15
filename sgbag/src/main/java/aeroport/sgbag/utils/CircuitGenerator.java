package aeroport.sgbag.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.eclipse.swt.graphics.Point;
import org.w3c.dom.ls.LSInput;

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

/**
 * Génère efficacement un circuit.
 * 
 * @author Michael Fagno, Mathieu Sabourin, Thibaut Patel
 */
public class CircuitGenerator {

	/**
	 * Structure contenant deux points (utilisé pour faire deux points).
	 */
	@AllArgsConstructor
	private class PointBinder {
		@Getter
		@Setter
		Point p1;
		@Getter
		@Setter
		Point p2;
	}

	/**
	 * Représente les paramètres nécessaires pour créer une entrée dans le circuit
	 * (typiquement, un tapis roulant).
	 */
	@AllArgsConstructor
	private class Entry {
		Point point;
		int length;
		int vitesse;
		int distanceEntreBagage;
		Boolean autoGeneration;
	}

	@Getter
	@Setter
	private VueHall vueHall;

	@Getter
	@Setter
	private Hall hall;

	@Setter
	private HashMap<Point, Noeud> listePointsNoeuds;
	@Setter
	private Circuit circuit;

	@Getter
	@Setter
	private List<PointBinder> listOfSegement;
	@Getter
	@Setter
	private List<Entry> listOfEntrys;
	@Getter
	@Setter
	private List<Point> listOfExits;
	@Getter
	@Setter
	private List<Chariot> listOfChariot;
	private HashMap<Chariot, Integer> mapOfChariot;
	private HashMap<Integer, ElementCircuit> mapOfElems;
	private LinkedList<Rail> listOfRail;

	/**
	 * Crée un générateur de circuit.
	 * @param vh Vue du hall surlequel travaillera le générateur.
	 */
	public CircuitGenerator(VueHall vh) {
		vueHall = vh;
		listePointsNoeuds = new HashMap<Point, Noeud>();
		circuit = new Circuit();
		hall = new Hall();
		hall.setCircuit(circuit);
		circuit.setParent(hall);
		vueHall.setHall(hall);
		listOfSegement = new LinkedList<CircuitGenerator.PointBinder>();
		listOfEntrys = new LinkedList<CircuitGenerator.Entry>();
		listOfExits = new LinkedList<Point>();
		listOfRail = new LinkedList<Rail>();
		listOfChariot = new LinkedList<Chariot>();
		mapOfChariot = new HashMap<Chariot, Integer>();
		mapOfElems = new HashMap<Integer, ElementCircuit>();
	}

	/**
	 * Crée un segment orienté reliant deux points.
	 * @param pointEntree Début du segment.
	 * @param pointSortie Fin du segment.
	 * @return La vue représentant le rail (segment), généré et ajouté en interne 
	 * au circuit.
	 */
	public VueRail createSegment(Point pointEntree, Point pointSortie) {
		listOfSegement.add(new PointBinder(pointEntree, pointSortie));
		VueRail vr = generateSegment(pointEntree, pointSortie);
		listOfRail.add(vr.getRail());
		return vr;
	}

	/**
	 * Génère un segment orienté reliant deux points.
	 * @param pointEntree Début du segment.
	 * @param pointSortie Fin du segment.
	 * @return La vue représentant le rail (segment) généré selon les paramètres.
	 */
	private VueRail generateSegment(Point pointEntree, Point pointSortie) {
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
		railAssocie.setParent(circuit);
		vueR.setRail(railAssocie);

		// Pour que la methode update view ne casse pas tout, on met la meme
		// longueur
		railAssocie.setLength((int) (Math.sqrt(Math.pow(centre.x
				- pointEntree.x, 2)
				+ Math.pow(centre.y - pointEntree.y, 2))) * 4 - 2*VueEmbranchement.defaultSizeOfNode);

		noeudDebut.addRailSortie(railAssocie);

		ViewSelector.getInstance().setKernelView(railAssocie, vueR);

		mapOfElems.put(noeudDebut.getId(), noeudDebut);
		mapOfElems.put(noeudFin.getId(), noeudFin);
		mapOfElems.put(railAssocie.getId(), railAssocie);

		return vueR;
	}

	/**
	 * Crée un noeud situé au point donné en paramètres.
	 * @param point Point aulequel le noeud sera généré. 
	 * @return La vue représentant le noeud créé.
	 */
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

	/**
	 * Crée une entrée (tapis roulant) du circuit.
	 * @param point Point sur lequel sera créé le tapis.
	 * @param length Taille du tapis.
	 * @param vitesse Vitesse du tapis.
	 * @param distanceEntreBagage Distance entre les bagages situés sur le tapis.
	 * @param autoGeneration Booléen permettant de définir les bagages doivent
	 * être autogénérés ou non.
	 * @return La vue représentant le tapis roulant généré et ajouté au
	 * sein du générateur.
	 */
	public VueTapisRoulant createEntry(Point point, int length, int vitesse,
			int distanceEntreBagage, Boolean autoGeneration) {
		listOfEntrys.add(new Entry(point, length, vitesse, distanceEntreBagage,
				autoGeneration));

		return generateEntry(point, length, vitesse, distanceEntreBagage,
				autoGeneration);
	}
	
	/**
	 * Genère une entrée (tapis roulant) du circuit.
	 * @param point Point sur lequel sera créé le tapis.
	 * @param length Taille du tapis.
	 * @param vitesse Vitesse du tapis.
	 * @param distanceEntreBagage Distance entre les bagages situés sur le tapis.
	 * @param autoGeneration Booléen permettant de définir les bagages doivent
	 * être autogénérés ou non.
	 * @return La vue représentant le tapis roulant simplement généré.
	 */
	private VueTapisRoulant generateEntry(Point point, int length, int vitesse,
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
		vueHall.ajouterVue(vTapis, 2);
		ViewSelector.getInstance().setKernelView(tapis, vTapis);

		return vTapis;
	}

	/**
	 * Crée une sortie (toboggan) sur le circuit.
	 * @param point Point où sera créé le toboggan.
	 * @return La vue représentant le toboggan généré et ajouté au générateur.
	 */
	public VueToboggan createExit(Point point) {
		listOfExits.add(point);

		return generateExit(point);
	}

	/**
	 * Génère une sortie (toboggan) sur le circuit.
	 * @param point Point où sera créé le toboggan.
	 * @return La vue représentant le toboggan simplement généré.
	 */
	private VueToboggan generateExit(Point point) {
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
		vueHall.ajouterVue(vTobo, 2);
		ViewSelector.getInstance().setKernelView(tobo, vTobo);

		return vTobo;
	}

	/**
	 * Crée un chariot et l'ajoute au circuit.
	 * @param noeud Noeud surlequel est posé le chariot.
	 * @param maxMoveDistance Vitesse du chariot : correspond à la distance
	 * qu'il peut parcourir au maximum par tic d'horloge.
	 * @param length Taille du chariot.
	 * @param destination Nœud de destination du chariot.
	 * @param bagage Bagage contenu dans le chariot.
	 * @param cheminPrevu Chemin prévu pour le parcours du chariot.
	 * @return La vue représentant le chariot créé et ajouté au générateur.
	 */
	public VueChariot addChariot(Noeud noeud, int maxMoveDistance, int length,
			Noeud destination, Bagage bagage,
			LinkedList<ElementCircuit> cheminPrevu) {
		VueChariot vc = generateChariot(noeud, maxMoveDistance, length,
				destination, bagage, cheminPrevu);

		mapOfChariot.put(vc.getChariot(), noeud.getId());
		listOfChariot.add(vc.getChariot());
		return vc;
	}

	/**
	 * Crée un chariot et l'ajoute au circuit.
	 * @param rail Rail surlequel est posé le chariot.
	 * @param maxMoveDistance Vitesse du chariot : correspond à la distance
	 * qu'il peut parcourir au maximum par tic d'horloge.
	 * @param length Taille du chariot.
	 * @param position Position du chariot sur le rail.
	 * @param destination Nœud de destination du chariot.
	 * @param bagage Bagage contenu dans le chariot.
	 * @param cheminPrevu Chemin prévu pour le parcours du chariot.
	 * @return La vue représentant le chariot créé et ajouté au générateur.
	 */
	public VueChariot addChariot(Rail rail, int maxMoveDistance, int length,
			int position, Noeud destination, Bagage bagage,
			LinkedList<ElementCircuit> cheminPrevu) {
		VueChariot vc = generateChariot(rail, maxMoveDistance, length, 0,
				destination, bagage, cheminPrevu);

		mapOfChariot.put(vc.getChariot(), rail.getId());
		listOfChariot.add(vc.getChariot());
		return vc;
	}

	/**
	 * Genère un chariot et l'ajoute au circuit.
	 * @param noeud Noeud surlequel est posé le chariot.
	 * @param maxMoveDistance Vitesse du chariot : correspond à la distance
	 * qu'il peut parcourir au maximum par tic d'horloge.
	 * @param length Taille du chariot.
	 * @param destination Nœud de destination du chariot.
	 * @param bagage Bagage contenu dans le chariot.
	 * @param cheminPrevu Chemin prévu pour le parcours du chariot.
	 * @return La vue représentant le chariot créé et ajouté au générateur.
	 */
	private VueChariot generateChariot(Noeud noeud, int maxMoveDistance,
			int length, Noeud destination, Bagage bagage,
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

	/**
	 * Génère un chariot et l'ajoute au circuit.
	 * @param rail Rail surlequel est posé le chariot.
	 * @param maxMoveDistance Vitesse du chariot : correspond à la distance
	 * qu'il peut parcourir au maximum par tic d'horloge.
	 * @param length Taille du chariot.
	 * @param position Position du chariot sur le rail.
	 * @param destination Nœud de destination du chariot.
	 * @param bagage Bagage contenu dans le chariot.
	 * @param cheminPrevu Chemin prévu pour le parcours du chariot.
	 * @return La vue représentant le chariot créé et ajouté au générateur.
	 */
	private VueChariot generateChariot(Rail rail, int maxMoveDistance,
			int length, int position, Noeud destination, Bagage bagage,
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

	/**
	 * Remplace le nœud actuel situé sur un point, par un autre nœud
	 * passé en paramètre.
	 * @param point Point associé au nœud à remplacé.
	 * @param noeud Nouveau nœux, remplaçant celui pointé.
	 */
	private void replaceNode(Point point, Noeud noeud) {

		Noeud ancienNoeud = listePointsNoeuds.get(point);
		Viewable vue = ViewSelector.getInstance().getViewForKernelObject(
				ancienNoeud);
		((VueEmbranchement) vue).setNoeud(noeud);

		// On copie les rails de sorties
		noeud.setRailsSortie(ancienNoeud.getRailsSortie());
		for (int i = 0; i < ancienNoeud.getRailsSortie().size(); i++) {
			ancienNoeud.getRailsSortie().get(i).setNoeudPrecedent(noeud);
		}

		for (int i = 0; i < circuit.getElements().size(); i++) {
			ElementCircuit e = circuit.getElements().get(i);
			if (e instanceof Rail
					&& ((Rail) e).getNoeudSuivant().equals(ancienNoeud)) {
				((Rail) e).setNoeudSuivant(noeud);
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
