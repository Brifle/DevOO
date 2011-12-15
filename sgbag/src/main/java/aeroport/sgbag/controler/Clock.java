package aeroport.sgbag.controler;

import org.eclipse.swt.SWTException;

import lombok.*;
import aeroport.sgbag.kernel.Hall;
import aeroport.sgbag.views.VueHall;

/**
 * Gère l'horloge du système.
 * 
 * Après chaque intervalle de temps (le nombre de millisecondes est
 * stocké dans interval), la méthode run est lancée pour effectuer une
 * mise à jour du modèle (si le système n'est pas en pause) et de la
 * vue (dans tous les cas, pour assurer une fluidité du système).   
 * 
 * @author Arnaud Lahache, Thibaut Patel, Stanislas Signoud
 */
@AllArgsConstructor
public class Clock implements Runnable {

	
	@Getter
	@Setter
	private int interval;

	@Getter
	@Setter
	private Hall hall;

	@Getter
	@Setter
	private VueHall vueHall;

	private boolean isPaused = true;

	/**
	 * Démarre l'horloge.
	 */
	public void init() {
		vueHall.getDisplay().timerExec(interval, this);
	}

	/**
	 * Effectue un tic d'horloge.
	 * 
	 * Ce tic d'horloge met à jour le modèle (si l'horloge n'est pas
	 * en pause) puis demande à la vue de se synchroniser avec le 
	 * modèle.
	 * 
	 * Même si le système est en pause, la vue est rafraîchie, pour
	 * éviter des problèmes lors du scrolling ou lorsque des actions
	 * se déroulent alors que l'horloge est en pause (typiquement,
	 * la réinitialisation de la simulation à l'aide du bouton Stop).
	 */
	public void run() {
		try {
			if(hall != null && !isPaused){
				hall.update();
			}		
			vueHall.updateView();
			vueHall.draw();

			vueHall.getDisplay().timerExec(interval, this);
		} catch(SWTException e) {
			// Exception lancé à la sortie de l'application
			// Elle n'est pas importante, elle est donc juste catchée
		}
	}
	
	/**
	 * Mets l'horloge en pause.
	 * 
	 * Les actions de rafraîchissement du modèle ne seront plus effectuées,
	 * jusqu'au moment où unpause sera appelé.
	 */
	public void pause() {
		isPaused = true;
	}
	
	/**
	 * Relance l'horloge mise en pause précédemment.
	 * 
	 * Les actions de rafraîchissement du modèle reprendront au prochain
	 * tic d'horloge.
	 */
	public void unpause() {
		isPaused = false;
		
	}

}
