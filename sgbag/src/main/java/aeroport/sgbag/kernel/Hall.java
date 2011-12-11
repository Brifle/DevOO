package aeroport.sgbag.kernel;

import java.util.*;

import lombok.*;

@AllArgsConstructor
public class Hall extends KernelObject {

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

	public Hall() {
		bagagesList = new ArrayList<Bagage>();
		fileBagageList = new ArrayList<FileBagage>();
		chariotList = new ArrayList<Chariot>();
	}

	public void init() {
		BagageFactory.getBagageFactory().setCircuit(circuit);
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