package aeroport.sgbag.controler;

import java.io.File;

import lombok.*;
import aeroport.sgbag.kernel.*;
import aeroport.sgbag.views.*;

/**
 * Create things like kernel instances and views.
 * @author Arnaud Lahache & Stanislas Signoud
 *
 */
@RequiredArgsConstructor
public class Simulation {
	
	public enum Mode {
		MANUEL, AUTO
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
	private Clock clock;
	
	public void init() {
		
		// TODO init vueHall with XML file
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
	
}
