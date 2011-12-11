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
	
	@NonNull
	@Getter
	private File xmlFile;
	
	@Getter
	private VueHall vueHall;
	
	@Getter
	private Hall hall;
	
	public void init() {
		// TODO
	}
	
}
