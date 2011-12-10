package aeroport.sgbag.controler;

import lombok.*;
import aeroport.sgbag.kernel.Hall;
import aeroport.sgbag.views.VueHall;

@AllArgsConstructor
public class Clock implements Runnable {
	
	@Getter
	@Setter
	private Hall hall;
	
	@Getter
	@Setter
	private VueHall vueHall;

	public void run() {
		hall.update();
		vueHall.update();
		vueHall.draw();
	}

}
