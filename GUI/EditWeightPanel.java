package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;



/**
 * This class contains the contents and functionality to edit the weights of a problem set
 * 
 * @author luke newton, madelyn krasnay
 * @version 2
 */
public class EditWeightPanel extends UserInputPanel {
	//serialized ID
	private static final long serialVersionUID = 6041499093779519108L;
	
	/**
	 * Constructor
	 * 
	 * @param problem the problem set currently working with
	 */
	public EditWeightPanel(Controller c){
		super(c);
		
		createContent(c.problem.getNumberOfFields());
	}
	
	/**
	 * create and add the required content of this container
	 * 
	 * @param n the number of attribute/features in a training/test example
	 */
	protected void createContent(int n){
		//create the list of texts for title to go with each panel
		ArrayList<String> panelTitles = new ArrayList<>();
		for(int i = 0; i < n; i++){
			panelTitles.add("Enter weight for " + problem.getFieldName(i) + " field: ");
		}
		
		//call to super create content
		super.createContent(n, panelTitles);
		
		/*fill the contents of the panels with the current weights of that feature*/
		for(int i = 0; i < panels.size(); i++){	
			panels.get(i).setTextFieldText(Double.toString(problem.getWeight(i)));
		}
		
		doneButton.addActionListener(new AcceptWeightsListener());
	}
	
	/*action listener for applying new weights to problem*/
	private class AcceptWeightsListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//create new set of weights to add to
			double[] weights = new double[problem.getNumberOfFields()];
			//boolean value to tell us if we have a valid set of weights
			boolean validWeights = true;
			
			//add the contents of panels to the weights and check if weights are valid
			for(int i = 0; i < panels.size(); i++){
				try{
					//try parsing weight value from panel
					weights[i] = Double.parseDouble(panels.get(i).getInput());
					
					//weights cannot be negative
					if(weights[i] < 0)
						throw new NumberFormatException();
				} catch(NumberFormatException e1){
					//show error message if panel has invalid contents
					JOptionPane.showMessageDialog(null, "Error: Please enter a valid positive number");
					validWeights = false;
					return;
				}
			}
			
			//if all weights are valid, apply them to problem and return to data display
			if(validWeights){
				//set weights in problem with new weights inputed
				problem.setWeights(weights);
				
				returnToDisplayScreen(e);
			}
		}
	}
}
