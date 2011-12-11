package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
public class Circuit extends KernelObject {

	@Getter
	@Setter
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
	 * Le premier élément de la liste renvoyée est le noeud depart.
	 * Le dernier élément de la liste renvoyée est le noeud arrivee.
	 * @param depart Le noeud duquel on part.
	 * @param arrivee Le noeud auquel on souhaite arriver.
	 * @return Renvoie le chemin calculé. Si le noeud de départ est le même que
	 * le noeud d'arrivée, alors la méthode renvoie null.
	 */
	public LinkedList<ElementCircuit> calculChemin(Noeud depart, Noeud arrivee) {
		
		if(depart.equals(arrivee)) {
			return null;
		}
		
		//On récupère uniquement les noeuds du Circuit
		ArrayList<Noeud> noeuds = new ArrayList<Noeud>();
		for (int i = 0; i < elements.size(); i++) {
			if(elements.get(i) instanceof Noeud) {
				noeuds.add((Noeud)elements.get(i));
			}
		}
		
		//Queue de traitement
		LinkedList<Noeud> queue = new LinkedList<Noeud>();
		//Liste des noeuds visités associés à leur noeud antécédant
		HashMap<Noeud, Noeud> noeudEtPrecedent = new HashMap<Noeud, Noeud>();
		
		//algorithme de parcours en profondeur (=Dijkstra?)
		queue.add(depart);
		noeudEtPrecedent.put(depart, null);
		while(queue.size() != 0) {
			Noeud n = queue.pop();
			if(n.equals(arrivee)) { //On arrête le parcours en profondeur
				break;
			}
			for (int i = 0; i < n.getRailsSortie().size(); i++) {
				Noeud voisin = n.getRailsSortie().get(i).getNoeudSuivant();
				if(!noeudEtPrecedent.containsKey(voisin)) {
					queue.add(voisin);
					noeudEtPrecedent.put(voisin, n);
				}
			}
		}
		
		//Reconstruction du chemin
		LinkedList<ElementCircuit> chemin = new LinkedList<ElementCircuit>();
		Noeud courant = arrivee;
		while(courant != depart) {
			chemin.addFirst(courant);
			Noeud noeudPrecedent = noeudEtPrecedent.get(courant);
			Rail railPrecedent = null;
			for(int i=0; i<noeudPrecedent.getRailsSortie().size() && railPrecedent == null; i++) {
				if(noeudPrecedent.getRailsSortie().get(i).getNoeudSuivant().equals(courant)) {
					railPrecedent = noeudPrecedent.getRailsSortie().get(i);
				}
			}
			chemin.addFirst(railPrecedent);
			courant = noeudPrecedent;
		}
		chemin.addFirst(courant); //On ajoute le départ à la liste
		
		return chemin;
	}

}