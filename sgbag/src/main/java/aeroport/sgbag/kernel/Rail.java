package aeroport.sgbag.kernel;

import java.util.Iterator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * Modèle représentant un rail.
 * 
 * @author Mathieu Sabourin, Thibaut Patel, Arnaud Lahache
 */
@AllArgsConstructor
@NoArgsConstructor
@Log4j
public class Rail extends ElementCircuit {
	protected static String kObjName = "Rail";

	@Getter
	@Setter
	private Noeud noeudSuivant;

	@Getter
	@Setter
	private Noeud noeudPrecedent;

	@Getter
	@Setter
	private int length;

	/**
	 * Construit un rail.
	 * @param length Taille du rail créé.
	 */
	public Rail(int length) {
		this(length, null);
	}
	
	/**
	 * Construit un rail.
	 * @param length Taille du rail créé.
	 * @param parent Circuit parent du rail.
	 */
	public Rail(int length, Circuit parent) {
		super(parent);
		this.length = length;
	}

	/**
	 * Mets à jour le rail, en déplaçant les chariots qu'il contient.
	 */
	public Boolean update() {
		log.trace("Update du rail " + this);

		Iterator<Chariot> ite = listeChariot.descendingIterator();

		while (ite.hasNext()) {
			Chariot c = ite.next();

			int newPosition = c.getPosition() + c.getMaxMoveDistance();
			Chariot chariotSuivant;

			try {
				chariotSuivant = listeChariot.get(listeChariot.indexOf(c) + 1);
			} catch (IndexOutOfBoundsException e) {
				chariotSuivant = null;
			}

			if (chariotSuivant != null
					&& c.willCollide(newPosition, chariotSuivant)) {
				//c.setPosition(chariotSuivant.getRearPosition() - 3);
				c.setPosition(chariotSuivant.getRearPosition()-c.getLength()/2-10);
				log.debug("Collision entre deux chariots : " + c + " et "
						+ chariotSuivant);
			} else {
				if (newPosition > (length - c.getLength() / 2)) { // Le Chariot sort
					log.debug("Sortie du rail " + this + "du chariot " + c);
					if (noeudSuivant.registerChariot(c)) {
						c.setParent(noeudSuivant);
						ite.remove();
					} else {
						c.setPosition(length - c.getLength() / 2);
					}
				} else { // Cas nominal

					// Dans le cas où le chariot est obligé d'attendre qu'un
					// chariot sort du noeud suivant :
					/*if (this.noeudSuivant.isFull()
							&& newPosition > (length - c.getLength() / 2)) {
						newPosition = length - c.getLength() / 2;
					}*/

					c.setPosition(newPosition);
					log.debug("Rail : " + this
							+ " le chariot suivant avance à " + newPosition
							+ " : " + c);
				}
			}
		}

		return true;
	}
}
