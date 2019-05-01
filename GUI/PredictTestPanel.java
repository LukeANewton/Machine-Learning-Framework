package GUI;


import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.SwingUtilities;

import problemComponents.CompositeFeature;
import problemComponents.Feature;
import Control.Prediction;
import problemComponents.Problem;
import problemComponents.SimpleFeature;
import problemComponents.SimpleFeatureType;
import simpleFeatureDistanceStrategies.*;

/**
 * This class has the contents and functionality to predict a value for a test exmaple
 * 
 * currently always uses all training examples and euclidean distance. will be eventually updated
 * to have these be customizable
 * 
 * @author luke newton
 *
 */
public class PredictTestPanel extends Container {
	//serialized ID
	private static final long serialVersionUID = 22892476582418299L;
	//index value of the test example to predict
	private int exampleToPredict;
	//the problem set currently working with
	private Problem problem;
	//textfield to get number of nearest neighbors
	private JTextField kValueTextField;
	//group of radio buttons to specify which distance functions to use
	private ButtonGroup pointDistanceFunctionButtonGroup, characterDistanceFunctionButtonGroup, doubleDistanceFunctionButtonGroup, integerDistanceFunctionButtonGroup, stringDistanceFunctionButtonGroup;
	private HashMap<String, SimpleDistanceStrategy> distanceFunctionSelectionMap;

	/**
	 * Constructor
	 * 
	 * @param problem the problem set currently working with
	 * @param exampleToPredict index value of the test example to predict
	 */
	public PredictTestPanel(Controller c, int exampleToPredict){
		super(); 
		this.exampleToPredict = exampleToPredict;
		this.problem = c.problem;
		c.setMenuBarEnabled(false);
		distanceFunctionSelectionMap = new HashMap<>();

		createContent();
	}

	private void createContent(){
		removeAll();

		//need a row for specifying  k value, specifying point distance function and done button
		setLayout(new GridLayout(7,1));

		/*create the collection of radio buttons to specify which point distance function to use*/
		//put all related buttons in a bordered box
		JPanel pointDistanceFunctionSelection = new JPanel(new GridLayout(2,2));
		pointDistanceFunctionSelection.setBorder(BorderFactory.createTitledBorder("Point Distance Function"));

		//button group only allows one button to be selected at a time
		pointDistanceFunctionButtonGroup = new ButtonGroup();

		/*add radio buttons for each point distance option to ButtonGroup and Panel*/
		JRadioButton radioButton = new JRadioButton("SimpleSummation");
		addRadioButton(radioButton, pointDistanceFunctionButtonGroup, pointDistanceFunctionSelection);

		radioButton = new JRadioButton("EuclideanDistance");
		addRadioButton(radioButton, pointDistanceFunctionButtonGroup, pointDistanceFunctionSelection);

		radioButton = new JRadioButton("ManhattanDistance");
		addRadioButton(radioButton, pointDistanceFunctionButtonGroup, pointDistanceFunctionSelection);

		radioButton = new JRadioButton("NumberOfSimilarFeatures");
		addRadioButton(radioButton, pointDistanceFunctionButtonGroup, pointDistanceFunctionSelection);
		//add bordered panel for radio buttons to main panel
		pointDistanceFunctionSelection.setVisible(true);
		add(pointDistanceFunctionSelection);

		/*create the collection of radio buttons to specify which character distance function to use*/
		//put all related buttons in a bordered box
		JPanel characterDistanceFunctionSelection = new JPanel(new GridLayout(3,2));
		characterDistanceFunctionSelection.setBorder(BorderFactory.createTitledBorder("Character Distance Function"));

		//button group only allows one button to be selected at a time
		characterDistanceFunctionButtonGroup = new ButtonGroup();

		/*add radio buttons for each distance option to ButtonGroup and Panel*/
		String radioButtonName = "Characters Equal";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, characterDistanceFunctionButtonGroup, characterDistanceFunctionSelection, new CharacterEquals());

		radioButtonName = "Characters Equal, Ignore Case";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, characterDistanceFunctionButtonGroup, characterDistanceFunctionSelection, new CharacterEqualsIgnoreCase());

		radioButtonName = "Character Difference";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, characterDistanceFunctionButtonGroup, characterDistanceFunctionSelection, new CharacterDistance());

		radioButtonName = "Character Difference, Ignore Case";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, characterDistanceFunctionButtonGroup, characterDistanceFunctionSelection, new CharacterDistanceIgnoreCase());

		radioButtonName = "Absolute Character Difference";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, characterDistanceFunctionButtonGroup, characterDistanceFunctionSelection, new CharacterAbsDistance());

		radioButtonName = "Absolute Character Difference, Ignore Case";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, characterDistanceFunctionButtonGroup, characterDistanceFunctionSelection, new CharacterAbsDistanceIgnoreCase());

		//add bordered panel for radio buttons to main panel
		characterDistanceFunctionSelection.setVisible(true);
		add(characterDistanceFunctionSelection);

		/*create the collection of radio buttons to specify which double distance function to use*/
		//put all related buttons in a bordered box
		JPanel doubleDistanceFunctionSelection = new JPanel(new GridLayout(1,2));
		doubleDistanceFunctionSelection.setBorder(BorderFactory.createTitledBorder("Double Distance Function"));

		//button group only allows one button to be selected at a time
		doubleDistanceFunctionButtonGroup = new ButtonGroup();

		/*add radio buttons for each distance option to ButtonGroup and Panel*/
		radioButtonName = "Double Difference";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, doubleDistanceFunctionButtonGroup, doubleDistanceFunctionSelection, new DoubleDistance());

		radioButtonName = "Absolute Double Difference";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, doubleDistanceFunctionButtonGroup, doubleDistanceFunctionSelection, new DoubleAbsDistance());

		//add bordered panel for radio buttons to main panel
		doubleDistanceFunctionSelection.setVisible(true);
		add(doubleDistanceFunctionSelection);

		/*create the collection of radio buttons to specify which integer distance function to use*/
		//put all related buttons in a bordered box
		JPanel integerDistanceFunctionSelection = new JPanel(new GridLayout(1,2));
		integerDistanceFunctionSelection.setBorder(BorderFactory.createTitledBorder("Integer Distance Function"));

		//button group only allows one button to be selected at a time
		integerDistanceFunctionButtonGroup = new ButtonGroup();

		/*add radio buttons for each distance option to ButtonGroup and Panel*/
		radioButtonName = "Integer Difference";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, integerDistanceFunctionButtonGroup, integerDistanceFunctionSelection, new IntegerDistance());

		radioButtonName = "Absolute Integer Difference";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, integerDistanceFunctionButtonGroup, integerDistanceFunctionSelection, new IntegerAbsDistance());

		//add bordered panel for radio buttons to main panel
		integerDistanceFunctionSelection.setVisible(true);
		add(integerDistanceFunctionSelection);

		/*create the collection of radio buttons to specify which string distance function to use*/
		//put all related buttons in a bordered box
		JPanel stringDistanceFunctionSelection = new JPanel(new GridLayout(3,2));
		stringDistanceFunctionSelection.setBorder(BorderFactory.createTitledBorder("String Distance Function"));

		//button group only allows one button to be selected at a time
		stringDistanceFunctionButtonGroup = new ButtonGroup();

		/*add radio buttons for each distance option to ButtonGroup and Panel*/
		radioButtonName = "String Equals";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringEquals());

		radioButtonName = "String Equals, Ignore Case";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringEqualsIgnoreCase());

		radioButtonName = "Number of equal characters in a String";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringSimilarity());

		radioButtonName = "Number of equal characters in a String, Ignore Case";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringSimilarityIgnoreCase());

		radioButtonName = "Equal String Length";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringLengthEquals());

		radioButtonName = "String Equal Word Count";
		radioButton = new JRadioButton(radioButtonName);
		addRadioButton(radioButton, stringDistanceFunctionButtonGroup, stringDistanceFunctionSelection, new StringWordCountEquals());

		//add bordered panel for radio buttons to main panel
		stringDistanceFunctionSelection.setVisible(true);
		add(stringDistanceFunctionSelection);

		JPanel kInputPanel = new JPanel(new GridLayout(1,2));
		//add a JLabel to describe TextField
		kInputPanel.add(new JLabel("Specify how many neighbors to use in prediction: "));
		/*add textfield to get input for k*/
		kValueTextField = new JTextField(20);
		kInputPanel.add(kValueTextField);
		kInputPanel.setVisible(true);
		add(kInputPanel);

		//include button to finish editting point and update problem
		JButton predictButton = new JButton("Predict");
		predictButton.addActionListener(new predictButtonListener());
		add(predictButton);
	}

	/**
	 * adds a radio button to a selected button group and to the panel
	 * 
	 * @param radioButton the button to add to a ButtonGroup and JPanel
	 * @param buttonGroup the ButtonGroup to add the radio button to
	 * @param panel the JPanel to add the radio button to
	 */
	private void addRadioButton(JRadioButton radioButton, ButtonGroup buttonGroup, JPanel panel){
		buttonGroup.add(radioButton);
		panel.add(radioButton);
	}

	/**
	 * adds a radio button to a selected button group and to the panel
	 * 
	 * @param radioButton the button to add to a ButtonGroup and JPanel
	 * @param buttonGroup the ButtonGroup to add the radio button to
	 * @param panel the JPanel to add the radio button to
	 * @param distanceFunction DistanceFunction that the radio button denotes
	 */
	private void addRadioButton(JRadioButton radioButton, ButtonGroup buttonGroup, JPanel panel, SimpleDistanceStrategy distanceFunction){
		addRadioButton(radioButton, buttonGroup, panel);
		distanceFunctionSelectionMap.put(radioButton.getText(), distanceFunction);
	}

	/*listener to predict output based on customizations given*/
	private class predictButtonListener implements ActionListener{
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

			//get the selected point distance function user specified
			String selectedPointDistanceFunction = getSelectedButton(pointDistanceFunctionButtonGroup);
			//if no button is selected, show error message
			if(selectedPointDistanceFunction == null){
				JOptionPane.showMessageDialog(null, "Error: Please select a point distance function to use");
				return;
			}
			
			//get the selected character distance function user specified
			String selectedCharacterDistanceFunction = getSelectedButton(characterDistanceFunctionButtonGroup);
			//if no button is selected, show error message
			if(selectedCharacterDistanceFunction == null){
				JOptionPane.showMessageDialog(null, "Error: Please select a character distance function to use");
				return;
			}
			problem.setDistanceFunction(distanceFunctionSelectionMap.get(selectedCharacterDistanceFunction), SimpleFeatureType.CHARACTER);
			
			//get the selected double distance function user specified
			String selectedDoubleDistanceFunction = getSelectedButton(doubleDistanceFunctionButtonGroup);
			//if no button is selected, show error message
			if(selectedDoubleDistanceFunction == null){
				JOptionPane.showMessageDialog(null, "Error: Please select a double distance function to use");
				return;
			}
			problem.setDistanceFunction(distanceFunctionSelectionMap.get(selectedDoubleDistanceFunction), SimpleFeatureType.DOUBLE);

			//get the selected integer distance function user specified
			String selectedIntegerDistanceFunction = getSelectedButton(integerDistanceFunctionButtonGroup);
			//if no button is selected, show error message
			if(selectedIntegerDistanceFunction == null){
				JOptionPane.showMessageDialog(null, "Error: Please select a integer distance function to use");
				return;
			}
			problem.setDistanceFunction(distanceFunctionSelectionMap.get(selectedIntegerDistanceFunction), SimpleFeatureType.INTEGER);

			//get the selected String distance function user specified
			String selectedStringDistanceFunction = getSelectedButton(stringDistanceFunctionButtonGroup);
			//if no button is selected, show error message
			if(selectedStringDistanceFunction == null){
				JOptionPane.showMessageDialog(null, "Error: Please select a String distance function to use");
				return;
			}
			problem.setDistanceFunction(distanceFunctionSelectionMap.get(selectedStringDistanceFunction), SimpleFeatureType.STRING);
			
			//make prediction
			Object prediction = Prediction.getPrediction(k, problem, exampleToPredict);

			//ask the user if they want to compare the predicted value against a known value
			int result = JOptionPane.showConfirmDialog(null, "prediction result is " + prediction.toString() + ", do you want to compare against a known value?",
					"prediction dialog box", JOptionPane.YES_NO_OPTION);
			
			/*if user says yes, get a value from them and update the accuracy*/
			if(result == JOptionPane.YES_OPTION){
				JPanel initPanel = new JPanel();
				initPanel.add(new JLabel("Enter known output value:"));
				JTextField numOfFeaturesField = new JTextField(20);
				initPanel.add(numOfFeaturesField);
				
				//get known ouput from user and parse
				SimpleFeature knownOutput = SimpleFeature.parseSimpleFeature(
						JOptionPane.showInputDialog(initPanel, "Enter known output value:")
						);
				//update accuracy
				problem.updateAccuracy(prediction, knownOutput.getContents());
				//display update accuracy
				JOptionPane.showMessageDialog(null, "Current prediction accuracy is " + problem.getPredictionError().getAccuracy());
			}
			
			//ask the user if they want to replace the output of test example in problem with this prediction
			result = JOptionPane.showConfirmDialog(null, "prediction result is " + prediction.toString() + ", do you want to overwrite \nthe example you predicted for to contain this result?",
					"prediction dialog box", JOptionPane.YES_NO_OPTION);

			/*if user says yes, add that prediction to the test example*/
			if(result == JOptionPane.YES_OPTION){
				//the the current test exmaple
				ArrayList<Feature> dataElements = problem.getTestExample(exampleToPredict).getFields();
				//set the output of the test example to the predicted value
				dataElements.set(dataElements.size() - 1, CompositeFeature.parseFeature(prediction.toString()));
				//replace the test example in the problem with updated one
				problem.editTestExample(exampleToPredict, dataElements);
			}

			//get the frame by getting the source of this event, and the window the source exists in
			Controller controller = (Controller) SwingUtilities.windowForComponent((JButton)e.getSource());
			//update the problem set in the frame
			controller.problem = problem;

			//return to the main display of problem info
			controller.setContentPane(new DisplayProblemContents(controller));
			controller.pack();
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
}
