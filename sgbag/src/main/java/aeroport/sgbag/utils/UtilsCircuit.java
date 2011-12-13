package aeroport.sgbag.utils;

import java.util.ArrayList;

import aeroport.sgbag.kernel.Bagage;
import aeroport.sgbag.kernel.Circuit;
import aeroport.sgbag.kernel.ConnexionCircuit;
import aeroport.sgbag.kernel.ElementCircuit;
import aeroport.sgbag.kernel.FileBagage;
import aeroport.sgbag.kernel.Noeud;
import aeroport.sgbag.kernel.TapisRoulant;
import aeroport.sgbag.kernel.Toboggan;

import lombok.extern.log4j.Log4j;

@Log4j
public class UtilsCircuit {

	private static UtilsCircuit instance = null;

	private Circuit circuit;

	private ArrayList<TapisRoulant> lTapis = new ArrayList<TapisRoulant>();
	private ArrayList<Toboggan> lTobogan = new ArrayList<Toboggan>();

	private UtilsCircuit() {
	}

	public static UtilsCircuit getUtilsCircuit() {
		if (instance == null) {
			instance = new UtilsCircuit();
		}

		return instance;
	}

	private void searchTapisAndTobogans() {
		for (ElementCircuit e : circuit.getElements()) {
			if (e instanceof ConnexionCircuit) {
				FileBagage f = ((ConnexionCircuit) e).getFileBagage();

				if (f instanceof Toboggan)
					lTobogan.add((Toboggan) f);

				if (f instanceof TapisRoulant)
					lTapis.add((TapisRoulant) f);
			}
		}
	}

	public TapisRoulant getTapisRoulantRandom() {
		if (!this.lTapis.isEmpty()) {
			int random = (int) (Math.random() * this.lTapis.size());

			return lTapis.get(random);
		}

		return null;
	}

	public Toboggan getRandomExistingTobogan() {
		if (!this.lTobogan.isEmpty()) {
			int random = (int) (Math.random() * this.lTobogan.size());

			return lTobogan.get(random);
		}

		return null;
	}

	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
		searchTapisAndTobogans();
	}
	
	/**
	 * Genere un bagage avec comme destination un tobogan aleatoire
	 * @return le bagage generé
	 */
	public Bagage generateBagage() {
		return generateBagage(getRandomExistingTobogan().getConnexionCircuit());
	}

	/**
	 * Genere un bagage avec une destination donnée
	 * @param destination la destination du bagage
	 * @return le bagage generé
	 */
	public Bagage generateBagage(Noeud destination) {
		Bagage b = new Bagage();
		this.circuit.getParent().getBagagesList().add(b);

		log.debug("Ajout d'un bagage métier.");
		b.setDestination(destination);

		return b;
	}
	
	/**
	 * Permet d'obtenir la ConnexionCircuit associée au TapisRoulant qui a le plus besoin d'avoir un chariot.
	 * Un TapisRoulant avec 3 bagages dessus sera prioritaire a un autre TapisRoulant 
	 * qui a 5 bagages mais a déjà 4 chariots qui s'y dirigent.
	 * A chaque fois qu'on appelle cette methode, on suppose que un chariot va se diriger vers le
	 * TapisRoulant associé. N'appeler donc la methode qu'une fois par charriot.
	 * @return la ConnexionCircuit associée au TapisRoulant le plus necessité.
	 */
	public TapisRoulant getTapisRoulantOptimalNext() {
		TapisRoulant t = null;
		int noteMax = Integer.MIN_VALUE;

		for (ElementCircuit e : circuit.getElements()) {
			if (e instanceof ConnexionCircuit) {
				FileBagage f = ((ConnexionCircuit) e).getFileBagage();

				if (f instanceof TapisRoulant) {
					if (((TapisRoulant) f).getNoteBesoinBagages() > noteMax){
						t = (TapisRoulant) f;
						noteMax = ((TapisRoulant) f).getNoteBesoinBagages();
					}
						
				}
			}
		}
		
		t.chariotIncoming();

		return t;
	}
	
	public void resetTapisRoulantIncomingChariotsNumber(){
		for (TapisRoulant t : this.lTapis) {
			t.resetChariotIncomingNumber();
		}
	}

}
