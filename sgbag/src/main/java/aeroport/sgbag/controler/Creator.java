package aeroport.sgbag.controler;

import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.Hall;
import aeroport.sgbag.views.VueHall;

/**
 * Create things like kernel instances and views.
 * @author Stanislas Signoud <signez@stanisoft.net>
 *
 */
public class Creator {
	public VueHall vueHall;
	
	/**
	 * Create all views corresponding to all kernel instances.
	 * 
	 * OMG ITS CHRISTMAS GUYS !
	 */
	public void createAllViews(Hall rootHall) {
		for(Chariot ch: rootHall.getChariotList()) {
			
		}
		//vueHall.ajouterVue(vue, layer);
	}
}
