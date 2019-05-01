package GUI;

/**
 * This class creates an input panel object that consists of a textbox.
 * These are used to get data from the user for an unknown number of attributes.
 *
 *@author Cameron Rushton, Madelyn Krasnay
 *@version 2.0
 */

import javax.swing.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TextFieldInputPanel extends InputPanel{
	private static final long serialVersionUID = 4916333257931958033L;
	JFormattedTextField textField; //Data being added

	/**
	 * Constructor
	 * 
	 *@param title : String - the header of the panel object
	 */
	public TextFieldInputPanel(String title) {

		super(title);

		//Add input text field
		textField = new JFormattedTextField("");
		textField.setColumns(15);
		textField.addPropertyChangeListener(new newInputListener());
		add(textField);
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
	
	/**
	 * Listens for the value of the text field to be changed and updates inputedData
	 * to reflect the textfeild's current contents
	 */
	private class newInputListener implements PropertyChangeListener{
		@Override
		public void propertyChange(PropertyChangeEvent e) {
			inputedData = ((JTextField)e.getSource()).getText();
		}
	}

}
