package aeroport.sgbag.controler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import aeroport.sgbag.gui.PropertiesWidget;
import aeroport.sgbag.kernel.Bagage;
import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.ElementCircuit;
import aeroport.sgbag.kernel.FileBagage;
import aeroport.sgbag.kernel.Hall;
import aeroport.sgbag.kernel.Noeud;
import aeroport.sgbag.kernel.Rail;
import aeroport.sgbag.kernel.TapisRoulant;
import aeroport.sgbag.utils.UtilsCircuit;
import aeroport.sgbag.views.VueChariot;
import aeroport.sgbag.views.VueElem;
import aeroport.sgbag.views.VueHall;
import aeroport.sgbag.views.VueRail;
import aeroport.sgbag.views.VueTapisRoulant;
import aeroport.sgbag.views.VueToboggan;
import aeroport.sgbag.xml.CircuitArchive;
import aeroport.sgbag.xml.MalformedCircuitArchiveException;

/**
 * Gère la simulation au sein du prototype.
 * 
 * La gestion de la simulation consiste à l'exportation de l'IHM de méthodes 
 * de démarrage, pause, arrêt et de quelques informations concernant le 
 * contenu de la simulation.
 * 
 * @author Arnaud Lahache, Jonàs Bru Monserrat, Mathieu Sabourin, Stanislas Signoud
 * 
 */
@RequiredArgsConstructor
@Log4j
public class Simulation {

	public enum Mode {
		MANUEL, AUTO
	}

	public enum Etat {
		NORMAL, SELECTION, CHOIX_DESTINATION_BAGAGE, CHOIX_DESTINATION_CHARIOT
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
	
	@Getter
	@Setter
	private LinkedList<Rail> railsFleches;

	@Setter
	private PropertiesWidget propertiesWidget;

	/**
	 * Initialise la simulation.
	 * 
	 * Si un fichier xml a été renseigné au sein de la propriété xmlFile, ce fichier
	 * sera lu, désérialisé et son contenu intégré à la simulation. 
	 * 
	 * Un tic d'horloge allégé est simulé pour effectuer la mise en place de la vue.
	 * 
	 * Cette méthode doit être exécutée avant l'appel des méthodes play, pause et
	 * stop pour garantir leur bon fonctionnement.
	 * 
	 * @throws MalformedCircuitArchiveException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void init() throws MalformedCircuitArchiveException, FileNotFoundException, IOException {

		if(xmlFile != null){
			CircuitArchive ca = CircuitArchive.readFromXML(xmlFile.getPath());
		
			ca.unpackTo(vueHall);
		}

		vueHall.setSimulation(this);
		
		hall = vueHall.getHall();

		/* L'horloge est créée dans un état de pause. */
		clock = new Clock(100, hall, vueHall, true);
		etat = Etat.NORMAL;
		mode = Mode.AUTO;
	}

	/**
	 * Démarre la simulation.
	 * 
	 * Si celle-ci n'a pas été initialisée avec la méthode init(), rien
	 * n'est effectué.
	 */
	public void play() {
		if (clock != null) {
			clock.unpause();
			clock.run();
		}
	}

	/**
	 * Pause la simulation.
	 * 
	 * Si celle-ci n'a pas été initialisée avec la méthode init(), rien
	 * n'est effectué.
	 */
	public void pause() {
		if (clock != null) {
			clock.pause();
		}
	}
	
	/**
	 * Stoppe la simulation.
	 * 
	 * Si celle-ci n'a pas été initialisée avec la méthode init(), rien
	 * n'est effectué.
	 */
	public void stop() {
		if (clock != null) {
			clock.pause();

			getHall().getBagagesList().clear();
			
			for(FileBagage fb : getHall().getFileBagageList()) {
				fb.removeAllBagage();
			}
		}
	}
	
	/**
	 * Modifie l'intervalle de rafraîchissement de la simulation.
	 * 
	 * @param refreshInterval Le nouvel interval de rafraîchissement,
	 * en millisecondes.
	 */
	public void setSpeed(int refreshInterval) {
		clock.setInterval(refreshInterval);
		log.debug("Set interval to "+refreshInterval+"ms.");
	}

	/**
	 * Modifie la sélection, pour sélectionner l'élément _selectedItem.
	 * 
	 * @param _selectedElem Nouvel élément sélectionné.
	 */
	public void setSelectedElem(VueElem _selectedElem) {
		if (selectedElem != null)
			selectedElem.setSelected(false);
		selectedElem = _selectedElem;
		if (selectedElem != null)
			selectedElem.setSelected(true);

		propertiesWidget.refresh();
	}

	/**
	 * Crée un bagage partant de la vue depart à la vue destination.
	 *  
	 * @param depart Vue représentant l'élément du circuit duquel
	 * partira le bagage.
	 * @param destination Vue représentant l'élément du circuit vers
	 * lequel se dirigera le bagage.
	 * @return true si le bagage est créé, faux si la destination n'est
	 * pas la représentation d'un toboggan ou, pour le départ, la 
	 * représentation d'un tapis roulant.  
	 */
	public boolean createBagage(VueElem depart, VueElem destination) {

		if (!(destination instanceof VueToboggan)
				|| !(depart instanceof VueTapisRoulant)) {
			return false;
		}
		VueToboggan vueToboggan = (VueToboggan) destination;
		VueTapisRoulant vueTapisRoulant = (VueTapisRoulant) depart;

		// Create the bagage in kernel :
		Bagage b = UtilsCircuit.getUtilsCircuit().generateBagage(
				vueToboggan.getToboggan().getConnexionCircuit());
		vueTapisRoulant.getTapisRoulant().addBagage(b);
		b.setParent(vueTapisRoulant.getTapisRoulant());

		return true;
	}
	
	/**
	 * Crée un bagage, partant de la vue depart.
	 * 
	 * La destination du bagage est décidée de manière automatique.
	 * 
	 * @param depart Vue représentant l'élément d'où partira le
	 * bagage. 
	 * @return false si le bagage n'a pas pu être créé (si l'élément
	 * de départ n'est pas un tapis roulant, ou si ce tapis roulant
	 * n'a pas de place pour un nouveau bagage).
	 */
	public boolean createBagage(VueElem depart) {

		if (!(depart instanceof VueTapisRoulant)) {
			return false;
		}
		
		VueTapisRoulant vueTapisRoulant = (VueTapisRoulant) depart;

		if(!vueTapisRoulant.getTapisRoulant().hasPlaceForBagage()){
			return false;
		}
		// Create the bagage in kernel :
		Bagage b = UtilsCircuit.getUtilsCircuit().generateBagage();
		vueTapisRoulant.getTapisRoulant().addBagage(b);
		b.setParent(vueTapisRoulant.getTapisRoulant());

		return true;
	}

	/**
	 * Crée un chariot sur l'élément depart.
	 * @param depart L'élément surlequel sera créé le chariot.
	 * @return false si l'élément donné n'est pas un rail.
	 */
	public boolean createChariot(VueElem depart) {

		if (!(depart instanceof VueRail)) {
			return false;
		}

		VueRail vueRail = (VueRail) depart;

		Chariot c = null;
		
		// Create the bagage in kernel :
		if (this.mode == Simulation.Mode.AUTO) {
			c = UtilsCircuit.getUtilsCircuit().generateChariot();
			c.setCheminPrevu(this.hall.getCircuit().calculChemin(vueRail.getRail().getNoeudSuivant(), c.getDestination()));
			
		} else if (this.mode == Simulation.Mode.MANUEL) {
			c = UtilsCircuit.getUtilsCircuit().generateChariot(null);
		} else {
			return false;
		}

		vueRail.getRail().registerChariot(c);
		c.setParent(vueRail.getRail());

		VueChariot v = new VueChariot(vueHall, c);
		vueHall.ajouterVue(v, 3);
		ViewSelector.getInstance().setKernelView(c, v);
		
		return true;
	}

	/**
	 * Modifie le mode de la simulation, entre automatique et manuel.
	 * 
	 * @param mode Nouveau mode de la simulation.
	 */
	public void setMode(Mode mode) {
		deleteFleches();
		
		if (mode == Mode.MANUEL) {
			// Passer en mode manuel
			vueHall.getHall().setAutomatique(false);
			for (int i = 0; i < vueHall.getHall().getFileBagageList().size(); i++) {
				if(vueHall.getHall().getFileBagageList().get(i) instanceof TapisRoulant) {
					TapisRoulant tapis = (TapisRoulant) vueHall.getHall().getFileBagageList().get(i);
					tapis.setAutoBagageGeneration(false);
				}
			}
			ArrayList<Chariot> chariots = vueHall.getHall().getChariotList();
			//On arrête les chariots au prochain noeud
			for (int i = 0; i < chariots.size(); i++) {
				if(chariots.get(i).getCheminPrevu() != null && chariots.get(i).getCheminPrevu().size() > 0 && chariots.get(i).getCheminPrevu().peek() instanceof Noeud) {
					//On va arriver sur un noeud
					LinkedList<ElementCircuit> newChemin = new LinkedList<ElementCircuit>();
					newChemin.add(chariots.get(i).getCheminPrevu().peek());
					chariots.get(i).setCheminPrevu(newChemin);
				} else {
					//On est sur un noeud
					chariots.get(i).setCheminPrevu(null);
				}
			}
		} else if (mode == Mode.AUTO) {
			vueHall.getHall().setAutomatique(true);
			
			for (int i = 0; i < vueHall.getHall().getFileBagageList().size(); i++) {
				if(vueHall.getHall().getFileBagageList().get(i) instanceof TapisRoulant) {
					TapisRoulant tapis = (TapisRoulant) vueHall.getHall().getFileBagageList().get(i);
					tapis.setAutoBagageGeneration(true);
				}
			}
			
			UtilsCircuit.getUtilsCircuit().resetTapisRoulantIncomingChariotsNumber();
			
			ArrayList<Chariot> chariots = vueHall.getHall().getChariotList();

			for (int i = 0; i < chariots.size(); i++) {
				Noeud nouvelleDestination;
				if (chariots.get(i).getBagage() == null) {
					// Get prochain tapis
					nouvelleDestination = UtilsCircuit.getUtilsCircuit()
							.getTapisRoulantOptimalNext().getConnexionCircuit();
				} else {
					// Get prochain toboggan
					nouvelleDestination = UtilsCircuit.getUtilsCircuit()
							.getRandomExistingTobogan().getConnexionCircuit();
				}
				// Y aller !
				Noeud noeudChariot = null;
				if (chariots.get(i).getParent() instanceof Noeud) {
					noeudChariot = (Noeud) chariots.get(i).getParent();
				} else {
					noeudChariot = ((Rail) chariots.get(i).getParent())
							.getNoeudSuivant();
				}
				chariots.get(i)
						.setCheminPrevu(
								vueHall.getHall()
										.getCircuit()
										.calculChemin(noeudChariot,
												nouvelleDestination));
				chariots.get(i).setDestination(nouvelleDestination);
			}

		} else {
			log.warn("Mode non existant.");
			return;
		}
		this.mode = mode;
	}
	
	/**
	 * Mets à jour les propriétés affichées à l'écran.
	 */
	public void refreshProperties(){
		this.propertiesWidget.refresh();
	}
	
	/**
	 * Affiche les flèches correspondant aux rails surlesquels peut
	 * se déplacer le chariot, en mode manuel.
	 * @param rails Rails surlesquels les flèches seront affichées.
	 */
	public void displayFleches(LinkedList<Rail> rails) {
		railsFleches = rails;
		for (Rail r : railsFleches) {
			VueRail vueR = (VueRail) ViewSelector
					.getInstance()
					.getViewForKernelObject(r);
			vueR.setDisplayingArrow(true);
		}
	}
	
	/**
	 * Supprime les flèches affichées par displayFleches.
	 */
	public void deleteFleches() {
		if(railsFleches == null) return;
		for (Rail r : railsFleches) {
			VueRail vueR = (VueRail) ViewSelector
					.getInstance()
					.getViewForKernelObject(r);
			vueR.setDisplayingArrow(false);
		}
		railsFleches = null;
	}

}
