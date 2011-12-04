package aeroport.sgbag.kernel;

import java.util.LinkedList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Chariot extends ConteneurBagage {

	@Getter
	@Setter
	private ElementCircuit parent;

	@Getter
	@Setter
	private int maxMoveDistance;

	private int length;

	@Getter
	@Setter
	private int position;

	@Getter
	@Setter
	private Noeud destination;

	private Bagage bagage;

	@Getter
	@Setter
	private LinkedList<ElementCircuit> cheminPrevu;

	public Chariot(ElementCircuit parent, int maxMoveDistance,
			Noeud destination, LinkedList<ElementCircuit> cheminPrevu) {
		this(parent, maxMoveDistance, 50, maxMoveDistance, destination, null, cheminPrevu);
	}

	public Chariot(ElementCircuit parent, int maxMoveDistance, int length,
			int position, Noeud destination, Bagage bagage,
			LinkedList<ElementCircuit> cheminPrevu) {
		super();
		this.parent = parent;
		this.maxMoveDistance = maxMoveDistance;
		this.length = length;
		this.position = position;
		this.destination = destination;
		this.bagage = bagage;
		this.cheminPrevu = cheminPrevu;
	}

	public Rail getNextRail() {
		ElementCircuit nextElemC = cheminPrevu.getFirst();

		if (nextElemC instanceof Rail)
			return (Rail) nextElemC;

		return getNextRail();
	}

	public Boolean isEmpty() {
		return (bagage == null);
	}

	public Boolean moveBagageToFile(FileBagage file) {
		file.addBagage(bagage);
		return removeBagage();
	}

	public Boolean removeBagage() {
		bagage = null;

		return this.isEmpty();
	}

	public Boolean isColliding(Chariot chariotSuivant) {
		if (position + length > chariotSuivant.position - chariotSuivant.length)
			return true;

		return false;
	}

	public Boolean moveBy(Integer distance) {
		// TODO:
		return null;
	}

	public Boolean moveTo(Integer distance) {
		// TODO:
		return null;
	}

	public Boolean update() {
		// TODO:
		return null;
	}

}