package aeroport.sgbag.gui;

import lombok.extern.log4j.Log4j;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import aeroport.sgbag.utils.CircuitGenerator;
import aeroport.sgbag.views.VueHall;
import aeroport.sgbag.views.VueRail;

@Log4j
public class MainWindowTest {
	
	private MainWindow win;
	private VueHall vueHall;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
	}
	
	@Before
	public void createWindow(){
		win = new MainWindow();
		win.setBlockOnOpen(false);
		win.open();
		
		vueHall = win.getSimulation().getVueHall();
	}
	

	@Test
	public void test() {
		CircuitGenerator cg = new CircuitGenerator(vueHall);

		Point p1 = new Point(20, 20);
		Point p2 = new Point(100, 20);
		Point p3 = new Point(200, 100);
		Point p4 = new Point(500, 500);
		Point p5 = new Point(40, 300);
		Point p6 = new Point(40, 400);
		
		cg.createSegment(p1, p2);
		cg.createSegment(p2, p3);
		cg.createSegment(p3, p4);
		cg.createSegment(p4, p5);
		VueRail vueRail1 = cg.createSegment(p5, p6);
		cg.createSegment(p3, p5);
		cg.createExit(p3);
		cg.createEntry(p5, 100, 10, 5, false);
		cg.addChariot(vueRail1.getRail(), 40, 20, 200, null, null, null);
	}
	
	@After
	public void showWindow(){
		win.setBlockOnOpen(true);
		win.open();
		Display.getCurrent().dispose();
	}

}
