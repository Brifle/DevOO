package aeroport.sgbag.kernel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modèle représentant un bagage.
 * 
 * @author Mathieu Sabourin, Arnaud Lahache
 */
@NoArgsConstructor
public class Bagage extends KernelObject {
	protected static String kObjName = "Bagage";

	public Bagage(Noeud destination) {
		super();
		this.destination = destination;
	}

	@Setter
	@Getter
	private Noeud destination;

	@Getter
	private int position = 0;
	
	@Getter
	@Setter
	private Object parent;

	/**
	 * Déplace le bagage.
	 * @param distance Distance de déplacement.
	 */
	public void moveBy(Integer distance) {
		position += distance;
	}

}