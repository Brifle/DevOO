package aeroport.sgbag.controler;

import java.util.HashMap;

import aeroport.sgbag.views.Viewable;
import aeroport.sgbag.views.VueElem;

/**
 * Permet de retourner la vue à partir d'un élément métier.
 * 
 * @author Mathieu Sabourin
 */
public class ViewSelector {

	HashMap<Object, Viewable> hash;

	private static ViewSelector instance = null;
	
	/**
	 * Crée une instance du singleton ViewSelector.
	 */
	private ViewSelector() {
		super();
		hash = new HashMap<Object, Viewable>();
	}

	/**
	 * Retourne l'instance actuelle du ViewSelector.
	 * 
	 * @return Une instance de ViewSelector.
	 */
	public static ViewSelector getInstance() {
		if (instance == null) {
			instance = new ViewSelector();
		}
		return instance;
	}

	/**
	 * Retourne la vue en relation avec l'objet métier k.
	 * 
	 * @param k L'objet métier.
	 * @return L'objet vue qui correspond.
	 */
	public Viewable getViewForKernelObject(Object k) {
		return hash.get(k);
	}

	/**
	 * Associe un objet métier avec un objet vue, pour permettre
	 * d'effectuer cette relation plus tard.
	 * 
	 * @param k L'objet métier à associer.
	 * @param v L'objet vue à associer.
	 */
	public void setKernelView(Object k, Viewable v) {
		hash.put(k, v);
	}

	/**
	 * Retire une relation entre l'objet métier k et sa vue. 
	 * 
	 * @param k 
	 * @return true 
	 */
	public Viewable removeKeyValue(Object k) {
		return hash.remove(k);
	}
	
	/**
	 * Retire une relation entre la vue v et son objet métier.
	 * 
	 * @param v
	 */
	public void removeByView(VueElem v) {
		hash.values().remove(v);
	}
	
	/**
	 * Vide toutes les relations vue/métier sauvegardées.
	 */
	public void clear() {
		hash.clear();
	}
}
