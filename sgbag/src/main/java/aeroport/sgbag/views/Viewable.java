package aeroport.sgbag.views;

/**
 * @author Arnaud Lahache
 * 
 */
public interface Viewable {

	/**
	 * Updates the view regarding to the kernel object properties.
	 */
	public void updateView();

	/**
	 * Draws the object on the screen.
	 */
	public void draw();

	/**
	 * Returns true if the view is clicked. Note : it does not take in account
	 * the layer in which the view is contained.
	 * 
	 * @return True if the view is clicked.
	 */
	public boolean isClicked();

}
