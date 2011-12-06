package aeroport.sgbag.kernel;

import java.util.*;
import org.apache.log4j.*;
import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
// Utilise par la classe Circuit
@ToString
public class Noeud extends ElementCircuit {
	
	static Logger log = Logger.getLogger(Noeud.class);

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
		if (hasChariot() && ++ticksToUpdate >= tickThresholdToUpdate) {
			ticksToUpdate = 0;
			return moveToNextRail();
		} else if(!hasChariot()){
			ticksToUpdate = 0;
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

			log.debug("Le chariot " + chariot + " sort du noeud " + this);
			unregisterChariot();
		}

		return (prochainRail != null);
	}

	public Boolean registerChariot(Chariot c) {
		if (!hasChariot()) { // S'il n'y a pas de chariot sur le noeud on peut
								// l'ajouter
			log.debug("Le chariot " + c + " arrive dans le noeud " + this);
			return super.registerChariot(c);
		}

		return false;
	}

	public void addRailSortie(Rail r) {
		railsSortie.add(r);
	}
}
