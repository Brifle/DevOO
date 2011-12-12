package aeroport.sgbag.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import lombok.Getter;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;

import aeroport.sgbag.controler.Simulation;
import aeroport.sgbag.kernel.Bagage;
import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.KernelObject;
import aeroport.sgbag.kernel.Rail;
import aeroport.sgbag.kernel.TapisRoulant;
import aeroport.sgbag.kernel.Toboggan;
import aeroport.sgbag.views.VueBagage;
import aeroport.sgbag.views.VueChariot;
import aeroport.sgbag.views.VueElem;
import aeroport.sgbag.views.VueRail;
import aeroport.sgbag.views.VueTapisRoulant;
import aeroport.sgbag.views.VueToboggan;

public class PropertiesWidget extends Composite {

	@Getter
	private Simulation simulation;

	private Composite canvas;

	/**
	 * Change simulation and refresh.
	 * 
	 * You don't need to refresh this widget just after setting its simulation.
	 * 
	 * @param simulation
	 *            The new simulation.
	 */
	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;

		refresh();
	}

	public PropertiesWidget(Composite parent, int style, Simulation simulation) {
		super(parent, style);

		this.simulation = simulation;

		this.setLayout(new FillLayout());

		canvas = new Composite(this, SWT.NONE);
	}

	public void refresh() {
		if (canvas != null) {
			canvas.dispose();
		}

		canvas = new Composite(this, SWT.NONE);

		canvas.setLayout(new GridLayout(2, false));

		if (this.simulation != null) {
			if (this.simulation.getSelectedElem() == null) {
				Label l = new Label(canvas, SWT.NONE);
				l.setText("Aucun objet selectionné");
				l.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
						2, 1));
			} else {
				if (this.simulation.getSelectedElem() instanceof VueBagage) {
					setVueBagageMode();
				} else if (this.simulation.getSelectedElem() instanceof VueChariot) {
					setVueChariotMode();
				} else if (this.simulation.getSelectedElem() instanceof VueRail) {
					setVueRailMode();
				} else if (this.simulation.getSelectedElem() instanceof VueToboggan) {
					setVueToboganMode();
				} else if (this.simulation.getSelectedElem() instanceof VueTapisRoulant) {
					setVueTapisRoulantViewMode();
				}
			}
		}

		canvas.layout();
		this.layout();
	}
	
	private void setVueBagageMode() {
		Bagage tr = ((VueBagage) this.simulation.getSelectedElem())
				.getBagage();

		setCommonProperties(tr);
		
		if (tr.getDestination() != null && tr.getDestination().getName() != null) {
			new Label(canvas, SWT.NONE).setText("Noeud destination : ");
			new Label(canvas, SWT.NONE).setText(tr.getDestination()
					.getName());
		}
		
		new Label(canvas, SWT.NONE).setText("Position relative : ");
		new Label(canvas, SWT.NONE).setText("" + tr.getPosition());
	}

	private void setVueChariotMode() {
		Chariot tr = ((VueChariot) this.simulation.getSelectedElem())
				.getChariot();

		setCommonProperties(tr);

		new Label(canvas, SWT.NONE).setText("Mouvement maximal : ");
		Spinner s1 = new Spinner(canvas, SWT.WRAP);
		s1.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				Chariot tr = ((VueChariot) simulation.getSelectedElem())
						.getChariot();

				tr.setMaxMoveDistance(((Spinner) arg0.widget).getSelection());
			}
		});
		s1.setValues(tr.getMaxMoveDistance(), 0, 100, 0, 1, 10);
		
		new Label(canvas, SWT.NONE).setText("Position relative : ");
		new Label(canvas, SWT.NONE).setText("" + tr.getPosition());

		if (tr.getDestination() != null && tr.getDestination().getName() != null) {
			new Label(canvas, SWT.NONE).setText("Noeud destination : ");
			new Label(canvas, SWT.NONE).setText(tr.getDestination()
					.getName());
		}

		if (tr.getBagage() != null && tr.getBagage().getName() != null) {
			new Label(canvas, SWT.NONE).setText("Bagage : ");
			new Label(canvas, SWT.NONE).setText(tr.getBagage().getName());
		}
	}

	private void setVueRailMode() {
		Rail tr = ((VueRail) this.simulation.getSelectedElem()).getRail();

		setCommonProperties(tr);

		new Label(canvas, SWT.NONE).setText("Longueur du rail : ");
		new Label(canvas, SWT.NONE).setText("" + tr.getLength());

		if (tr.getNoeudPrecedent() != null) {
			new Label(canvas, SWT.NONE).setText("Noeud precedent : ");
			new Label(canvas, SWT.NONE).setText(tr.getNoeudPrecedent()
					.getName());
		}

		if (tr.getNoeudSuivant() != null) {
			new Label(canvas, SWT.NONE).setText("Noeud suivant : ");
			new Label(canvas, SWT.NONE).setText(tr.getNoeudSuivant().getName());
		}
	}

	private void setCommonProperties(KernelObject o) {
		new Label(canvas, SWT.NONE).setText("Nom : " + o.getName());

		new Label(canvas, SWT.NONE).setText("Id : " + o.getId());
	}

	private void setVueToboganMode() {
		Toboggan tr = ((VueToboggan) this.simulation.getSelectedElem())
				.getToboggan();

		setCommonProperties(tr);

		Label l1 = new Label(canvas, SWT.NONE);
		l1.setText("Temps avant la disparition du bagage :");

		Spinner s1 = new Spinner(canvas, SWT.WRAP);
		s1.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {
				Toboggan tr = ((VueToboggan) simulation.getSelectedElem())
						.getToboggan();

				tr.setNbTicsBagagesRemains(((Spinner) arg0.widget)
						.getSelection());
			}
		});
		s1.setValues(tr.getNbTicsBagagesRemains(), 0, 100, 0, 1, 10);

		Label l2 = new Label(canvas, SWT.NONE);
		l2.setText("Generation automatique de bagages :");

		Button button = new Button(canvas, SWT.CHECK);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Toboggan tr = ((VueToboggan) simulation.getSelectedElem())
						.getToboggan();

				tr.setAutoDeleteBagages(((Button) e.widget).getSelection());
			}
		});
		button.setSelection(tr.isAutoDeleteBagages());
	}

	private void setVueTapisRoulantViewMode() {
		TapisRoulant tr = ((VueTapisRoulant) this.simulation.getSelectedElem())
				.getTapisRoulant();

		setCommonProperties(tr);

		Label l1 = new Label(canvas, SWT.NONE);
		l1.setText("Vitesse du tapis :");

		Spinner s1 = new Spinner(canvas, SWT.WRAP);
		s1.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {
				TapisRoulant tr = ((VueTapisRoulant) simulation
						.getSelectedElem()).getTapisRoulant();

				tr.setVitesseTapis(((Spinner) arg0.widget).getSelection());
			}
		});
		s1.setValues(tr.getVitesseTapis(), 0, 100, 0, 1, 10);

		Label l2 = new Label(canvas, SWT.NONE);
		l2.setText("Distance entre bagages :");

		Spinner s2 = new Spinner(canvas, SWT.WRAP);
		s2.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {
				TapisRoulant tr = ((VueTapisRoulant) simulation
						.getSelectedElem()).getTapisRoulant();

				tr.setDistanceEntreBagages(((Spinner) arg0.widget)
						.getSelection());
			}
		});
		s2.setValues(tr.getDistanceEntreBagages(), 0, 100, 0, 1, 10);

		Label l5 = new Label(canvas, SWT.NONE);
		l5.setText("Longueur du tapis : " + tr.getLength());
	}

}
