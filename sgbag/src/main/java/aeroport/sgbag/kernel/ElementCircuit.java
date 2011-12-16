package aeroport.sgbag.kernel;

import java.util.LinkedList;

import lombok.Getter;
import lombok.Setter;

/**
 * Modèle représentant un élément du circuit.
 * 
 * Toutes les classes représentant un élément du circuit (tels que les
 * rails, les nœuds, les connexions de circuit...) héritent de cette
 * classe.
 * 
 * @author Mathieu Sabourin, Thibaut Patel
 */
public abstract class ElementCircuit extends KernelObject {
	protected static String kObjName = "Élement de circuit";

	@Getter
	protected LinkedList<Chariot> listeChariot;
	
	@Getter
	@Setter
	private Circuit parent;

	public abstract Boolean update();
	
	/**
	 * Construit un élément de circuit.
	 */
	public ElementCircuit() {
		super();
		listeChariot = new LinkedList<Chariot>();
	}
	
	/**
	 * Construit un élément de circuit.
	 * @param parent Circuit contenant l'élément.
	 */
	public ElementCircuit(Circuit parent) {
		this();
		this.parent = parent;
	}

	/**
	 * Ajoute un chariot à la liste des chariots situés actuellement
	 * sur l'élément de circuit.
	 * @param chariot Chariot à considérer comme sur l'élément
	 * de circuit.
	 * @return true si l'opération s'est déroulée avec succès.
	 */
	public Boolean registerChariot(Chariot chariot) {
		int oldSize = listeChariot.size();

		listeChariot.addFirst(chariot);

		return (listeChariot.size() == oldSize + 1);
	}

	/**
	 * Retire le dernier chariot ajouté à la liste des chariots 
	 * situés actuellement sur l'élément de circuit.
	 * @return true si l'opération s'est bien déroulée.
	 */
	public Boolean unregisterChariot() {
		int oldSize = listeChariot.size();
		
		// Le dernier élément ajouté est le premier élément de la liste
		listeChariot.getLast().setPosition(0);

		listeChariot.removeLast();

		return (listeChariot.size() == oldSize - 1);
	}

	/**
	 * Indique si au moins un chariot est situé sur l'élément.
	 * @return true si au moins un chariot est situé sur l'élément.
	 */
	public Boolean hasChariot() {
		return (listeChariot.size() > 0);
	}
	
	/**
	 * Représente l'élement sous forme d'une chaîne de caractère
	 * lisible par l'humain.
	 */
	@Override
	public String toString() {
		return "<ElementCircuit id=" + super.getId() + "/>";
	}
}