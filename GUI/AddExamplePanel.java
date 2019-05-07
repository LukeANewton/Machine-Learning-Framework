package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import problemComponents.Feature;

/**
 * This class is the content and functionality required to add a new training/test example to the problem set.
 * 
 * @author luke newton
 */
public class AddExamplePanel extends ExampleModificationPanel {
	private static final long serialVersionUID = -6068066953807677256L;

	/**
	 * Constructor
	 * 
	 * @param exampleType specifies whether we are adding a training example or test example
	 * @param m the parent JFrame this panel will be contained in
	 */
	public AddExamplePanel(ExampleType exampleType, MachineLearningFramework m){
		super(exampleType, m);
		createContent(m.getProblem().getNumberOfFields());
	}

	/**
	 * create and add the required contents of this container
	 * 
	 * @param n the number of attributes/features in a training/test example
	 */
	protected void createContent(int n){
		//call to super create content
		super.createContent(n);

		//add specialized listener for this type of panel
		doneButton.addActionListener(new AcceptExampleListener());
	}

	/*listener for adding new example to problem set*/
	private class AcceptExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			/*build an example from the contents of each text field*/
			ArrayList<Feature> dataElements = buildExampleFromTextFields();
			
			/*the ExampleType eNum passed to constructor tells whether we're adding a training or test example*/
			if(exampleType == ExampleType.TestExample){
				problem.addTestExample(dataElements);
			} else if(exampleType == ExampleType.TrainingExample){
				problem.addTrainingExample(dataElements);
			}
			returnToDisplayScreen(e);
		}
	}

}
