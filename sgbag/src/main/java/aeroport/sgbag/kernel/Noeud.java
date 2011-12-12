package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;
import lombok.extern.log4j.*;

@AllArgsConstructor
@Log4j
public class Noeud extends ElementCircuit {
	protected static String kObjName = "Nœud";

	@Setter
	@Getter
	private LinkedList<Rail> railsSortie;

	protected final int tickThresholdToUpdate = 10;
	protected int ticksToUpdate = 0;

	public Noeud() {
		this(null);
	}
	
	public Noeud(Circuit parent) {
		super(parent);
		railsSortie = new LinkedList<Rail>();
	}

	public Boolean update() {
		log.trace("Update du noeud " + this + " temps restant : " + (tickThresholdToUpdate-ticksToUpdate));
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
			
			chariot.setParent(prochainRail);

			log.debug("Le chariot sort du noeud " + this + " par le rail " + prochainRail);
			unregisterChariot();
		}

		return (prochainRail != null);
	}

	public Boolean registerChariot(Chariot c) {
		if (!hasChariot()) { // S'il n'y a pas de chariot sur le noeud on peut
								// l'ajouter
			log.debug("Entrée dans le noeud " + this + " du chariot " + c);
			return super.registerChariot(c);
		}

		return false;
	}

	public void addRailSortie(Rail r) {
		railsSortie.add(r);
	}
	
	public boolean isFull() {
		return this.hasChariot();
	}
}
