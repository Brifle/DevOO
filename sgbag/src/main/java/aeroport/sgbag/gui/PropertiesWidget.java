package aeroport.sgbag.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import lombok.Getter;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import aeroport.sgbag.controler.Simulation;
import aeroport.sgbag.kernel.KernelObject;
import aeroport.sgbag.kernel.TapisRoulant;
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
				l.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
			} else {
				if (this.simulation.getSelectedElem() instanceof VueBagage) {

				} else if (this.simulation.getSelectedElem() instanceof VueChariot) {

				} else if (this.simulation.getSelectedElem() instanceof VueRail) {

				} else if (this.simulation.getSelectedElem() instanceof VueToboggan) {

				} else if (this.simulation.getSelectedElem() instanceof VueTapisRoulant) {
					setVueTapisRoulantViewMode();
				}
			}
		}

		canvas.layout();
		this.layout();
	}
	
	private void setVueTapisRoulantViewMode(){
		TapisRoulant tr = ((VueTapisRoulant) this.simulation
				.getSelectedElem()).getTapisRoulant();
		
		Label l3 = new Label(canvas, SWT.NONE);
		l3.setText("Nom : " + tr.getName());
		
		Label l4 = new Label(canvas, SWT.NONE);
		l4.setText("Id : " + tr.getId());

		Label l1 = new Label(canvas, SWT.NONE);
		l1.setText("Vitesse du tapis :");
		
		Spinner s1 = new Spinner(canvas, SWT.WRAP);
		s1.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				TapisRoulant tr = ((VueTapisRoulant) simulation
						.getSelectedElem()).getTapisRoulant();
				
				tr.setVitesseTapis(((Spinner)arg0.widget).getSelection());
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
				
				tr.setDistanceEntreBagages(((Spinner)arg0.widget).getSelection());
			}
		});
		s2.setValues(tr.getDistanceEntreBagages(), 0, 100, 0, 1, 10);
		
		Label l5 = new Label(canvas, SWT.NONE);
		l5.setText("Longueur du tapis : " + tr.getLength());
	}
	
	
}
