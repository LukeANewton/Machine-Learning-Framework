package GUI;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class InputPanel extends JPanel{
	private static final long serialVersionUID = -264503172934550773L;
	private String title;
	protected String inputedData;

	/**
	 * Constructor
	 * 
	 *@param tittle : String - the header of the panel object
	 */
	public InputPanel(String title) {
		this.title = title;

		//creates a border around the panel with the tile at the top
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(5,5,5,5)));
	}

	/**	
	 * 	Don't allow this panel to get taller than its preferred size.
	 *	BoxLayout pays attention to maximum size, though most layout
	 *	managers don't.
	 */
	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
	}
	
	/**
	 * Getter for the title.
	 * 
	 * @return String: the title of the panel
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets all of the current input in the panel
	 * 
	 * @return String: The inputed data.
	 */
	public String getInput() {
		return inputedData;
	}
}
