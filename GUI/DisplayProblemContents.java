package GUI;


import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import problemComponents.Feature;
import problemComponents.Problem;

/**
 * This class is the contents and functionality to display the problem contents
 * 
 * @author luke newton, madelyn krasnay
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
	//the JFrame this will be displayed in
	private Controller controller;

	/**
	 * Constructor
	 * 
	 * @param problem problem set currently working with
	 */
	public DisplayProblemContents(Controller c){
		super();
		problem = c.problem;
		controller = c;

		controller.selectedTrainingExample = -1;
		controller.selectedTestExample = -1;

		controller.setMenuBarEnabled(true);
		
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
		for(int i = 0; i < problem.getNumberOfTrainingExamples(); i++)
			features.addElement(problem.getTrainingExample(i).getFields());
		trainingExamples = new JList<ArrayList<Feature>>(features);
		trainingExamples.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		trainingExamples.addMouseListener(new DeselectTrainingExampleListener());
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
		for(int i = 0; i < problem.getNumberOfTestExamples(); i++)
			features.addElement(problem.getTestExample(i).getFields());
		testExamples = new JList<ArrayList<Feature>>(features);
		testExamples.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		testExamples.addMouseListener(new DeselectTestExampleListener());
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), 
						"Test Examples", TitledBorder.LEFT, TitledBorder.TOP)));
		scrollPane.setViewportView(testExamples);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane);
	}

	/**
	 * update problem set and JLists. Used when importing a new problem set
	 * 
	 * @param problem new problem set to watch with JLists
	 */
	protected void updateProblem(Problem problem){
		this.problem = problem;

		DefaultListModel<ArrayList<Feature>> features = new DefaultListModel<>();
		for(int i = 0; i < problem.getNumberOfTrainingExamples(); i++)
			features.addElement(problem.getTrainingExample(i).getFields());
		trainingExamples.setModel(features);

		features = new DefaultListModel<>();
		for(int i = 0; i < problem.getNumberOfTestExamples(); i++)
			features.addElement(problem.getTestExample(i).getFields());
		testExamples.setModel(features);
	}

	/*listener on Jlist for deselecting items*/
	private class DeselectTrainingExampleListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			JList<?> list = (JList<?>)e.getSource();
			Controller c = (Controller)SwingUtilities.getRoot(list);
			int index = list.locationToIndex(e.getPoint());

			if(c.selectedTrainingExample == index){
				list.clearSelection();
				c.selectedTrainingExample = -1;
			}else{
				c.selectedTrainingExample = index;
			}
		}
	}

	/*listener on Jlist for deselecting items*/
	private class DeselectTestExampleListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			JList<?> list = (JList<?>)e.getSource();
			Controller c = (Controller)SwingUtilities.getRoot(list);
			int index = list.locationToIndex(e.getPoint());

			if(c.selectedTestExample == index){
				list.clearSelection();
				c.selectedTestExample = -1;
			}else{
				c.selectedTestExample = index;
			}
		}
	}
}