package aeroport.sgbag.kernel;

import java.util.LinkedList;

public abstract class ElementCircuit {

	private LinkedList<Chariot> listeChariot;

	public Boolean update() {
		return null;
	}

	public Boolean registerChariot(Chariot chariot) {
		int oldSize = listeChariot.size();
		
		listeChariot.addFirst(chariot);
		
		return (listeChariot.size() == oldSize + 1);
	}

	public Boolean unregisterChariot() {
		int oldSize = listeChariot.size();
		
		listeChariot.removeLast();
		
		return (listeChariot.size() == oldSize - 1);
	}

	public Boolean hasChariot() {
		return (listeChariot.size() > 0);
	}

}