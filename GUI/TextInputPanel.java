package GUI;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

/**
 * An input box for text with standardized formatting
 * 
 * @author Madelyn Krasnay, Luke Newton
 */
public class TextInputPanel extends JPanel{
	private static final long serialVersionUID = -264503172934550773L;
	//description of the content the user should enter into the text box
	private String title;
	//user input area
	JFormattedTextField textField;

	/**
	 * Constructor
	 * 
	 *@param title : String - the header of the panel object
	 */
	public TextInputPanel(String title) {
		this.title = title;
		//creates a border around the panel with the tile at the top
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(5,5,5,5)));
		textField = new JFormattedTextField("");
		textField.setColumns(15);
		add(textField);
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
		return textField.getText();
	}
	
	/**
	 * gets the Panel's text field so it's contents can be set to specified 
	 * content (eg, to the values of a existing data point's features in 
	 * CreateEditDataPointFrame)
	 * 
	 * @return JFormattedTextField: the panel's text field
	 */
	public JFormattedTextField getTextFeild() {
		return textField;
	}
	
	/**
	 * Sets the content of the textfield.
	 * 
	 * @param content: String the content to fill the textfield with.
	 */
	public void setTextFieldText(String content) {
		this.textField.setText(content);
	}
}
