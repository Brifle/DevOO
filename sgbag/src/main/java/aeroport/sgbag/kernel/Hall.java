package aeroport.sgbag.kernel;

import java.util.*;

import aeroport.sgbag.utils.UtilsCircuit;

import lombok.*;

@AllArgsConstructor
public class Hall extends KernelObject {
	protected static String kObjName = "Hall";

	@Getter
	@Setter
	private Circuit circuit;

	@Getter
	@Setter
	private ArrayList<Bagage> bagagesList;

	@Getter
	private ArrayList<FileBagage> fileBagageList;
	
	@Getter 
	private ArrayList<Chariot> chariotList;
	
	@Getter
	@Setter
	private boolean isAutomatique = true;

	public Hall() {
		bagagesList = new ArrayList<Bagage>();
		fileBagageList = new ArrayList<FileBagage>();
		chariotList = new ArrayList<Chariot>();
	}

	public void init() {
		UtilsCircuit.getUtilsCircuit().setCircuit(circuit);
	}

	public boolean update() {
		boolean allUpdated = true;
		for (int i = 0; i < fileBagageList.size(); i++) {
			if (!fileBagageList.get(i).update()) {
				allUpdated = false;
			}
		}
		if (!circuit.update()) {
			allUpdated = false;
		}
		return allUpdated;
	}

	public void addFileBagage(FileBagage file) {
		fileBagageList.add(file);
	}

	public void removeFileBagage(FileBagage file) {
		fileBagageList.remove(file);
	}

	/**
	 * Permet d'obtenir la ConnexionCircuit associée au TapisRoulant qui a le plus besoin d'avoir un chariot.
	 * Un TapisRoulant avec 3 bagages dessus sera prioritaire a un autre TapisRoulant 
	 * qui a 5 bagages mais a déjà 4 chariots qui s'y dirigent.
	 * A chaque fois qu'on appelle cette methode, on suppose que un chariot va se diriger vers le
	 * TapisRoulant associé. N'appeler donc la methode qu'une fois par charriot.
	 * @return la ConnexionCircuit associée au TapisRoulant le plus necessité.
	 */
	public ConnexionCircuit getOptimalNextTapisRoulant() {
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

		return t.getConnexionCircuit();
	}

}
