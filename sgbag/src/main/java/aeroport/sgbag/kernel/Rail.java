package aeroport.sgbag.kernel;

import java.util.*;
import org.apache.log4j.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Rail extends ElementCircuit {
	
	static Logger log = Logger.getLogger(Rail.class);

	@Getter
	@Setter
	private Noeud noeudSuivant;

	@Getter
	@Setter
	private Noeud noeudPrecedent;

	@Getter
	@Setter
	private int length;

	public Boolean update() {

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
				c.setPosition(chariotSuivant.getRearPosition() - 3);
				log.debug("Collision entre deux chariots : " + c + " et " + chariotSuivant);
			} else {
				if (newPosition >= length) { // Le Chariot sort
					log.debug("Le chariot " + c + " sort du rail " + this);
					if (noeudSuivant.registerChariot(c)) {
						ite.remove();
					} else {
						c.setPosition(length - c.getLength() / 2);
					}
				} else { // Cas nominal
					log.info("Le chariot " + c + " avance sur le rail " + this);
					if (newPosition > (length - c.getLength() / 2))
						newPosition = length - c.getLength() / 2;
						
					c.setPosition(newPosition);
				}
			}
		}

		return true;
	}
}
