package aeroport.sgbag.controler;

import java.util.HashMap;

import aeroport.sgbag.views.Viewable;

public class ViewSelector {

	HashMap<Object, Viewable> hash;

	private static ViewSelector instance = null;
	
	private ViewSelector() {
		super();
		hash = new HashMap<Object, Viewable>();
	}

	public static ViewSelector getInstance() {
		if (instance == null) {
			instance = new ViewSelector();
		}
		return instance;
	}

	public Viewable getViewForKernelObject(Object k) {
		return hash.get(k);
	}

	public void setKernelView(Object k, Viewable v) {
		hash.put(k, v);
	}

	public Viewable removeKeyValue(Object k) {
		return hash.remove(k);
	}

}
