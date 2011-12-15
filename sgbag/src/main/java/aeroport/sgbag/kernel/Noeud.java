package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;
import lombok.extern.log4j.*;

/**
 * Modèle représentant un nœud du circuit.
 * 
 * Un noeud est une intersection entre deux rails ou plus.
 * 
 * @author Mathieu Sabourin, Thibaut Patel
 *
 */
@AllArgsConstructor
@Log4j
public class Noeud extends ElementCircuit {
	protected static String kObjName = "Nœud";

	@Setter
	@Getter
	private LinkedList<Rail> railsSortie;

	@Getter
	protected final int tickThresholdToUpdate = 10;
	
	@Getter
	protected int ticksToUpdate = 0;

	/**
	 * Construit un noeud vide, sans parent.
	 */
	public Noeud() {
		this(null);
	}
	
	/**
	 * Construit un noeud vide, avec son circuit parent.
	 * @param parent Circuit parent du noeud.
	 */
	public Noeud(Circuit parent) {
		super(parent);
		railsSortie = new LinkedList<Rail>();
	}
	
	/**
	 * Mets à jour le contenu du nœud.
	 * 
	 * Ceci est effectué à chaque tic d'horloge.
	 */
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

	/**
	 * Déplace le dernier chariot sur son prochain rail, si possible.
	 * @return true si cette opération est effectuée avec succès.
	 */
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

	/**
	 * Enregistre le chariot auprès du nœud.
	 */
	public Boolean registerChariot(Chariot c) {
		if (!hasChariot()) { // S'il n'y a pas de chariot sur le noeud on peut
								// l'ajouter
			log.debug("Entrée dans le noeud " + this + " du chariot " + c);
			return super.registerChariot(c);
		}

		return false;
	}

	/**
	 * Ajoute un rail en sortie du noeud.
	 * @param r Rail à ajouter en sortie du noeud.
	 */
	public void addRailSortie(Rail r) {
		railsSortie.add(r);
	}
	
	/**
	 * Indique si le noeud contient déjà un chariot.
	 * @return true si le noeud contient un chariot.
	 */
	public boolean isFull() {
		return this.hasChariot();
	}
}
