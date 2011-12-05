package aeroport.sgbag.kernel;

import java.util.LinkedList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Chariot {

	@Getter
	@Setter
	private ElementCircuit parent;

	@Getter
	@Setter
	private int maxMoveDistance;

	private int halfLength;

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
	private LinkedList<ElementCircuit> cheminPrevu;

	public Chariot(ElementCircuit parent, int maxMoveDistance,
			Noeud destination, LinkedList<ElementCircuit> cheminPrevu) {
		this(parent, maxMoveDistance, 50, maxMoveDistance, destination, null,
				cheminPrevu);
	}

	public Chariot(ElementCircuit parent, int maxMoveDistance, int length,
			int position, Noeud destination, Bagage bagage,
			LinkedList<ElementCircuit> cheminPrevu) {
		super();
		this.parent = parent;
		this.maxMoveDistance = maxMoveDistance;
		this.halfLength = length;
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
		if (position + halfLength > chariotSuivant.position
				- chariotSuivant.halfLength)
			return true;

		return false;
	}

	public Boolean willCollide(int newPosition, Chariot chariotSuivant) {
		if (newPosition + halfLength > chariotSuivant.position
				- chariotSuivant.halfLength)
			return true;

		return false;
	}

	public int getLength() {
		return halfLength * 2;
	}
	
	public void setLength(int len){
		halfLength = len/2;
	}
}
