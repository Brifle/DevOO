package aeroport.sgbag.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;

import aeroport.sgbag.views.VueHall;


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
	
	@Test
	public void test1(){
		CircuitGenerator.setVueHall(vueHall);

		Point p1 = new Point(20, 20);
		Point p2 = new Point(100, 20);
		Point p3 = new Point(200, 100);
		Point p4 = new Point(500, 500);
		Point p5 = new Point(40, 300);
		Point p6 = new Point(40, 400);
		
		CircuitGenerator.createSegment(p1, p2);
		CircuitGenerator.createSegment(p2, p3);
		CircuitGenerator.createSegment(p3, p4);
		CircuitGenerator.createSegment(p4, p5);
		CircuitGenerator.createSegment(p5, p6);
		CircuitGenerator.createSegment(p3, p5);
		
		shell.open();
		vueHall.draw();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
}
