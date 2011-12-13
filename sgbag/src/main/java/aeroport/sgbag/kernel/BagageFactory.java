package aeroport.sgbag.kernel;

import java.util.ArrayList;

import lombok.extern.log4j.Log4j;

@Log4j
public class BagageFactory {

	private static BagageFactory instance = null;

	private Circuit circuit;

	private ArrayList<TapisRoulant> lTapis = new ArrayList<TapisRoulant>();
	private ArrayList<Toboggan> lTobogan = new ArrayList<Toboggan>();

	private BagageFactory() {
	}

	public static BagageFactory getBagageFactory() {
		if (instance == null) {
			instance = new BagageFactory();
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

	public TapisRoulant getTapis() {
		if (!this.lTapis.isEmpty()) {
			int random = (int) (Math.random() * this.lTapis.size());

			return lTapis.get(random);
		}

		return null;
	}

	public Toboggan getTobogan() {
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

	public Bagage generateBagage(Hall hall) {
		//if (!lTobogan.isEmpty()) {
			Bagage b = new Bagage();
			hall.getBagagesList().add(b);
			
			log.debug("Ajout d'un bagage métier.");
			b.setDestination(getTobogan().getConnexionCircuit());

			return b;
		//} else {
		//	return null;
		//}
	}

}
