package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


import problemComponents.Problem;
import simpleFeatureDistanceStrategies.*;
import compositeFeatureDistanceStrategies.*;
import exampleDistanceCombinationStrategies.*;

/**
 * This class has the contents and functionality to select the metrics that will be used in predictions
 * 
 * @author luke newton
 */
public class PredictionConfigurePanel extends JPanel {
	private static final long serialVersionUID = 22892476582418299L;
	//the problem set currently working with
	private Problem problem;
	//textfield to get number of nearest neighbors
	private JTextField kValueTextField;
	//group of radio buttons to specify which distance functions to use
	private ButtonGroup exampleDistanceFunctionButtonGroup, pointDistanceFunctionButtonGroup, characterDistanceFunctionButtonGroup, 
	doubleDistanceFunctionButtonGroup, integerDistanceFunctionButtonGroup, stringDistanceFunctionButtonGroup;
	//maps of radio button texts to the corresponding strategies
	private HashMap<String, SimpleDistanceStrategy> simpleDistanceFunctionSelectionMap;
	private HashMap<String, CompositeDistanceStrategy> compositeDistanceFunctionSelectionMap;
	private HashMap<String, ExampleDistanceStrategy> exampleDistanceFunctionSelectionMap;
	//the parent JFrame this panel is displayed in
	private MachineLearningFramework m;

	/**
	 * Constructor
	 * 
	 * @param problem the problem set currently working with
	 * @param exampleToPredict index value of the test example to predict
	 */
	public PredictionConfigurePanel(MachineLearningFramework m, int exampleToPredict){
		super(); 
		this.problem = m.getProblem();
		this.m = m;
		m.setMenuBarEnabled(false);
		simpleDistanceFunctionSelectionMap = new HashMap<>();
		compositeDistanceFunctionSelectionMap = new HashMap<>();
		exampleDistanceFunctionSelectionMap = new HashMap<>();
		m.setPreferredSize(new Dimension(600, 600));
		createContent();
	}

	private void createContent(){
		removeAll();

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;

		//create the collection of radio buttons to specify which example distance function to use
		JPanel exampleDistanceFunctionSelection = new JPanel(new GridLayout(2,2));
		exampleDistanceFunctionSelection.setBorder(BorderFactory.createTitledBorder("Example Distance Function"));
		exampleDistanceFunctionButtonGroup = new ButtonGroup();
		addRadioButton("Summation", exampleDistanceFunctionButtonGroup, exampleDistanceFunctionSelection, new SimpleSummation());
		addRadioButton("Absolute Summation", exampleDistanceFunctionButtonGroup, exampleDistanceFunctionSelection, new exampleDistanceCombinationStrategies.ManhattanDistance());
		addRadioButton("Euclidean Distance", exampleDistanceFunctionButtonGroup, exampleDistanceFunctionSelection, new EuclidianDistance());
		addRadioButton("Number Of Similar Features", exampleDistanceFunctionButtonGroup, exampleDistanceFunctionSelection, new NumberOfSimilarFeatures());
		add(exampleDistanceFunctionSelection, c);
		c.gridy++;

		//create the collection of radio buttons to specify which point distance function to use
		JPanel pointDistanceFunctionSelection = new JPanel(new GridLayout(3,2));
		pointDistanceFunctionSelection.setBorder(BorderFactory.createTitledBorder("Composite Distance Function"));
		pointDistanceFunctionButtonGroup = new ButtonGroup();
		addRadioButton("Summation", pointDistanceFunctionButtonGroup, pointDistanceFunctionSelection, new compositeFeatureDistanceStrategies.ManhattanDistance());
		addRadioButton("Euclidean Distance", pointDistanceFunctionButtonGroup, pointDistanceFunctionSelection, new EuclideanDistance());
		addRadioButton("Maximum Distance", pointDistanceFunctionButtonGroup, pointDistanceFunctionSelection, new ChebyshevDistance());
		addRadioButton("Number Of Similar Features", pointDistanceFunctionButtonGroup, pointDistanceFunctionSelection, new NumberSimilarFeatures());
		addRadioButton("Exactly Equivalent", pointDistanceFunctionButtonGroup, pointDistanceFunctionSelection, new CompositeEquivalence());
		add(pointDistanceFunctionSelection, c);
		c.gridy++;

		//create the collection of radio buttons to specify which character distance function to use
		JPanel characterDistanceFunctionSelection = new JPanel(new GridLayout(3,2));
		characterDistanceFunctionSelection.setBorder(BorderFactory.createTitledBorder("Character Distance Function"));
		characterDistanceFunctionButtonGroup = new ButtonGroup();
		addRadioButton("Characters Equal", characterDistanceFunctionButtonGroup, characterDistanceFunctionSelection, new CharacterEquals());
		addRadioButton("Characters Equal, Ignore Case", characterDistanceFunctionButtonGroup, characterDistanceFunctionSelection, new CharacterEqualsIgnoreCase());
		addRadioButton("Character Difference", characterDistanceFunctionButtonGroup, characterDistanceFunctionSelection, new CharacterDistance());
		addRadioButton("Character Difference, Ignore Case", characterDistanceFunctionButtonGroup, characterDistanceFunctionSelection, new CharacterDistanceIgnoreCase());
		addRadioButton("Absolute Character Difference", characterDistanceFunctionButtonGroup, characterDistanceFunctionSelection, new CharacterAbsDistance());
		addRadioButton("Absolute Character Difference, Ignore Case", characterDistanceFunctionButtonGroup, characterDistanceFunctionSelection, new CharacterAbsDistanceIgnoreCase());
		add(characterDistanceFunctionSelection, c);
		c.gridy++;

		//create the collection of radio buttons to specify which double distance function to use
		JPanel doubleDistanceFunctionSelection = new JPanel(new GridLayout(1,2));
		doubleDistanceFunctionSelection.setBorder(BorderFactory.createTitledBorder("Double Distance Function"));
		doubleDistanceFunctionButtonGroup = new ButtonGroup();
		addRadioButton("Double Difference", doubleDistanceFunctionButtonGroup, doubleDistanceFunctionSelection, new DoubleDistance());
		addRadioButton("Absolute Double Difference", doubleDistanceFunctionButtonGroup, doubleDistanceFunctionSelection, new DoubleAbsDistance());
		add(doubleDistanceFunctionSelection, c);
		c.gridy++;

		//create the collection of radio buttons to specify which integer distance function to use
		JPanel integerDistanceFunctionSelection = new JPanel(new GridLayout(1,2));
		integerDistanceFunctionSelection.setBorder(BorderFactory.createTitledBorder("Integer Distance Function"));
		integerDistanceFunctionButtonGroup = new ButtonGroup();
		addRadioButton("Integer Difference", integerDistanceFunctionButtonGroup, integerDistanceFunctionSelection, new IntegerDistance());
		addRadioButton("Absolute Integer Difference", integerDistanceFunctionButtonGroup, integerDistanceFunctionSelection, new IntegerAbsDistance());
		add(integerDistanceFunctionSelection, c);
		c.gridy++;

		//create the collection of radio buttons to specify which string distance function to use
		JPanel stringDistanceFunctionSelection = new JPanel(new GridLayout(4,2));
		stringDistanceFunctionSelection.setBorder(BorderFactory.createTitledBorder("String Distance Function"));
		stringDistanceFunctionButtonGroup = new ButtonGroup();
		addRadioButton("Equal Strings", stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringEquals());
		addRadioButton("Equal Strings, Ignore Case", stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringEqualsIgnoreCase());
		addRadioButton("Number of equal characters", stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringSimilarity());
		addRadioButton("Number of equal characters, Ignore Case", stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringSimilarityIgnoreCase());
		addRadioButton("Equal Length", stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringLengthEquals());
		addRadioButton("Difference in Length", stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringLengthDistance());
		addRadioButton("Equal Word Count", stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringWordCountEquals());
		addRadioButton("Difference in Word Count", stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringWordCountDistance());
		add(stringDistanceFunctionSelection, c);
		c.gridy++;

		//create input panel for specifying number of nearest neighbors to use
		JPanel kInputPanel = new JPanel(new GridLayout(1,2));
		kInputPanel.add(new JLabel("Specify how many neighbors to use in prediction: "));
		kValueTextField = new JTextField(20);
		kInputPanel.add(kValueTextField);
		kInputPanel.setVisible(true);
		add(kInputPanel, c);
		c.gridy++;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;

		//use sub panel to place buttons beside each other
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		
		//include button to submit prediction format and get result
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new doneButtonListener());
		panel.add(doneButton);

		//include button to cancel prediction
		JButton cancelButon = new JButton("Cancel");
		cancelButon.addActionListener(new CancelButtonListener());
		panel.add(cancelButon);
		add(panel, c);
	}

	/**
	 * adds a radio button to a selected button group and to the panel
	 * 
	 * @param text the text of the button to add to a ButtonGroup and JPanel
	 * @param buttonGroup the ButtonGroup to add the radio button to
	 * @param panel the JPanel to add the radio button to
	 */
	private void addRadioButton(String text, ButtonGroup buttonGroup, JPanel panel){
		JRadioButton radioButton = new JRadioButton(text);
		buttonGroup.add(radioButton);
		panel.add(radioButton);
	}

	/**
	 * adds a radio button to a selected button group and to the panel
	 * 
	 * @param text the text of the button to add to a ButtonGroup and JPanel
	 * @param buttonGroup the ButtonGroup to add the radio button to
	 * @param panel the JPanel to add the radio button to
	 * @param distanceFunction DistanceFunction that the radio button denotes
	 */
	private void addRadioButton(String text, ButtonGroup buttonGroup, JPanel panel, CompositeDistanceStrategy distanceFunction){
		addRadioButton(text, buttonGroup, panel);
		compositeDistanceFunctionSelectionMap.put(text, distanceFunction);
	}

	/**
	 * adds a radio button to a selected button group and to the panel
	 * 
	 * @param text the text of the button to add to a ButtonGroup and JPanel
	 * @param buttonGroup the ButtonGroup to add the radio button to
	 * @param panel the JPanel to add the radio button to
	 * @param distanceFunction DistanceFunction that the radio button denotes
	 */
	private void addRadioButton(String text, ButtonGroup buttonGroup, JPanel panel, SimpleDistanceStrategy distanceFunction){
		addRadioButton(text, buttonGroup, panel);
		simpleDistanceFunctionSelectionMap.put(text, distanceFunction);
	}

	/**
	 * adds a radio button to a selected button group and to the panel
	 * 
	 * @param text the text of the button to add to a ButtonGroup and JPanel
	 * @param buttonGroup the ButtonGroup to add the radio button to
	 * @param panel the JPanel to add the radio button to
	 * @param distanceFunction DistanceFunction that the radio button denotes
	 */
	private void addRadioButton(String text, ButtonGroup buttonGroup, JPanel panel, ExampleDistanceStrategy distanceFunction){
		addRadioButton(text, buttonGroup, panel);
		exampleDistanceFunctionSelectionMap.put(text, distanceFunction);
	}
	
	private void returnToDisplayContents(){
		//update the problem set in the frame
		m.setProblem(problem);
		m.setPreferredSize(new Dimension(400, 648));
		//return to the main display of problem info
		m.setContentPane(new DisplayProblemContents(m));
		m.pack();
	}

	/*listener to predict output based on customizations given*/
	private class doneButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int k;
			//try to get k value from user
			try{
				k = Integer.parseInt(kValueTextField.getText());

				//k must be greater than zero and less than the number of training examples
				if(k <= 0 || k >problem.getNumberOfTrainingExamples())
					throw new NumberFormatException();
			}catch(NumberFormatException e1){
				//display error message if user inputs a number out of range or not a number
				JOptionPane.showMessageDialog(null, "Error: Please enter a valid integer between 1 and " + problem.getNumberOfTrainingExamples());
				return;
			}

			//get the selected example distance function user specified
			String selectedExampleDistanceFunction = getSelectedButton(exampleDistanceFunctionButtonGroup);
			//if no button is selected, show error message
			if(selectedExampleDistanceFunction == null){
				JOptionPane.showMessageDialog(null, "Error: Please select an example distance function to use");
				return;
			}

			//get the selected point distance function user specified
			String selectedPointDistanceFunction = getSelectedButton(pointDistanceFunctionButtonGroup);
			//if no button is selected, show error message
			if(selectedPointDistanceFunction == null){
				JOptionPane.showMessageDialog(null, "Error: Please select a composite distance function to use");
				return;
			}

			//get the selected character distance function user specified
			String selectedCharacterDistanceFunction = getSelectedButton(characterDistanceFunctionButtonGroup);
			//if no button is selected, show error message
			if(selectedCharacterDistanceFunction == null){
				JOptionPane.showMessageDialog(null, "Error: Please select a character distance function to use");
				return;
			}

			//get the selected double distance function user specified
			String selectedDoubleDistanceFunction = getSelectedButton(doubleDistanceFunctionButtonGroup);
			//if no button is selected, show error message
			if(selectedDoubleDistanceFunction == null){
				JOptionPane.showMessageDialog(null, "Error: Please select a double distance function to use");
				return;
			}

			//get the selected integer distance function user specified
			String selectedIntegerDistanceFunction = getSelectedButton(integerDistanceFunctionButtonGroup);
			//if no button is selected, show error message
			if(selectedIntegerDistanceFunction == null){
				JOptionPane.showMessageDialog(null, "Error: Please select a integer distance function to use");
				return;
			}

			//get the selected String distance function user specified
			String selectedStringDistanceFunction = getSelectedButton(stringDistanceFunctionButtonGroup);
			//if no button is selected, show error message
			if(selectedStringDistanceFunction == null){
				JOptionPane.showMessageDialog(null, "Error: Please select a String distance function to use");
				return;
			}

			//set problem stategies to selected strategies
			m.configurePrediction(k, exampleDistanceFunctionSelectionMap.get(selectedExampleDistanceFunction),
					compositeDistanceFunctionSelectionMap.get(selectedPointDistanceFunction), simpleDistanceFunctionSelectionMap.get(selectedCharacterDistanceFunction),
					simpleDistanceFunctionSelectionMap.get(selectedDoubleDistanceFunction), simpleDistanceFunctionSelectionMap.get(selectedIntegerDistanceFunction),
					simpleDistanceFunctionSelectionMap.get(selectedStringDistanceFunction));

			//return to main display
			returnToDisplayContents();
			m.setConfigured(true);
		}

		/**
		 * gets the text associated with the selected button in a group
		 * 
		 * @param buttonGroup the ButtonGroup to get the selected button from
		 * @return the text associated with the selected button, null if no button selected
		 */
		private String getSelectedButton(ButtonGroup buttonGroup){
			for(Enumeration<AbstractButton> buttonList = buttonGroup.getElements(); buttonList.hasMoreElements();){
				AbstractButton button = buttonList.nextElement();
				if(button.isSelected())
					return button.getText();
			}
			return null;
		}
	}

	/*action listener for cancelling operation*/
	private class CancelButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			returnToDisplayContents();
		}
	}
}
