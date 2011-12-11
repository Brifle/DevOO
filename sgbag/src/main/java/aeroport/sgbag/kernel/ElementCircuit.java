package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;

@ToString(exclude="listeChariot")
public abstract class ElementCircuit extends KernelObject {

	@Getter
	protected LinkedList<Chariot> listeChariot;
	
	@Getter
	@Setter
	private Circuit parent;

	public abstract Boolean update();

	public ElementCircuit() {
		super();
		listeChariot = new LinkedList<Chariot>();
	}
	
	public ElementCircuit(Circuit parent) {
		this();
		this.parent = parent;
	}

	public Boolean registerChariot(Chariot chariot) {
		int oldSize = listeChariot.size();

		listeChariot.addFirst(chariot);

		return (listeChariot.size() == oldSize + 1);
	}

	public Boolean unregisterChariot() {
		int oldSize = listeChariot.size();
		
		listeChariot.getLast().setPosition(0); //Et oui

		listeChariot.removeLast();

		return (listeChariot.size() == oldSize - 1);
	}

	public Boolean hasChariot() {
		return (listeChariot.size() > 0);
	}

}