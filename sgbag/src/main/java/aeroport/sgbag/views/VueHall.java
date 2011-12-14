package aeroport.sgbag.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import aeroport.sgbag.controler.Simulation;
import aeroport.sgbag.controler.ViewSelector;
import aeroport.sgbag.kernel.Bagage;
import aeroport.sgbag.kernel.Hall;
import aeroport.sgbag.utils.ParticleManager;

import java.util.*;

import lombok.*;
import lombok.extern.log4j.Log4j;

@Log4j
public class VueHall extends Canvas implements Viewable {

	private Image buffer;

	@Getter
	@Setter
	private Hall hall;

	@Getter
	@Setter
	private Simulation simulation;

	@Getter
	@Setter
	private GC gcBuffer;

	@Getter
	private TreeMap<Integer, LinkedList<VueElem>> calques;

	@Getter
	private ArrayList<VueBagage> bagagesVues;

	@Getter
	private Rectangle displayRect;

	@Getter
	private Point origin;

	private ScrollBar hBar;

	private ScrollBar vBar;

	public VueHall(Composite parent, int style) {
		super(parent, SWT.NO_REDRAW_RESIZE | SWT.NO_BACKGROUND | SWT.V_SCROLL
				| SWT.H_SCROLL);
		calques = new TreeMap<Integer, LinkedList<VueElem>>();

		bagagesVues = new ArrayList<VueBagage>();
		displayRect = new Rectangle(0,0,1000,1000);

		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent event) {

				// Create the image to fill the canvas
				buffer = new Image(getDisplay(), displayRect);

				// Set up the offscreen gc
				gcBuffer = new GC(buffer);

				// Draw the background
				gcBuffer.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				gcBuffer.fillRectangle(buffer.getBounds());

				// Draw all the views ordered by the layers :
				for (Iterator<Integer> iterator = calques.keySet().iterator(); iterator
						.hasNext();) {
					LinkedList<VueElem> vues = calques.get(iterator.next());
					for (int j = 0; j < vues.size(); j++) {
						vues.get(j).draw();
					}
				}

				ParticleManager.getParticleManager().draw(gcBuffer);

				// Draw the offscreen buffer to the screen
				event.gc.drawImage(buffer, origin.x, origin.y);

				// Scroll
				Rectangle rect = buffer.getBounds();
				Rectangle client = getClientArea();
				int marginWidth = client.width - rect.width;
				if (marginWidth > 0) {
					event.gc.fillRectangle(rect.width, 0, marginWidth,
							client.height);
				}
				int marginHeight = client.height - rect.height;
				if (marginHeight > 0) {
					event.gc.fillRectangle(0, rect.height, client.width,
							marginHeight);
				}

				// Clean up
				buffer.dispose();
				gcBuffer.dispose();
			}
		});

		origin = new Point(0, 0);
		hBar = this.getHorizontalBar();
		hBar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int hSelection = hBar.getSelection();
				int destX = -hSelection - origin.x;
				Rectangle rect = getDisplayRect();
				scroll(destX, 0, 0, 0, rect.width, rect.height, false);
				origin.x = -hSelection;
			}
		});

		vBar = getVerticalBar();
		vBar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int vSelection = vBar.getSelection();
				int destY = -vSelection - origin.y;
				Rectangle rect = getDisplayRect();
				scroll(0, destY, 0, 0, rect.width, rect.height, false);
				origin.y = -vSelection;
			}
		});

		addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event e) {
				Rectangle rect = getDisplayRect();
				Rectangle client = getClientArea();
				hBar.setMaximum(rect.width);
				vBar.setMaximum(rect.height);
				hBar.setThumb(Math.min(rect.width, client.width));
				vBar.setThumb(Math.min(rect.height, client.height));
				int hPage = rect.width - client.width;
				int vPage = rect.height - client.height;
				int hSelection = hBar.getSelection();
				int vSelection = vBar.getSelection();
				if (hSelection >= hPage) {
					if (hPage <= 0)
						hSelection = 0;
					origin.x = -hSelection;
				}
				if (vSelection >= vPage) {
					if (vPage <= 0)
						vSelection = 0;
					origin.y = -vSelection;
				}
				redraw();
			}
		});

		addMouseListener(new TraitementClic(this));
	}

	public void ajouterVue(VueElem vue, int layer) {
		LinkedList<VueElem> elementOfLayer;
		if (calques.get(layer) == null) {
			elementOfLayer = new LinkedList<VueElem>();
			calques.put(layer, elementOfLayer);
		} else {
			elementOfLayer = calques.get(layer);
		}
		elementOfLayer.add(vue);
		if (vue.getParent() == null) {
			vue.setParent(this);
		}
	}

	public void retirerVue(VueElem vue) {
		boolean trouve = false;
		for (Iterator<Integer> iterator = calques.keySet().iterator(); iterator
				.hasNext() && !trouve;) {
			LinkedList<VueElem> vues = calques.get(iterator.next());
			for (int j = 0; j < vues.size() && !trouve; j++) {
				if (vues.get(j).equals(vue)) {
					vues.remove(j);
					trouve = true;
				}
			}
		}
	}

	public void updateView() {

		// Add new bagages views
		ArrayList<Bagage> bagages = this.getHall().getBagagesList();
		for (int i = 0; i < bagages.size(); i++) {
			boolean found = false;
			for (int j = 0; j < bagagesVues.size() && !found; j++) {
				if (bagages.get(i).equals(bagagesVues.get(j).getBagage())) {
					found = true;
				}
			}
			if (!found) {
				addBagage(bagages.get(i));
				log.debug("Ajout d'une vue bagage");
			}
		}

		// Remove deleted bagages views
		for (int i = 0; i < bagagesVues.size(); i++) {
			boolean found = false;
			for (int j = 0; j < bagages.size() && !found; j++) {
				if (bagagesVues.get(i).getBagage().equals(bagages.get(j))) {
					found = true;
				}
			}
			if (!found) {
				// Remove bagage vue
				bagagesVues.get(i).destroy();
				bagagesVues.remove(i);
				log.debug("Suppression d'une vue bagage");
			}
		}

		// Update all the views, ordered by the layers
		for (Iterator<Integer> iterator = calques.keySet().iterator(); iterator
				.hasNext();) {
			LinkedList<VueElem> vues = calques.get(iterator.next());
			for (int j = 0; j < vues.size(); j++) {
				vues.get(j).updateView();
			}
		}

		ParticleManager.getParticleManager().update();
	}

	public void addBagage(Bagage bagage) {
		VueBagage vueBagage = new VueBagage(this);
		vueBagage.setBagage(bagage);
		this.ajouterVue(vueBagage, 4);
		ViewSelector.getInstance().setKernelView(bagage, vueBagage);
		bagagesVues.add(vueBagage);
	}

	public void draw() {
		// Force a redraw event :
		try {
			redraw();
		} catch(SWTException e) {
			// Exception lancé à la sortie de l'application
			// Elle n'est pas importante, elle est donc juste catchée
		}
	}

	public boolean isClicked(Point p) {
		return true;
	}

	public Viewable getClickedView(int x, int y) {

		Point p = new Point(x, y);
		for (Iterator<Integer> iterator = calques.descendingKeySet().iterator(); iterator
				.hasNext();) {
			LinkedList<VueElem> vues = calques.get(iterator.next());
			for (Iterator<VueElem> itVueElem = vues.descendingIterator(); itVueElem
					.hasNext();) {
				VueElem v = itVueElem.next();
				if (v.isClicked(p))
					return v;
			}
		}

		return this;
	}

}
