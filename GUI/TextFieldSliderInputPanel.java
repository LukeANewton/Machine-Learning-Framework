package GUI;

/**
 * This class creates an input panel object that consists of a textbox and slider.
 * These are used to get data from the user for an unknown number of attributes.
 *
 *@author Cameron Rushton, Madelyn Krasnay
 *@version 1.0
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TextFieldSliderInputPanel extends TextFieldInputPanel{
	private static final long serialVersionUID = -771703039217967499L;
	private JTextField weightOutputField; //output for weight
	private JSlider slider; //input for weight
	
	private int weight;

	public TextFieldSliderInputPanel(String title) {
		super(title);
		
		//Add the slider
		slider = new JSlider();
		slider.addChangeListener(new newSliderValueListener());
		
		//add output text field
		weightOutputField = new JTextField("Weight (Default 0)");
		weightOutputField.setEditable(false);
		
		JPanel sliderPanel = new JPanel();
		//sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.PAGE_AXIS));
		sliderPanel.add(slider);
		sliderPanel.add(weightOutputField);
		add(sliderPanel);
	}
	
	/**
	 * gets the value of the feature as indicated by the value of the slider.
	 * 
	 * @return int : the value of the panel's slider
	 */
	protected int getWeight() {
		return weight;
	}
	
	/**
	 *  Slider listener that updates the panel's weightOutputField and TextField
	 */
	private class newSliderValueListener implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			weightOutputField.setText("" + source.getValue());
			weight = (int)source.getValue();
		}
	}

}