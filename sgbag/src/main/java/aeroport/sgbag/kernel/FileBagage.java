package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;

/**
 * Modèle représentant une file de bagage.
 * 
 * Une file de bagage est un élément abstrait contenant des 
 * bagages.
 *
 * @author Mathieu Sabourin, Thibaut Patel, Jonàs Bru Monserrat. 
 */
public abstract class FileBagage extends KernelObject {
	protected static String kObjName = "File de bagage";

	@Getter
	@Setter
	private ConnexionCircuit connexionCircuit;

	protected LinkedList<Bagage> listBagages;

	/**
	 * Construit une file de bagages, vide.
	 */
	public FileBagage() {
		listBagages = new LinkedList<Bagage>();
	}

	/**
	 * Ajoute un bagage à la file.
	 * @param b Bagage à ajouter.
	 */
	public void addBagage(Bagage b) {
		listBagages.add(b);
	}

	/**
	 * Retourne un bagage de la file en l'enlevant de la liste.
	 * @return Le bagage retiré de la file.
	 */
	public Bagage popBagage() {
		return listBagages.pop();
	}

	/**
	 * Supprime un bagage.
	 * @param b Bagage à supprimer.
	 * @return true si le bagage a bien été supprimé, false sinon.
	 */
	public boolean removeBagage(Bagage b) {
		return listBagages.remove(b);
	}
	
	/**
	 * Supprime le indice'ème bagage.
	 * @param indice Index du bagage à supprimer.
	 * @return true si le bagage a bien été supprimé, false sinon.
	 */
	public Bagage removeBagage(int indice) {
		return listBagages.remove(indice);
	}

	/**
	 * Vide la file des bagages.
	 */
	public void removeAllBagage() {
		listBagages.clear();
	}

	public abstract boolean update();

}