package aeroport.sgbag.views;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import java.util.*;

import lombok.*;

public class VueHall extends Canvas implements Viewable {

	private Image buffer;
	
	@Getter
	@Setter
	private GC gcBuffer;
	
	@Getter
	private TreeMap<Integer, LinkedList<VueElem>> calques;

	public VueHall(Composite parent, int style) {
		super(parent, style);
		calques = new TreeMap<Integer, LinkedList<VueElem>>();
	}

	public void ajouterVue(VueElem vue, int layer) {
		LinkedList<VueElem> elementOfLayer;
		if(calques.get(layer) == null) {
			elementOfLayer = new LinkedList<VueElem>();
			calques.put(layer, elementOfLayer);
		} else {
			elementOfLayer = calques.get(layer);
		}
		elementOfLayer.add(vue);
		if(vue.getParent() == null) {
			vue.setParent(this);
		}
	}

	public void retirerVue(VueElem vue) {
		boolean trouve = false;
		for (Iterator<Integer> iterator = calques.keySet().iterator(); iterator.hasNext() && !trouve;) {
			LinkedList<VueElem> vues = calques.get(iterator.next());
			for (int j = 0; j < vues.size() && !trouve; j++) {
				if(vues.get(j).equals(vue)) {
					vues.remove(j);
					trouve = true;
				}
			}
		}
	}

	public void updateView() {
		// Update all the views, ordered by the layers
		for (Iterator<Integer> iterator = calques.keySet().iterator(); iterator.hasNext();) {
			LinkedList<VueElem> vues = calques.get(iterator.next());
			for (int j = 0; j < vues.size(); j++) {
				vues.get(j).updateView();
			}
		}
	}

	public void draw() {
		buffer = new Image(this.getDisplay(), this.getBounds());
		gcBuffer = new GC(buffer);
		gcBuffer.drawLine(0, 0, 300, 300);
		// Draw all the views ordered by the layers :
		for (Iterator<Integer> iterator = calques.keySet().iterator(); iterator.hasNext();) {
			LinkedList<VueElem> vues = calques.get(iterator.next());
			for (int j = 0; j < vues.size(); j++) {
				vues.get(j).draw();
			}
		}
		
		// Draw the resulting image :
		this.setBackgroundImage(buffer);
		
		gcBuffer.dispose();
	}
	
	public boolean isClicked(Point p) {
		return true;
	}
	
	public Viewable getClickedView(int x, int y) {
		
		Point p = new Point(x, y);
		for (Iterator<Integer> iterator = calques.descendingKeySet().iterator(); iterator.hasNext();) {
			LinkedList<VueElem> vues = calques.get(iterator.next());
			for (Iterator<VueElem> itVueElem = vues.descendingIterator(); itVueElem.hasNext();) {
				VueElem v = itVueElem.next();
				if(v.isClicked(p)) return v;
			}
		}
		
		return this;
	}

}
