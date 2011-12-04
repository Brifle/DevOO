package aeroport.sgbag.kernel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Kernel class representing a bagage
 * 
 * @author mathieu
 * 
 */
@NoArgsConstructor
public class Bagage {

	public Bagage(Noeud destination) {
		this.destination = destination;
		position = 0;
	}

	@Getter
	@Setter
	private ConteneurBagage conteneur;

	@Setter
	@Getter
	private Noeud destination;

	@Getter
	private int position;

	public void moveBy(Integer distance) {
		position = distance;
	}

}