package aeroport.sgbag.kernel;

import java.util.ArrayList;

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

	@SuppressWarnings("unused")
	private TapisRoulant getTapis() { // Unused
		if (!this.lTapis.isEmpty()) {
			int random = (int) (Math.random() * this.lTapis.size());

			return lTapis.get(random);
		}

		return null;
	}

	private Toboggan getTobogan() {
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

	public Bagage generateBagage() {
		//if (!lTobogan.isEmpty()) {
			Bagage b = new Bagage();

			b.setDestination(getTobogan().getConnexionCircuit());

			return b;
		//} else {
		//	return null;
		//}
	}

}
