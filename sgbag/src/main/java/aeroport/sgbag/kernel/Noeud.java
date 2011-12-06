package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
// Utilise par la classe Circuit
@ToString
public class Noeud extends ElementCircuit {

	@Setter
	@Getter
	private LinkedList<Rail> railsSortie;

	private final int tickThresholdToUpdate = 10;
	private int ticksToUpdate = 0;

	public Noeud() {
		super();
		railsSortie = new LinkedList<Rail>();
	}

	public Boolean update() {
		if (++ticksToUpdate >= tickThresholdToUpdate) {
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

		if (prochainRail != null) {

			if (!railsSortie.contains(prochainRail)) {
				return false;
			}

			if (!prochainRail.registerChariot(chariot)) {
				return false;
			}

			unregisterChariot();
		}

		return (prochainRail != null);
	}

	public Boolean registerChariot(Chariot c) {
		if (!hasChariot()) { // S'il n'y a pas de chariot sur le noeud on peut
								// l'ajouter
			return super.registerChariot(c);
		}

		return false;
	}

	public void addRailSortie(Rail r) {
		railsSortie.add(r);
	}
}
