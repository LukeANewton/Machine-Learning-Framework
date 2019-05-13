package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import problemComponents.Problem;


/**
 * Content panel used to set the parameters for the optimization algorithm
 * 
 * @author Luke Newton
 */
public class OptimiazationSettingsPanel extends JPanel {
	private static final long serialVersionUID = -3792046903443998780L;
	//the problem set currently working with
	private Problem problem;
	//the parent JFrame this panel is displayed in
	private MachineLearningFramework m;
	//the size to use in optimization alorithm pool
	private TextInputPanel populationSize;
	//number of generations to run optimization algorithm for
	private TextInputPanel numberGenerations;
	//mutation chance in optimization algorithm
	private TextInputPanel mutationRate;
	//seed value used in optimization algorithm
	private TextInputPanel seed;

	/**Constructor */
	public OptimiazationSettingsPanel(MachineLearningFramework m){
		super(); 
		this.problem = m.getProblem();
		this.m = m;
		m.setMenuBarEnabled(false);
		m.setPreferredSize(new Dimension(360, 350));
		createContent();
	}

	private void createContent(){
		removeAll();

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;

		populationSize = new TextInputPanel("Specify size of the population pool");
		populationSize.setTextFieldText(Integer.toString(m.getPopulationSize()));
		add(populationSize, c);
		c.gridy++;
		
		numberGenerations = new TextInputPanel("Specify the number of generations to run");
		numberGenerations.setTextFieldText(Integer.toString(m.getNumberOfGenerations()));
		add(numberGenerations, c);
		c.gridy++;
		
		mutationRate = new TextInputPanel("Specify the mutation rate to use");
		mutationRate.setTextFieldText(Double.toString(m.getMutationRate()));
		add(mutationRate, c);
		c.gridy++;
		
		seed = new TextInputPanel("Specify the random seed to use");
		seed.setTextFieldText(Integer.toString(m.getRandomSeed()));
		add(seed, c);
		c.gridy++;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;

		//use sub panel to place buttons beside each other
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		
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

	private void returnToDisplayContents(){
		//update the problem set in the frame
		m.setProblem(problem);
		m.setPreferredSize(new Dimension(400, 648));
		//return to the main display of problem info
		m.setContentPane(new DisplayProblemContents(m));
		m.pack();
	}

	/*listener to updating optimization settings*/
	private class doneButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//set population size	
			try{
				int popSize = Integer.parseInt(populationSize.getInput());
				if(popSize < 0)
					throw new NumberFormatException();
				
				m.setPopulationSize(popSize);			
			} catch(NumberFormatException e1){
				//show error message if panel has invalid contents
				JOptionPane.showMessageDialog(null, "Error: Population size must be a positive integer");
				return;
			}	
			//set number of generations	
			try{
				int numGen = Integer.parseInt(numberGenerations.getInput());
				if(numGen < 0)
					throw new NumberFormatException();
				
				m.setNumberOfGenerations(numGen);			
			} catch(NumberFormatException e1){
				//show error message if panel has invalid contents
				JOptionPane.showMessageDialog(null, "Error: Number of generations size must be a positive integer");
				return;
			}
			//set mutation rate
			try{
				double mutRate = Double.parseDouble(mutationRate.getInput());
				if(mutRate < 0 || mutRate > 1)
					throw new NumberFormatException();
				
				m.setMutationRate(mutRate);			
			} catch(NumberFormatException e1){
				//show error message if panel has invalid contents
				JOptionPane.showMessageDialog(null, "Error: mutation rate must be between 0 and 1");
				return;
			}
			//set seed
			try{		
				m.setRandomSeed(Integer.parseInt(seed.getInput()));			
			} catch(NumberFormatException e1){
				//show error message if panel has invalid contents
				JOptionPane.showMessageDialog(null, "Error: seed must be an integer");
				return;
			}
			returnToDisplayContents();
		}
	}

	/*action listener for cancelling operation*/
	private class CancelButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			returnToDisplayContents();
		}
	}
}
