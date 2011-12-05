package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
// Utilise par la classe Circuit
public class Noeud extends ElementCircuit {

	@Setter
	@Getter
	private LinkedList<Rail> railsSortie;

	@Setter
	@Getter
	private LinkedList<Rail> railsEntree;

	private int ticksToUpdate = 0;

	public Noeud() {
		super();
		railsEntree = new LinkedList<Rail>();
		railsSortie = new LinkedList<Rail>();
	}

	public Boolean update() {
		if (++ticksToUpdate >= 10) {
			ticksToUpdate = 0;
			if (hasChariot()) {
				return moveToNextRail();
			}
		}

		return true;
	}

	public Boolean moveToNextRail() {

		Chariot chariot = listeChariot.getLast();

		Rail prochainRail = chariot.getNextRail();

		if (!railsSortie.contains(prochainRail))
			return false;

		if (!prochainRail.registerChariot(chariot))
			return false;

		unregisterChariot();

		return true;
	}

	public Boolean registerChariot(Chariot c) {
		if (!hasChariot()) { // S'il n'y a pas de chariot sur le noeud on peut
								// l'ajouter
			return super.registerChariot(c);
		}

		return false;
	}

	public void addRailEntree(Rail r) {
		railsEntree.add(r);
	}

	public void addRailSortie(Rail r) {
		railsSortie.add(r);
	}
}
