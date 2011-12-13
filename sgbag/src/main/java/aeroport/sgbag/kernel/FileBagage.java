package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;

public abstract class FileBagage extends KernelObject {
	protected static String kObjName = "File de bagage";

	@Getter
	@Setter
	private ConnexionCircuit connexionCircuit;

	protected LinkedList<Bagage> listBagages;

	public FileBagage() {
		listBagages = new LinkedList<Bagage>();
	}

	public void addBagage(Bagage b) {
		listBagages.add(b);
	}

	public Bagage popBagage() {
		return listBagages.pop();
	}

	public boolean removeBagage(Bagage b) {
		return listBagages.remove(b);
	}

	public Bagage removeBagage(int indice) {
		return listBagages.remove(indice);
	}

	public void removeAllBagage() {
		listBagages.clear();
	}

	public abstract boolean update();

}