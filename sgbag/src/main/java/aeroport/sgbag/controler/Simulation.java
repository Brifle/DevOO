package aeroport.sgbag.controler;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import lombok.*;
import lombok.extern.log4j.Log4j;
import aeroport.sgbag.gui.PropertiesWidget;
import aeroport.sgbag.kernel.*;
import aeroport.sgbag.utils.CircuitGenerator;
import aeroport.sgbag.views.*;

/**
 * Create things like kernel instances and views.
 * 
 * @author Arnaud Lahache & Stanislas Signoud
 * 
 */
@RequiredArgsConstructor
@Log4j
public class Simulation {

	public enum Mode {
		MANUEL, AUTO
	}

	public enum Etat {
		NORMAL, SELECTION, CHOIX_DESTINATION
	}

	@Getter
	@Setter
	private File xmlFile;

	@Getter
	@NonNull
	private VueHall vueHall;

	@Getter
	private Hall hall;

	@Getter
	private Mode mode;

	@Getter
	@Setter
	private Etat etat;

	@Getter
	private Clock clock;

	@Getter
	private VueElem selectedElem;
	
	@Setter
	private PropertiesWidget propertiesWidget;

	public void init() {

		// TODO init vueHall with XML file
		
		vueHall.setSimulation(this);
		hall = vueHall.getHall();
		
		if(hall != null){
			hall.update();
		}
		vueHall.update();
		vueHall.draw();

		clock = new Clock(100, hall, vueHall);
	}

	public void play() {
		if(clock != null){
			clock.run();
		}
	}

	public void pause() {
		if(clock != null){
			clock.pause();
		}
	}

	public void stop() {
		if(clock != null){
			clock.pause();
			// TODO clear all bagages
		}
	}
	
	public void setSpeed(int refreshInterval) {
		clock.setInterval(refreshInterval);
		log.debug("Set interval to "+refreshInterval+"ms.");
	}

	public void setSelectedElem(VueElem _selectedElem) {
		if (selectedElem != null)
			selectedElem.setSelected(false);
		selectedElem = _selectedElem;
		if (selectedElem != null)
			selectedElem.setSelected(true);
		
		propertiesWidget.refresh();
	}

	public boolean createBagage(VueElem depart, VueElem destination) {

		if (!(destination instanceof VueToboggan)
				|| !(depart instanceof VueTapisRoulant)) {
			return false;
		}
		VueToboggan vueToboggan = (VueToboggan) destination;
		VueTapisRoulant vueTapisRoulant = (VueTapisRoulant) depart;
		
		// Create the bagage in kernel :
		Bagage b = new Bagage();
		b.setDestination(vueToboggan.getToboggan().getConnexionCircuit());
		vueTapisRoulant.getTapisRoulant().addBagage(b);
		b.setParent(vueTapisRoulant.getTapisRoulant());

		// Create the vueBagage :
		VueBagage v = new VueBagage(vueHall);
		vueHall.ajouterVue(v, 4);
		ViewSelector.getInstance().setKernelView(b, v);

		return true;
	}
	
	public void setMode(Mode mode) {
		if(mode == Mode.MANUEL) {
			//Passer en mode manuel
			vueHall.getHall().setAutomatique(false);
			ArrayList<Chariot> chariots = vueHall.getHall().getChariotList();
			for (int i = 0; i < chariots.size(); i++) {
				chariots.get(i).setCheminPrevu(null);
			}
		} else if(mode == Mode.AUTO) {
			vueHall.getHall().setAutomatique(true);
			ArrayList<Chariot> chariots = vueHall.getHall().getChariotList();
			for (int i = 0; i < chariots.size(); i++) {
				Noeud nouvelleDestination;
				if(chariots.get(i).getBagage() == null) {
					//Get prochain tapis
					nouvelleDestination = BagageFactory.getBagageFactory().getTapis().getConnexionCircuit();
				} else {
					//Get prochain toboggan
					nouvelleDestination = BagageFactory.getBagageFactory().getTobogan().getConnexionCircuit();
				}
				//Y aller !
				Noeud noeudChariot = null;
				if(chariots.get(i).getParent() instanceof Noeud) {
					noeudChariot = (Noeud)chariots.get(i).getParent();
				} else {
					noeudChariot = ((Rail)chariots.get(i).getParent()).getNoeudSuivant();
				}
				chariots.get(i).setCheminPrevu(
						vueHall.getHall().getCircuit().calculChemin(
								noeudChariot,
								nouvelleDestination));
				chariots.get(i).setDestination(nouvelleDestination);
			}
			
		} else {
			log.warn("Mode non existant.");
			return;
		}
		this.mode = mode;
	}
	
	

}
