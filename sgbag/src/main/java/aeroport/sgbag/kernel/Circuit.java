package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;


@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Circuit {

	@Getter
	private ArrayList<ElementCircuit>  elements;

	/**
	 * Appelle la méthode update de tous les éléments du circuit.
	 * @return Renvoie true si tous les éléments ont été correctement mis à jour.
	 */
	public Boolean update() {
		Boolean allUpdated = true;
		for (int i = 0; i < elements.size(); i++) {
			if(!elements.get(i).update()) {
				allUpdated = false;
			}
		}
		return allUpdated;
	}

	/**
	 * Renvoie un chemin entre le noeud depart et le noeud arrivee.
	 * @param depart Le noeud duquel on part.
	 * @param arrivee Le noeud auquel on souhaite arriver.
	 * @return Renvoie le chemin 
	 */
	public ArrayList<ElementCircuit> calculChemin(Noeud depart, Noeud arrivee) {
		return null;
	}

}