package aeroport.sgbag.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import lombok.Getter;
import lombok.Setter;

import org.eclipse.swt.SWT;
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
	
	public static void clear(){
		hall = new Hall();
		listePointsNoeuds = new HashMap<Point, Noeud>();
		simpleList = new ArrayList<ElementCircuit>();
		circuit = new Circuit();
	}
	
	public static void updateCircuit(){
		circuit.setElements(simpleList);
		hall.setCircuit(circuit);
	}

	public static VueRail createSegment(Point pointEntree, Point pointSortie){//creer les noeuds si existent pas
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
		return vueR;
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
	
	public static TapisRoulant createEntry(Point point, int length, int vitesse, int distanceEntreBagage, Boolean autoGeneration){
		Noeud noeud = listePointsNoeuds.get(point);
		if(noeud == null){
			createNode(point);
		}
		
		// Creation Toboggan
		TapisRoulant tapis = new TapisRoulant(length,vitesse,distanceEntreBagage,autoGeneration);
		hall.addFileBagage(tapis);
		
		//On créé un noeud associé à un toboggan
		ConnexionCircuit noeudExit = new ConnexionCircuit(tapis, circuit);
		tapis.setConnexionCircuit(noeudExit);
		
		noeudExit.setRailsSortie(noeud.getRailsSortie());
		//Attention bagages non copiés!!!
		
		replaceNode(point,noeudExit);
		
		VueTapisRoulant vTapis = new VueTapisRoulant(vueHall, tapis);
		vTapis.setX(point.x);
		vTapis.setY(point.y);
		vueHall.ajouterVue(vTapis, 3);
		ViewSelector.getInstance().setKernelView(tapis, vTapis);
		
		return tapis;
	}
	
	public static Toboggan createExit(Point point){
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
		vTobo.setX(point.x);
		vTobo.setY(point.y);
		vueHall.ajouterVue(vTobo, 3);
		ViewSelector.getInstance().setKernelView(tobo, vTobo);
		
		return tobo;
	}
	
	public static void addChariot(Noeud noeud, int maxMoveDistance, int length, Noeud destination, Bagage bagage, LinkedList<ElementCircuit>  cheminPrevu){
		Chariot chariot = new Chariot( maxMoveDistance, length, 0, destination, bagage, cheminPrevu);

		// On place les chariots
		noeud.registerChariot(chariot);
		chariot.setParent(noeud);

		// On ajoute les vues
		VueChariot vueChariot = new VueChariot(vueHall, chariot);
		vueHall.ajouterVue(vueChariot, 3);
		hall.getChariotList().add(chariot);
		ViewSelector.getInstance().setKernelView(chariot, vueChariot);
	}
	
	public static void addChariot(Rail rail, int maxMoveDistance, int length, int position, Noeud destination, Bagage bagage, LinkedList<ElementCircuit>  cheminPrevu){
		Chariot chariot = new Chariot( maxMoveDistance, length, position, destination, bagage, cheminPrevu);
		
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
}