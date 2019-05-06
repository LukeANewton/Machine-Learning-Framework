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

import Control.Prediction;
import compositeFeatureDistanceStrategies.CompositeDistanceStrategy;
import exampleDistanceCombinationStrategies.ExampleDistanceStrategy;
import problemComponents.CompositeFeature;
import problemComponents.Feature;
import problemComponents.Problem;
import problemComponents.SimpleFeature;
import simpleFeatureDistanceStrategies.SimpleDistanceStrategy;

/**
 * This class is the frame which is displayed when the project is run. The fram contains a menu bar
 * and a content pane which changes everytime an option in the menu bar is selected to do the required
 * function.
 * 
 * @author luke newton, madelyn krasnay
 *
 */
public class MachineLearningFramework  extends JFrame{
	//serializable ID
	private static final long serialVersionUID = -7251613294872651907L;
	//the problem est we are working with
	protected Problem problem;
	//the index value of the  most recently selected training example
	protected int selectedTrainingExample;
	//the index value of the  most recently selected test example
	protected int selectedTestExample;
	//indicator of wether a problem has been created/loaded yet
	private boolean createdProblem;
	//indicatr of whether prediciton parameters has been configured yet
	private boolean configuredPrediction;
	//the number of nearest neighbors to find in prediction
	private int k;

	/**Constructor*/
	public MachineLearningFramework(){
		//call to parent class JFrame
		super("machine learning framework");
		problem = new Problem(1);
		createdProblem = false;
		configuredPrediction = false;
		//set selected examples to -1 to indicate no selection
		selectedTrainingExample = -1;
		selectedTestExample = -1;
		//setup options for frame and create menu bar
		makeFrame();
	}

	public void setCreatedProblem(boolean createdProblem) {
		this.createdProblem = createdProblem;
	}


	public void setConfigured(boolean b) {
		configuredPrediction = b;
	}

	public void setK(int k){
		this.k = k;
	}

	public void configurePrediction(int k, ExampleDistanceStrategy exStrat, CompositeDistanceStrategy compStrat,
			SimpleDistanceStrategy charStrat, SimpleDistanceStrategy doubleStrat, SimpleDistanceStrategy intStrat, 
			SimpleDistanceStrategy stringStrat){
		this.k = k;
		problem.setStrategies(exStrat, compStrat, charStrat, doubleStrat, intStrat, stringStrat);
	}

	public void setMenuBarEnabled(boolean value){
		//re enable all menu items
		JMenuBar menuBar = getJMenuBar();
		for(int i = 0; i < menuBar.getMenuCount(); i++){
			int items = menuBar.getMenu(i).getItemCount();
			for(int j = 0; j < items; j++)
				menuBar.getMenu(i).getItem(j).setEnabled(value);
		}
	}

	/**main function to run program*/
	public static void main(String[] args) {
		MachineLearningFramework m = new MachineLearningFramework();
		//default contents will be displaying the problem examples	
		m.setContentPane(new DisplayProblemContents(m));
		m.pack();
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

	/**create the menu bar for the frame*/
	private void makeMenuBar(){
		//the menu bar
		JMenuBar menuBar = new JMenuBar();
		//create problem menu
		JMenu problemMenu = new JMenu("Problem");

		/*add options to problem menu*/
		JMenuItem createProblem = new JMenuItem("create");
		createProblem.addActionListener(new CreateProblemListener());
		problemMenu.add(createProblem);

		JMenuItem saveProblem = new JMenuItem("save");
		saveProblem.addActionListener(new SaveProblemListener());
		problemMenu.add(saveProblem);

		JMenuItem loadProblem = new JMenuItem("load");
		loadProblem.addActionListener(new LoadProblemListener());
		problemMenu.add(loadProblem);

		JMenuItem editWeights = new JMenuItem("edit weights");
		editWeights.addActionListener(new EditWeightListener());
		problemMenu.add(editWeights);

		//add problem menu to menu bar
		menuBar.add(problemMenu);

		//create training example menu
		JMenu trainingExampleMenu = new JMenu("training example");

		/*add options to training example menu*/
		JMenuItem addTrainingExample = new JMenuItem("add");
		addTrainingExample.addActionListener(new AddTrainingExampleListener());
		trainingExampleMenu.add(addTrainingExample);

		JMenuItem removeTrainingExample = new JMenuItem("remove");
		removeTrainingExample.addActionListener(new RemoveTrainingExampleListener());
		trainingExampleMenu.add(removeTrainingExample);

		JMenuItem editTrainingExample = new JMenuItem("edit");
		editTrainingExample.addActionListener(new EditTrainingExampleListener());
		trainingExampleMenu.add(editTrainingExample);
		//add training example menu to menu bar
		menuBar.add(trainingExampleMenu);

		//create test example menu
		JMenu testExampleMenu = new JMenu("test example");

		/*add options to test example menu*/
		JMenuItem addTestExample = new JMenuItem("add");
		addTestExample.addActionListener(new AddTestExampleListener());
		testExampleMenu.add(addTestExample);

		JMenuItem removeTestExample = new JMenuItem("remove");
		removeTestExample.addActionListener(new RemoveTestExampleListener());
		testExampleMenu.add(removeTestExample);

		JMenuItem editTestExample = new JMenuItem("edit");
		editTestExample.addActionListener(new EditTestExampleListener());
		testExampleMenu.add(editTestExample);

		//add test example menu to menu bar
		menuBar.add(testExampleMenu);

		JMenu predictMenu = new JMenu("Prediction");
		JMenuItem configPrediction = new JMenuItem("configure predictions");
		configPrediction.addActionListener(new PredictConfigListener());
		predictMenu.add(configPrediction);

		JMenuItem predictTestExample = new JMenuItem("make prediction");
		predictTestExample.addActionListener(new PredictTestExampleListener());
		predictMenu.add(predictTestExample);
		menuBar.add(predictMenu);

		//set menu bar visible
		menuBar.setVisible(true);

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

	/**action listener for creating a new problem set*/
	private class CreateProblemListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int numFeatures = askForNumberOfFeatures();
			if(numFeatures > 0){
				JMenuItem source = (JMenuItem)e.getSource();
				JPopupMenu parent = (JPopupMenu)source.getParent();
				MachineLearningFramework c =  (MachineLearningFramework)SwingUtilities.getWindowAncestor(parent.getInvoker());

				setContentPane(new CreateProblemPanel(numFeatures, c));
				pack();
			}

		}
	}

	/**action listener for editing the weights of a problem*/
	private class EditWeightListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				JMenuItem source = (JMenuItem)e.getSource();
				JPopupMenu parent = (JPopupMenu)source.getParent();
				MachineLearningFramework c =  (MachineLearningFramework)SwingUtilities.getRoot(parent.getInvoker());

				setContentPane(new EditWeightPanel(c));
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
					JMenuItem source = (JMenuItem)e.getSource();
					JPopupMenu parent = (JPopupMenu)source.getParent();
					MachineLearningFramework m =  (MachineLearningFramework)SwingUtilities.getRoot(parent.getInvoker());

					int result = jfc.showSaveDialog(m);
					if(result == JFileChooser.APPROVE_OPTION)
						problem.serializedExport(jfc.getSelectedFile().getAbsolutePath());
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
				JMenuItem source = (JMenuItem)e.getSource();
				JPopupMenu parent = (JPopupMenu)source.getParent();
				MachineLearningFramework m =  (MachineLearningFramework)SwingUtilities.getRoot(parent.getInvoker());

				int result = jfc.showOpenDialog(m);
				if(result == JFileChooser.APPROVE_OPTION){
					problem.serializedImport(jfc.getSelectedFile().getAbsolutePath());
					createdProblem = true;
				}
			} catch (Exception e1) {
				System.out.println(e1);
				System.out.println("import failed");
				return;
			} 

			//if the contetns of the problem is currently being displayed, it need to be updated to show import
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
				JMenuItem source = (JMenuItem)e.getSource();
				JPopupMenu parent = (JPopupMenu)source.getParent();
				MachineLearningFramework m =  (MachineLearningFramework)SwingUtilities.getRoot(parent.getInvoker());

				setContentPane(new AddExamplePanel(ExampleType.TrainingExample, m));
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
				JMenuItem source = (JMenuItem)e.getSource();
				JPopupMenu parent = (JPopupMenu)source.getParent();
				MachineLearningFramework m =  (MachineLearningFramework)SwingUtilities.getRoot(parent.getInvoker());

				setContentPane(new AddExamplePanel(ExampleType.TestExample, m));
				pack();	
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to edit a training example in the problem set*/
	private class EditTrainingExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				if(selectedTrainingExample == -1){
					JOptionPane.showMessageDialog(null, "Error: Please select a training example first");
				}else{
					JMenuItem source = (JMenuItem)e.getSource();
					JPopupMenu parent = (JPopupMenu)source.getParent();
					MachineLearningFramework m =  (MachineLearningFramework)SwingUtilities.getRoot(parent.getInvoker());

					setContentPane(new EditExamplePanel(ExampleType.TrainingExample, m, selectedTrainingExample));
					pack();
				}
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to edit a test example in the problem set*/
	private class EditTestExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				if(selectedTestExample == -1){
					JOptionPane.showMessageDialog(null, "Error: Please select a test example first");
				}else{
					JMenuItem source = (JMenuItem)e.getSource();
					JPopupMenu parent = (JPopupMenu)source.getParent();
					MachineLearningFramework m =  (MachineLearningFramework)SwingUtilities.getRoot(parent.getInvoker());

					setContentPane(new EditExamplePanel(ExampleType.TestExample, m, selectedTestExample));
					pack();
				}
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to remove a training example from the problem set*/
	private class RemoveTrainingExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){	
				if(selectedTrainingExample == -1){
					JOptionPane.showMessageDialog(null, "Error: Please select a training example first");
				}else{
					//set contents to remove a training example
					problem.removeTrainingExample(selectedTrainingExample);
					selectedTrainingExample = -1;
					((DisplayProblemContents)getContentPane()).updateProblem(problem);
					pack();
				}
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to remove a test example from the problem set*/
	private class RemoveTestExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				if(selectedTestExample == -1){
					JOptionPane.showMessageDialog(null, "Error: Please select a test example first");
				}else{
					//set contents to remove a test example
					problem.removeTestExample(selectedTestExample);
					selectedTestExample = -1;
					((DisplayProblemContents)getContentPane()).updateProblem(problem);
					pack();
				}		
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to predict an output value for a test example int the problem set*/
	private class PredictConfigListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				JMenuItem source = (JMenuItem)e.getSource();
				JPopupMenu parent = (JPopupMenu)source.getParent();
				MachineLearningFramework m =  (MachineLearningFramework)SwingUtilities.getRoot(parent.getInvoker());

				setContentPane(new PredictionConfigurePanel(m, selectedTestExample));
				pack();
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to predict an output value for a test example int the problem set*/
	private class PredictTestExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				if(configuredPrediction){
					if(selectedTestExample == -1){
						JOptionPane.showMessageDialog(null, "Error: Please select a test example first");
					}else{
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
					}	
				}else{
					JOptionPane.showMessageDialog(null, "Error: Please configure the prediction first");
				}
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}
}
