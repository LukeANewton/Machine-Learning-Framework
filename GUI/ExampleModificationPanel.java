package GUI;
import java.util.ArrayList;

import problemComponents.CompositeFeature;
import problemComponents.Feature;

/**
 * Superclass for AddExamplePanel and EditExamplePanel to reduce code repetition
 * 
 * @author luke newton, madelyn krasnay
 *
 */
public abstract class ExampleModificationPanel extends UserInputPanel {
	//serialized ID
	private static final long serialVersionUID = -8196813862398776945L;
	//specifies whether we are adding a test example or training example
	protected ExampleType exampleType;

	/**
	 * Constructor
	 * 
	 * @param exampleType specifies whether we are adding a training example or test example
	 * @param problem the problem set we are working with
	 */
	public ExampleModificationPanel(ExampleType exampleType, MachineLearningFramework m){
		super(m);
		this.exampleType = exampleType;
	}
	
	/**
	 * create and add the required content of this container
	 * 
	 * @param n the number of attribute/features in a training/test example
	 */
	protected void createContent(int n){
		//create the list of texts for titles to go with each inputPanel
		ArrayList<String> panelTitle = new ArrayList<>();
		for(int i = 0; i < n; i++){
			panelTitle.add("Enter value for " + problem.getFieldName(i) + " field: ");
		}
		
		//call to super create content
		super.createContent(n, panelTitle);
	}
	
	/**
	 * parses the input from each textfield and creates an example out of it
	 * 
	 * @return a training/testExample as an ArrayList<Feature>
	 */
	protected ArrayList<Feature> buildExampleFromTextFields(){
		ArrayList<Feature> dataElements = new ArrayList<>();
		for(int i = 0; i < panels.size(); i++){
			dataElements.add(CompositeFeature.parseFeature(panels.get(i).getInput()));
		}
		return dataElements;
	}
}
