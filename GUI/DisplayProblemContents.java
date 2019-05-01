package GUI;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import problemComponents.Feature;
import problemComponents.Problem;

/**
 * This class is the contents and functionality to display the problem contents
 * 
 * @author luke newton, madelyn krasnay
 * @version 3
 */
public class DisplayProblemContents extends Container {
	//serialized ID
	private static final long serialVersionUID = -5170428175418141225L;
	//current problem set working with
	private Problem problem;
	//list to display training examples
	protected JList<ArrayList<Feature>> trainingExamples;
	//list to display test examples
	protected JList<ArrayList<Feature>> testExamples;

	/**
	 * Constructor
	 * 
	 * @param problem problem set currently working with
	 */
	public DisplayProblemContents(Problem problem){
		super();
		this.problem = problem;

		createContent();
	}

	/**create contents to display problem*/
	private void createContent(){
		//clear the controller
		removeAll();
		
		setLayout(new GridLayout(2, 1));
		
		//create JList for training examples
		JScrollPane scrollPane = new JScrollPane();
		DefaultListModel<ArrayList<Feature>> features = new DefaultListModel<>();
		for(int i = 0; i < problem.getNumberOfTrainingExamples(); i++){
			features.addElement(problem.getTrainingExample(i).getFields());
		}
		trainingExamples = new JList<ArrayList<Feature>>(features);
		DefaultListCellRenderer renderer =  (DefaultListCellRenderer)trainingExamples.getCellRenderer();  
		renderer.setHorizontalAlignment(JLabel.LEFT);  
		trainingExamples.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		trainingExamples.addListSelectionListener(new SelectTrainingExampleListener());
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), 
						"Training Examples", TitledBorder.LEFT, TitledBorder.TOP)));
		scrollPane.setViewportView(trainingExamples);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane);

		//create JList for test examples
		scrollPane = new JScrollPane();
		features = new DefaultListModel<>();
		for(int i = 0; i < problem.getNumberOfTestExamples(); i++){
			features.addElement(problem.getTestExample(i).getFields());
		}
		testExamples = new JList<ArrayList<Feature>>(features);
		renderer =  (DefaultListCellRenderer)testExamples.getCellRenderer();  
		renderer.setHorizontalAlignment(JLabel.LEFT); 
		testExamples.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		testExamples.addListSelectionListener(new SelectTestExampleListener());
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), 
						"Test Examples", TitledBorder.LEFT, TitledBorder.TOP)));
		scrollPane.setViewportView(testExamples);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane);;
	}

	/**
	 * update problem set and JLists. Used when importing a new problem set
	 * 
	 * @param problem new problem set to watch with JLists
	 */
	protected void updateProblem(Problem problem){
		this.problem = problem;
		
		DefaultListModel<ArrayList<Feature>> features = new DefaultListModel<>();
		for(int i = 0; i < problem.getNumberOfTrainingExamples(); i++){
			features.addElement(problem.getTrainingExample(i).getFields());
		}
		trainingExamples.setModel(features);
		features = new DefaultListModel<>();
		for(int i = 0; i < problem.getNumberOfTestExamples(); i++){
			features.addElement(problem.getTestExample(i).getFields());
		}
		testExamples.setModel(features);
	}

	/*listener for changing the selected training example*/
	private class SelectTrainingExampleListener implements ListSelectionListener{
		/*this warning is given in casting e.getSource() to JList<ArrayList<Feature>>.
		 * This is suppressed because there is only on object using this listener and it is
		 * of type JList<ArrayList<Feature>>, so there will never be a ClassCastException thrown here
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void valueChanged(ListSelectionEvent e) {
			Controller controller = (Controller) SwingUtilities.windowForComponent((JList<ArrayList<Feature>>)e.getSource());
			
			if(problem.getNumberOfTrainingExamples() == 1){
				return;
			}else {
				controller.selectedTrainingExample = trainingExamples.getSelectedIndex();
			}
		}
	}

	/*listener for changing the selected test example*/
	private class SelectTestExampleListener implements ListSelectionListener{
		/*this warning is given in casting e.getSource() to JList<ArrayList<Feature>>.
		 * This is supressed because there is only on object using this listener and it is
		 * of type JList<ArrayList<Feature>>, so there will never be a ClassCastException thrown here
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void valueChanged(ListSelectionEvent e) {
			Controller controller = (Controller) SwingUtilities.windowForComponent((JList<ArrayList<Feature>>)e.getSource());
			
			if(problem.getNumberOfTestExamples() == 1){
				return;
			}else {
				controller.selectedTestExample = testExamples.getSelectedIndex();
			}
		}
	}
}