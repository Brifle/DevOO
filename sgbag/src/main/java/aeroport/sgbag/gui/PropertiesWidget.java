package aeroport.sgbag.gui;

import lombok.Getter;
import lombok.Setter;

import org.eclipse.swt.widgets.Composite;

import aeroport.sgbag.controler.Simulation;

public class PropertiesWidget extends Composite {
	
	@Getter
	private Simulation simulation;

	/**
	 * Change simulation and refresh.
	 * 
	 * You don't need to refresh this widget just after setting its simulation.
	 * @param simulation The new simulation.
	 */
	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
		refresh();
	}
	
	public PropertiesWidget(Composite parent, int style, Simulation simulation) {
		super(parent, style);
		this.simulation = simulation;
	}
	
	public void refresh(){
		// TODO: Do refreshing here, using simulation.getSelected();
	}
}
