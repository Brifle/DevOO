package aeroport.sgbag.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import aeroport.sgbag.views.VueHall;
import aeroport.sgbag.views.VueRail;


public class TestCircuitGenerator {

	private Display display;
	private Shell shell;
	private VueHall vueHall;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		display = new Display();
		shell = new Shell(display);
		shell.setText("VueTest");
		shell.setLayout(new FillLayout());
		shell.setSize(800, 800);
		vueHall = new VueHall(shell, SWT.NONE);
		vueHall.setSize(300, 300);

	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		vueHall.dispose();
		shell.dispose();
		display.dispose();
	}
	
	@Test
	public void test1(){
		CircuitGenerator cg = new CircuitGenerator();
		cg.setVueHall(vueHall);

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
		cg.createSegment(p5, p6);
		cg.createSegment(p3, p5);
		
		shell.open();
		vueHall.draw();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	@Test
	public void testExitEntry(){
		CircuitGenerator cg = new CircuitGenerator();
		cg.setVueHall(vueHall);

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
		
		shell.open();
		vueHall.updateView();
		vueHall.draw();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
}
