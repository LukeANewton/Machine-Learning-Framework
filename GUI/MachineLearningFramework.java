package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import compositeFeatureDistanceStrategies.CompositeDistanceStrategy;
import exampleDistanceCombinationStrategies.ExampleDistanceStrategy;
import predictionElements.Prediction;
import predictionElements.ProblemConfiguration;
import predictionElements.ProblemOptimization;
import problemComponents.CompositeFeature;
import problemComponents.Feature;
import problemComponents.Problem;
import problemComponents.SimpleFeature;
import simpleFeatureDistanceStrategies.SimpleDistanceStrategy;

/**
 * This class is the frame which is displayed when the project is run. The frame contains a menu bar
 * and a content pane which changes everytime an option in the menu bar is selected to do the required
 * function.
 * 
 * @author luke newton, madelyn krasnay
 */
public class MachineLearningFramework  extends JFrame{
	private static final long serialVersionUID = -7251613294872651907L;
	//the problem est we are working with
	private Problem problem;
	//the index value of the  most recently selected training example
	private int selectedTrainingExample;
	//the index value of the  most recently selected test example
	private int selectedTestExample;
	//indicator of wether a problem has been created/loaded yet
	private boolean createdProblem;
	//indicatr of whether prediciton parameters has been configured yet
	private boolean configuredPrediction;
	//the number of nearest neighbors to find in prediction
	private int k;
	//the size to use in optimization alorithm pool
	private int populationSize;
	//number of generations to run optimization algorithm for
	private int numberOfGenerations;
	//mutation chance in optimization algorithm
	private double mutationRate;
	//seed value used in optimization algorithm
	private int randomSeed;
	//the example strategy to use in predictions
	private ExampleDistanceStrategy exStrat;
	//the composite strategy to use in predictions
	private CompositeDistanceStrategy compStrat;
	//the character distance strategy to use in predictions
	private SimpleDistanceStrategy charStrat;
	//the string distance strategy to use in predictions
	private SimpleDistanceStrategy stringStrat;
	//the integer distance strategy to use in predictions
	private SimpleDistanceStrategy intStrat;
	//the double distance strategy to use in predictions
	private SimpleDistanceStrategy doubleStrat;

	/**main function to run program*/
	public static void main(String[] args) {
		MachineLearningFramework m = new MachineLearningFramework();
		//default contents will be displaying the problem examples	
		m.setContentPane(new DisplayProblemContents(m));
		m.pack();
	}

	/**Constructor*/
	public MachineLearningFramework(){
		//call to parent class JFrame
		super("machine learning framework");
		problem = new Problem(1);
		createdProblem = false;
		configuredPrediction = false;
		//initialize optimization parameters
		populationSize = 100;
		//number of generations to run optimization algorithm for
		numberOfGenerations = 50;
		//mutation chance in optimization algorithm
		mutationRate = 0.001;
		//seed value used in optimization algorithm
		randomSeed = 123456789;
		//set selected examples to -1 to indicate no selection
		selectedTrainingExample = -1;
		selectedTestExample = -1;
		//setup options for frame and create menu bar
		makeFrame();
	}
	
	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public int getNumberOfGenerations() {
		return numberOfGenerations;
	}

	public void setNumberOfGenerations(int numberOfGenerations) {
		this.numberOfGenerations = numberOfGenerations;
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}

	public int getRandomSeed() {
		return randomSeed;
	}

	public void setRandomSeed(int randomSeed) {
		this.randomSeed = randomSeed;
	}

	/**set indicator of whether a valid problem exists*/
	public void setCreatedProblem(boolean createdProblem) {
		this.createdProblem = createdProblem;
	}

	/**set indicator of whether the current problem has been configured*/
	public void setConfigured(boolean b) {
		configuredPrediction = b;
	}

	/**set the number of nearest neighbors to use in a prediction*/
	public void setK(int k){
		this.k = k;
	}

	/*set the example distance strategy to use in predictions*/
	public void setExampleStrategy(ExampleDistanceStrategy exStrat) {
		this.exStrat = exStrat;
	}

	/**set the composite distance strategy to use in predicitons*/
	public void setCompositeStrategy(CompositeDistanceStrategy compStrat) {
		this.compStrat = compStrat;
	}

	/**set the character distacne strategy to use in predicitons*/
	public void setCharacterStrategy(SimpleDistanceStrategy charStrat) {
		this.charStrat = charStrat;
	}

	/**set the string distance strategy to use in predictions*/
	public void setStringStrategy(SimpleDistanceStrategy stringStrat) {
		this.stringStrat = stringStrat;
	}

	/**set the integer distance strategy to use in predictions*/
	public void setIntegerStrategy(SimpleDistanceStrategy intStrat) {
		this.intStrat = intStrat;
	}

	/**set the double distance strategy to use in predictions*/
	public void setDoubleStrategy(SimpleDistanceStrategy doubleStrat) {
		this.doubleStrat = doubleStrat;
	}

	/**returns the current problem set*/
	public Problem getProblem(){
		return problem;
	}

	/**set the current problem set*/
	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	/**returns the index of the selected training example in its JList display*/
	public int getSelectedTrainingExample() {
		return selectedTrainingExample;
	}

	/**returns the index of the selected test example in its JList display*/
	public int getSelectedTestExample() {
		return selectedTestExample;
	}

	/**sets the index of the selected training example*/
	public void setSelectedTrainingExample(int selectedTrainingExample) {
		this.selectedTrainingExample = selectedTrainingExample;
	}

	/**setts the index of the selected test example*/
	public void setSelectedTestExample(int selectedTestExample) {
		this.selectedTestExample = selectedTestExample;
	}

	/**sets all the strategies used in predictions to the passed strategies*/
	public void configurePrediction(int k, ExampleDistanceStrategy exStrat, CompositeDistanceStrategy compStrat,
			SimpleDistanceStrategy charStrat, SimpleDistanceStrategy doubleStrat, SimpleDistanceStrategy intStrat, 
			SimpleDistanceStrategy stringStrat){
		this.k = k;
		this.exStrat = exStrat;
		this.compStrat = compStrat;
		this.charStrat = charStrat;
		this.doubleStrat = doubleStrat;
		this.intStrat = intStrat;
		this.stringStrat = stringStrat;
	}

	/**toggles the ability to select items in the menu bar*/
	public void setMenuBarEnabled(boolean value){
		//re enable all menu items
		JMenuBar menuBar = getJMenuBar();
		for(int i = 0; i < menuBar.getMenuCount(); i++){
			int items = menuBar.getMenu(i).getItemCount();
			for(int j = 0; j < items; j++)
				menuBar.getMenu(i).getItem(j).setEnabled(value);
		}
	}

	/**set up the frame options and contents of the frame*/
	private void makeFrame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(400, 648));
		//create the menu bar for the frame
		makeMenuBar();

		pack();
		setVisible(true);
	}

	/**adds a menu item to the specified menu
	 * 
	 * @param text the label of the new menu item
	 * @param menu the menu to add the item to
	 * @param activity the ActionListener to trigger when the item is selected
	 */
	private void addMenuItem(String text, JMenu menu, ActionListener activity){
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.addActionListener(activity);
		menu.add(menuItem);
	}

	/**create the menu bar for the frame*/
	private void makeMenuBar(){
		//the menu bar
		JMenuBar menuBar = new JMenuBar();

		//create problem menu
		JMenu problemMenu = new JMenu("Problem");
		addMenuItem("create", problemMenu, new CreateProblemListener());
		addMenuItem("save", problemMenu, new SaveProblemListener());
		addMenuItem("load", problemMenu, new LoadProblemListener());
		addMenuItem("edit weights", problemMenu, new EditWeightListener());
		addMenuItem("edit field names", problemMenu, new EditFieldNameListener());
		menuBar.add(problemMenu);

		//create training example menu
		JMenu trainingExampleMenu = new JMenu("training example");
		addMenuItem("add", trainingExampleMenu, new AddTrainingExampleListener());
		addMenuItem("remove", trainingExampleMenu, new RemoveTrainingExampleListener());
		addMenuItem("edit", trainingExampleMenu, new EditTrainingExampleListener());
		menuBar.add(trainingExampleMenu);

		//create test example menu
		JMenu testExampleMenu = new JMenu("test example");
		addMenuItem("add", testExampleMenu, new AddTestExampleListener());
		addMenuItem("remove", testExampleMenu, new RemoveTestExampleListener());
		addMenuItem("edit", testExampleMenu, new EditTestExampleListener());
		menuBar.add(testExampleMenu);

		//create prediction menu
		JMenu predictMenu = new JMenu("Prediction");
		addMenuItem("make prediction", predictMenu, new PredictTestExampleListener());
		addMenuItem("optimize prediction configuration", predictMenu, new OptimizeConfigListener());
		addMenuItem("manually configure prediction", predictMenu, new PredictConfigListener());
		addMenuItem("optimization settings", predictMenu, new OptSettingsListener());
		
		menuBar.add(predictMenu);

		setJMenuBar(menuBar);
	}


	/**
	 * This method creates a pop-up window containing a textfeild for the number 
	 * of features in a new dataSet and a done button. when the button is pressed 
	 * the method checks if the number is valid, meaning it is a positive integer. 
	 * If the entry is invalid a error message appears. The method only exits after 
	 * a valid Input had been retrieved.
	 * 
	 * @return int: the number of features
	 */
	private int askForNumberOfFeatures() {
		int numOfFeatures = 0;
		do {	
			String result = JOptionPane.showInputDialog(null, new JLabel("Enter number of features:"), "Number of features", JOptionPane.PLAIN_MESSAGE);

			//cancel was selected in previous input dialog
			if(result == null){
				setContentPane(new DisplayProblemContents(this));
				return 0;
			}

			//Attempt to parse to int
			try{
				numOfFeatures = Integer.parseInt(result);
				if(numOfFeatures <= 0)
					throw new NumberFormatException();
			} 
			//show error message
			catch(NumberFormatException err) {
				JOptionPane.showMessageDialog(null, "Error: Please enter a valid positive integer");
			}      
		} while(numOfFeatures <= 0);
		return numOfFeatures;
	}

	/**obtains the parent JFrame from a JMenuItem*/
	private MachineLearningFramework getWindowFromMenuItem(JMenuItem item){
		JPopupMenu parent = (JPopupMenu)item.getParent();
		return (MachineLearningFramework)SwingUtilities.getWindowAncestor(parent.getInvoker());
	}

	/**action listener for creating a new problem set*/
	private class CreateProblemListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int numFeatures = askForNumberOfFeatures();
			if(numFeatures > 0){
				setContentPane(new CreateProblemPanel(numFeatures, getWindowFromMenuItem((JMenuItem)e.getSource())));
				pack();
			}
		}
	}

	/**action listener for editing the weights of a problem*/
	private class EditWeightListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				setContentPane(new EditWeightPanel(getWindowFromMenuItem((JMenuItem)e.getSource())));
				pack();
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}
	
	/**action listener for editing the filed names of a problem*/
	private class EditFieldNameListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				setContentPane(new EditFieldNamePanel(getWindowFromMenuItem((JMenuItem)e.getSource())));
				pack();
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}


	/**action listener to save the current contents of the problem*/
	private class SaveProblemListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				try {
					JFileChooser jfc = new JFileChooser();
					int result = jfc.showSaveDialog(getWindowFromMenuItem((JMenuItem)e.getSource()));

					if(result == JFileChooser.APPROVE_OPTION){
						//ensure all strategies are updated
						problem.setStrategies(exStrat, compStrat, charStrat, doubleStrat, intStrat, stringStrat);
						//save problem
						problem.serializedExport(jfc.getSelectedFile().getAbsolutePath());
					}
				} catch (Exception e1) {
					System.out.println("Export failed");
				}
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**action listener to load a problem into program from a predefined file*/
	private class LoadProblemListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				JFileChooser jfc = new JFileChooser();
				int result = jfc.showOpenDialog(getWindowFromMenuItem((JMenuItem)e.getSource()));

				if(result == JFileChooser.APPROVE_OPTION){
					problem.serializedImport(jfc.getSelectedFile().getAbsolutePath());
					createdProblem = true;
					configuredPrediction = false;
				}
			} catch (Exception e1) {
				System.out.println(e1);
				System.out.println("import failed");
				return;
			} 

			//if the contents of the problem is currently being displayed, it need to be updated to show import
			if(getContentPane() instanceof DisplayProblemContents){
				((DisplayProblemContents)getContentPane()).updateProblem(problem);
				pack();
			}
		}
	}

	/**Action listener to add a training example to the problem set*/
	private class AddTrainingExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				setContentPane(new AddExamplePanel(ExampleType.TrainingExample, getWindowFromMenuItem((JMenuItem)e.getSource())));
				pack();
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to add a test example to the problem set*/
	private class AddTestExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				setContentPane(new AddExamplePanel(ExampleType.TestExample, getWindowFromMenuItem((JMenuItem)e.getSource())));
				pack();	
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to edit a training example in the problem set*/
	private class EditTrainingExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem && selectedTrainingExample >= 0){
				setContentPane(new EditExamplePanel(ExampleType.TrainingExample, getWindowFromMenuItem((JMenuItem)e.getSource()), selectedTrainingExample));
				pack();
			}else if(selectedTrainingExample == -1){
				JOptionPane.showMessageDialog(null, "Error: Please select a training example first");
			}else if (!createdProblem){
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to edit a test example in the problem set*/
	private class EditTestExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem && selectedTestExample >= 0){
				setContentPane(new EditExamplePanel(ExampleType.TestExample, getWindowFromMenuItem((JMenuItem)e.getSource()), selectedTestExample));
				pack();
			}else if(selectedTestExample == -1){
				JOptionPane.showMessageDialog(null, "Error: Please select a test example first");
			}else if (!createdProblem){
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to remove a training example from the problem set*/
	private class RemoveTrainingExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem && selectedTrainingExample >= 0){
				//set contents to remove a training example
				problem.removeTrainingExample(selectedTrainingExample);
				selectedTrainingExample = -1;
				((DisplayProblemContents)getContentPane()).updateProblem(problem);
				pack();
			}else if(selectedTrainingExample == -1){
				JOptionPane.showMessageDialog(null, "Error: Please select a training example first");
			}else if (!createdProblem){
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to remove a test example from the problem set*/
	private class RemoveTestExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem && selectedTestExample >= 0){
				//set contents to remove a test example
				problem.removeTestExample(selectedTestExample);
				selectedTestExample = -1;
				((DisplayProblemContents)getContentPane()).updateProblem(problem);
				pack();
			}else if(selectedTestExample == -1){
				JOptionPane.showMessageDialog(null, "Error: Please select a test example first");
			}else if (!createdProblem){
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to set the strategies and weights in the problem set*/
	private class PredictConfigListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				setContentPane(new PredictionConfigurePanel(getWindowFromMenuItem((JMenuItem)e.getSource()), selectedTestExample));
				pack();
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}
	
	/**Action listener to set the parameters for optimization algorithm*/
	private class OptSettingsListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
				setContentPane(new OptimiazationSettingsPanel(getWindowFromMenuItem((JMenuItem)e.getSource())));
				pack();
		}
	}

	/**Action listener to automatically optimize the strategies and weights in the problem set*/
	private class OptimizeConfigListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				int result;
				//warn user the task takes a while and needs lots of data
				if(problem.getNumberOfTrainingExamples() < 100){
					 result = JOptionPane.showConfirmDialog(null, "This process is best suited towards sets with large amounts of data, do you wish to continue?",
								"confimation box", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				}else{
					 result = JOptionPane.showConfirmDialog(null, "This process can take some time, do you wish to continue?",
								"confimation box", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				}

				//if user says yes, continues with operation
				if(result == JOptionPane.YES_OPTION){
					ProblemOptimization opt = new ProblemOptimization(populationSize, numberOfGenerations, mutationRate, randomSeed);
					ProblemConfiguration pc = opt.optimizePrredictionConfiguration(problem);
					problem = pc.getProblem();
					k = pc.getK();
				}
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}
	
	/**Action listener to predict an output value for a test example in the problem set*/
	private class PredictTestExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem && configuredPrediction && selectedTestExample >= 0){
				//ensure all examples have the same distance metrics
				problem.setStrategies(exStrat, compStrat, charStrat, doubleStrat, intStrat, stringStrat);

				//make prediction
				Object prediction = Prediction.getPrediction(k, problem, selectedTestExample);

				//ask the user if they want to compare the predicted value against a known value
				int result = JOptionPane.showConfirmDialog(null, "prediction result is " + prediction.toString() + ", do you want to compare against a known value?",
						"prediction dialog box", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

				/*if user says yes, get a value from them and update the accuracy*/
				if(result == JOptionPane.YES_OPTION){
					JPanel initPanel = new JPanel();
					initPanel.add(new JLabel("Enter known output value:"));
					JTextField numOfFeaturesField = new JTextField(20);
					initPanel.add(numOfFeaturesField);

					//get known ouput from user and parse
					SimpleFeature knownOutput = SimpleFeature.parseSimpleFeature(
							JOptionPane.showInputDialog(initPanel, "Enter known output value:", "Known value", JOptionPane.PLAIN_MESSAGE)
							);
					//update accuracy
					problem.updateAccuracy(prediction, knownOutput.getContents());
					//display update accuracy
					JOptionPane.showMessageDialog(null, "Current prediction accuracy is " + problem.getPredictionError().getAccuracy());
				}

				//ask the user if they want to replace the output of test example in problem with this prediction
				result = JOptionPane.showConfirmDialog(null, "prediction result is " + prediction.toString() + ", do you want to overwrite \nthe example you predicted for to contain this result?",
						"prediction dialog box", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

				/*if user says yes, add that prediction to the test example*/
				if(result == JOptionPane.YES_OPTION){
					//the the current test exmaple
					ArrayList<Feature> dataElements = problem.getTestExample(selectedTestExample).getFields();
					//set the output of the test example to the predicted value
					dataElements.set(dataElements.size() - 1, CompositeFeature.parseFeature(prediction.toString()));
					//replace the test example in the problem with updated one
					problem.editTestExample(selectedTestExample, dataElements);
				}
			}else if(!createdProblem){
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}else if(!configuredPrediction){
				JOptionPane.showMessageDialog(null, "Error: Please configure the prediction first");
			}else if(selectedTestExample == -1){
				JOptionPane.showMessageDialog(null, "Error: Please select a test example first");
			}
		}
	}
}
