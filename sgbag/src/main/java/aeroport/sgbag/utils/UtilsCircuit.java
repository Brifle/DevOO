package aeroport.sgbag.utils;

import java.util.ArrayList;

import aeroport.sgbag.kernel.Bagage;
import aeroport.sgbag.kernel.Circuit;
import aeroport.sgbag.kernel.ConnexionCircuit;
import aeroport.sgbag.kernel.ElementCircuit;
import aeroport.sgbag.kernel.FileBagage;
import aeroport.sgbag.kernel.Hall;
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

	public static UtilsCircuit getBagageFactory() {
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

	public TapisRoulant getTapis() {
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
