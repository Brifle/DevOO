package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;
import lombok.extern.log4j.*;

/**
 * Modèle représentant un chariot.
 * 
 * @author Mathieu Sabourin, Michael Fagno, Thibaut Patel, Arnaud Lahache
 */
@NoArgsConstructor
@ToString(exclude={"maxMoveDistance","halfLength", "cheminPrevu"})
@Log4j
public class Chariot extends KernelObject {

	@Getter
	@Setter
	private int maxMoveDistance;

	private double halfLength;

	@Getter
	@Setter
	private int position;

	@Getter
	@Setter
	private Noeud destination;

	@Getter
	@Setter
	private Bagage bagage;
	
	@Getter
	@Setter
	private ElementCircuit parent;

	@Getter
	@Setter
	private LinkedList<ElementCircuit> cheminPrevu;

	/**
	 * Crée un chariot.
	 * @param maxMoveDistance Vitesse du chariot ; distance maximale
	 * qu'il peut parcourir par tic d'horloge.
	 * @param destination Destination du chariot.
	 * @param cheminPrevu Chemin prévu pour se rendre à la destination
	 * du chariot.
	 */
	public Chariot(int maxMoveDistance,
			Noeud destination, LinkedList<ElementCircuit> cheminPrevu) {
		this(maxMoveDistance, 80, maxMoveDistance, destination, null,
				cheminPrevu);
	}


	/**
	 * Crée un chariot.
	 * @param maxMoveDistance Vitesse du chariot ; distance maximale
	 * qu'il peut parcourir par tic d'horloge.
	 * @param length Taille du chariot.
	 * @param position Position du chariot.
	 * @param destination Destination du chariot.
	 * @param bagage Bagage contenu dans le chariot.
	 * @param cheminPrevu Chemin prévu pour se rendre à la destination
	 * du chariot.
	 */
	public Chariot(int maxMoveDistance, int length,
			int position, Noeud destination, Bagage bagage,
			LinkedList<ElementCircuit> cheminPrevu) {
		super();
		this.maxMoveDistance = maxMoveDistance;
		this.halfLength = new Double(length) / 2;
		this.position = position;
		this.destination = destination;
		this.bagage = bagage;
		this.cheminPrevu = cheminPrevu;
		log.debug("Création du chariot " + this);
	}

	/**
	 * Retourne le prochain rail où doit se rendre le chariot.
	 * @return Le prochain rail.
	 */
	public Rail getNextRail() {
		log.trace("recherche du prochain rail " + cheminPrevu);
		if(cheminPrevu == null 
				|| cheminPrevu.size() == 0) {
			
			return null;
		}
		
		ElementCircuit nextElemC = cheminPrevu.pop();

		if (nextElemC instanceof Rail)
			return (Rail) nextElemC;

		return getNextRail();
	}

	/**
	 * Indique sur le chariot est vide ou contient un bagage.
	 * @return true si le chariot est vide, false s'il contient un bagage.
	 */
	public Boolean isEmpty() {
		return (bagage == null);
	}

	/**
	 * Retire un bagage, et l'ajoute à la file passée en paramètre.
	 * @param file File où est ajoutée le bagage.
	 * @return true si le chariot est vide, false sinon.
	 */
	public Boolean moveBagageToFile(FileBagage file) {
		file.addBagage(bagage);
		bagage.setParent(file);
		return removeBagage();
	}

	/**
	 * Supprime un bagage de chariot.
	 * @return true si le chariot est vide, false sinon.
	 */
	public Boolean removeBagage() {
		bagage = null;
		
		log.debug("Vidage du chariot " + this);

		return this.isEmpty();
	}

	/**
	 * Teste si le chariot est en collision avec le chariot suivant.
	 * @param chariotSuivant Chariot avec lequel tester si une 
	 * collision se produit.
	 * @return true si une collision existe, false sinon.
	 */
	public Boolean isColliding(Chariot chariotSuivant) {
		if (position + halfLength > chariotSuivant.position
				- chariotSuivant.halfLength)
			return true;

		return false;
	}

	/**
	 * Teste si le chariot sera en collision avec le chariot suivant,
	 * lorsqu'il sera placé à la position newPosition.
	 * @param newPosition Nouvelle position, pour laquelle est testée
	 * la collision.
	 * @param chariotSuivant Chariot avec lequel tester si une
	 * collision se produit.
	 * @return true si une collision existe, false sinon.
	 */
	public Boolean willCollide(int newPosition, Chariot chariotSuivant) {
		if (newPosition + halfLength > chariotSuivant.position
				- chariotSuivant.halfLength)
			return true;

		return false;
	}
	
	/**
	 * Retourne la largeur du chariot.
	 * @return Largeur du chariot.
	 */
	public int getLength() {
		return (int) (halfLength * 2);
	}
	
	/**
	 * Modifie la largeur du chariot.
	 * @param len Largeur du chariot.
	 */
	public void setLength(int len){
		halfLength = ((double)len)/2;
	}
	
	/**
	 * Retourne la position arrière du chariot.
	 * @return Position arrière du chariot.
	 */
	public int getRearPosition(){
		return (position - (int)halfLength);
	}
	
	/**
	 * Indique si le chariot possède un bagage.
	 * 
	 * Strict contraire de isEmpty (syntaxic sugar).
	 * 
	 * @return true si le chariot contient un bagage, false sinon.
	 */
	public boolean hasBagage() {
		return bagage != null;
	}
}
