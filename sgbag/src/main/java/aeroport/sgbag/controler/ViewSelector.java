package aeroport.sgbag.controler;

import java.util.HashMap;

import aeroport.sgbag.views.Viewable;

public class ViewSelector {

	HashMap<Object, Viewable> hash;

	public ViewSelector() {
		super();
		hash = new HashMap<Object, Viewable>();
	}

	public Viewable getViewForKernelObject(Object k) {
		return hash.get(k);
	}

	public void setKernelView(Object k, Viewable v) {
		hash.put(k, v);
	}
	
	public Viewable removeKeyValue(Object k){
		return hash.remove(k);
	}

}
