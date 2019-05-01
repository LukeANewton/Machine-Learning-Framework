package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import problemComponents.Problem;

/**
 * This class is the frame which is displayed when the project is run. The fram contains a menu bar
 * and a content pane which changes everytime an option in the menu bar is selected to do the required
 * function.
 * 
 * @author luke newton, madelyn krasnay
 * @version 2
 *
 */
public class Controller  extends JFrame{
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

	/**Constructor*/
	public Controller(){
		//call to parent class JFrame
		super("machine learning framework");
		problem = new Problem(1);
		createdProblem = false;
		//setup options for frame and create menu bar
		makeFrame();
	}

	public void setCreatedProblem(boolean createdProblem) {
		this.createdProblem = createdProblem;
	}
	
	/**main function to run program*/
	public static void main(String[] args) {
		Controller controller = new Controller();
		//default contents of the controller will be displaying the problem examples	
		controller.setContentPane(new DisplayProblemContents(new Problem(1)));
		controller.pack();
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

		JMenuItem predictTestExample = new JMenuItem("predict");
		predictTestExample.addActionListener(new PredictTestListener());
		testExampleMenu.add(predictTestExample);

		//add test example menu to menu bar
		menuBar.add(testExampleMenu);

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
				setContentPane(new DisplayProblemContents(problem));
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
				setContentPane(new CreateProblemPanel(numFeatures, problem));
				pack();
			}

		}
	}

	/**action listener for editing the weights of a problem*/
	private class EditWeightListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				//set contents to edit weights
				setContentPane(new EditWeightPanel(problem));
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
					problem.serializedExport();
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
				problem.serializedImport();
				createdProblem = true;
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
				//set contents to add a new training example
				setContentPane(new AddExamplePanel(ExampleType.TrainingExample, problem));
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
				//set contents to add a new test example
				setContentPane(new AddExamplePanel(ExampleType.TestExample, problem));
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
				//set contents to edit a training example
				setContentPane(new EditExamplePanel(ExampleType.TrainingExample, problem, selectedTrainingExample));
				pack();
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to edit a test example in the problem set*/
	private class EditTestExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				//set contents to edit a test example
				setContentPane(new EditExamplePanel(ExampleType.TestExample, problem, selectedTestExample));
				pack();
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to remove a training example from the problem set*/
	private class RemoveTrainingExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				//set contents to remove a training example
				problem.removeTrainingExample(selectedTrainingExample);
				
				//if the contetns of the problem is currently being displayed, it need to be updated to show import
				if(getContentPane() instanceof DisplayProblemContents){
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
				//set contents to remove a test example
				problem.removeTestExample(selectedTestExample);
				
				//if the contetns of the problem is currently being displayed, it need to be updated to show import
				if(getContentPane() instanceof DisplayProblemContents){
					((DisplayProblemContents)getContentPane()).updateProblem(problem);
					pack();
				}
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}

	/**Action listener to predict an output value for a test example int the problem set*/
	private class PredictTestListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(createdProblem){
				//set contents to predict a test example output
				setContentPane(new PredictTestPanel(problem, selectedTestExample));
				pack();
			}else{
				JOptionPane.showMessageDialog(null, "Error: Please create/load a problem first");
			}
		}
	}
}
