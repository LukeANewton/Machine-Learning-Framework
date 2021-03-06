package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import problemComponents.Feature;

/**
 * This class contains the content and functionality to edit a training/test example
 * 
 * @author luke newton, madelyn krasnay
 *
 */
public class EditExamplePanel extends ExampleModificationPanel {
	private static final long serialVersionUID = -8203983755809461898L;
	//index value of the example we are editing
	private int exampleToEdit;

	/**
	 * Constructor
	 * 
	 * @param exampleType specifies whether we are adding a training example or test example
	 * @param problem the problem set we are working with
	 * @param exampleToEdit index of the example to edit
	 */
	public EditExamplePanel(ExampleType exampleType, MachineLearningFramework m, int exampleToEdit){
		super(exampleType, m);
		this.exampleToEdit = exampleToEdit;
		createContent(m.getProblem().getNumberOfFields());
	}

	/**
	 * create and add the required content of this container
	 * 
	 * @param n the number of attribute/features in a training/test example
	 */
	protected void createContent(int n){
		//call to super crate content
		super.createContent(n);

		/*fill the contents of the panels with the current contents of that feature*/
		int numPanels = panels.size();
		for(int i = 0; i < numPanels; i++){	
			if(exampleType == ExampleType.TestExample)
				panels.get(i).setTextFieldText(problem.getTestExample(exampleToEdit).getFields().get(i).toString());
			else 
				panels.get(i).setTextFieldText(problem.getTrainingExample(exampleToEdit).getFields().get(i).toString());
		}
		//add specialized listener for this type of panel
		doneButton.addActionListener(new AcceptExampleListener());
	}

	/*listener to finish editing a point and update problem*/
	private class AcceptExampleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			/*build an example from the contents of each text field*/
			ArrayList<Feature> dataElements = buildExampleFromTextFields();

			/*the exampleType eNum passed to constructor tells whether we're editting a training or test example*/
			if(exampleType == ExampleType.TestExample)
				problem.editTestExample(exampleToEdit, dataElements);
			else if(exampleType == ExampleType.TrainingExample)
				problem.editTrainingExample(exampleToEdit, dataElements);

			returnToDisplayScreen(e);
		}
	}
}
