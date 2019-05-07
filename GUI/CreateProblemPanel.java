package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import problemComponents.Problem;

/**
 * This class is the content and functionality required to create a new problem set.
 * 
 * @author luke newton, madelyn krasnay
 */
public class CreateProblemPanel extends UserInputContentPanel {
	private static final long serialVersionUID = -3929743187401378236L;
	//number of features in a training/test example
	private int numberOfFeatures;
	
	/**
	 *Constructor
	 * 
	 * @param n number of features we want in a new problem
	 * @param m the parent JFrame this panel will be contained in
	 */
	public CreateProblemPanel(int n, MachineLearningFramework m){
		super(m);
		numberOfFeatures = n;
		m.setMenuBarEnabled(false);
		createContent(n);
	}

	/**
	 * create and add the required content of this container
	 * 
	 * @param n the number of attribute/features in a training/test example
	 */
	protected void createContent(int n){
		//create the list of texts for labels to go with each textField
		ArrayList<String> fieldNames = new ArrayList<>();
		for(int i = 0; i < n; i++){
			fieldNames.add("Enter field " + (i + 1) + " name: ");
		}
		//call to super create content
		super.createContent(n, fieldNames);
		
		//add specialized listener for this type of panel
		doneButton.addActionListener(new AcceptFieldNamesListener());
	}

	/*listener to create new problem set*/
	private class AcceptFieldNamesListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			/*get the input from each InputPanel in panels list*/
			ArrayList<String> fieldNames = new ArrayList<>();
			for(TextInputPanel panel : panels){
				fieldNames.add(panel.getInput());
			}

			/*give default weightings of all 100*/
			double[] defaultWeightings = new double[numberOfFeatures];
			for(int i = 0; i < defaultWeightings.length; i++){
				defaultWeightings[i] = 100;
			}

			//create the new problem
			problem = new Problem(numberOfFeatures, fieldNames, defaultWeightings);
			
			//now that a probelm has been created, set this to true
			MachineLearningFramework m = (MachineLearningFramework)SwingUtilities.getRoot(((JButton)e.getSource()));
			m.setCreatedProblem(true);
			
			returnToDisplayScreen(m);
		}
	}
}
