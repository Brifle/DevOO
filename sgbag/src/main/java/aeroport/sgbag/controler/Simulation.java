package aeroport.sgbag.controler;

import java.io.File;

import lombok.*;
import aeroport.sgbag.kernel.*;
import aeroport.sgbag.views.*;

/**
 * Create things like kernel instances and views.
 * 
 * @author Arnaud Lahache & Stanislas Signoud
 * 
 */
@RequiredArgsConstructor
public class Simulation {

	public enum Mode {
		MANUEL, AUTO
	}

	public enum Etat {
		NORMAL, SELECTION, CHOIX_DESTINATION
	}

	@NonNull
	@Getter
	private File xmlFile;

	@Getter
	private VueHall vueHall;

	@Getter
	private Hall hall;

	@Getter
	@Setter
	private Mode mode;

	@Getter
	@Setter
	private Etat etat;

	@Getter
	private Clock clock;

	@Getter
	private VueElem selectedElem;

	public void init() {

		// TODO init vueHall with XML file

		vueHall.setSimulation(this);
		hall = vueHall.getHall();

		clock = new Clock(100, hall, vueHall);
	}

	public void play() {
		clock.run();
	}

	public void pause() {
		clock.pause();
	}

	public void stop() {
		clock.pause();
		// TODO clear all bagages
	}

	public void setSelectedElem(VueElem _selectedElem) {
		if (selectedElem != null)
			selectedElem.setSelected(false);
		selectedElem = _selectedElem;
		if (selectedElem != null)
			selectedElem.setSelected(true);
	}

	public void createBagage(VueElem depart, VueElem destination) {

		// TODO sh*tty stuff to create a Bagage in kernel

		// TODO sh*tty stuff to create a VueBagage

	}

}
