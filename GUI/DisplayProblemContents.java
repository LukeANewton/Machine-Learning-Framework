package GUI;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
public class DisplayProblemContents extends JPanel {
	private static final long serialVersionUID = -5170428175418141225L;
	//current problem set working with
	private Problem problem;
	//list to display training examples
	private JList<ArrayList<Feature>> trainingExamples;
	//list to display test examples
	private JList<ArrayList<Feature>> testExamples;
	//the JFrame this will be displayed in
	private MachineLearningFramework mainWindow;

	/**
	 * Constructor
	 * 
	 * @param problem problem set currently working with
	 */
	public DisplayProblemContents(MachineLearningFramework m){
		super();
		problem = m.getProblem();
		mainWindow = m;
		mainWindow.setSelectedTrainingExample(-1);
		mainWindow.setSelectedTestExample(-1);
		mainWindow.setMenuBarEnabled(true);
		createContent();
	}

	/**create contents to display problem*/
	private void createContent(){
		removeAll();
		setLayout(new GridLayout(2, 1));

		//create JList for training examples
		DefaultListModel<ArrayList<Feature>> features = new DefaultListModel<>();
		int numTrainingExamples =  problem.getNumberOfTrainingExamples();
		for(int i = 0; i < numTrainingExamples; i++)
			features.addElement(problem.getTrainingExample(i).getFields());
		trainingExamples = new JList<ArrayList<Feature>>(features);
		trainingExamples.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		trainingExamples.addMouseListener(new DeselectTrainingExampleListener());	
		
		//create JList for test examples
		features = new DefaultListModel<>();
		int numberTestExamples = problem.getNumberOfTestExamples();
		for(int i = 0; i < numberTestExamples; i++)
			features.addElement(problem.getTestExample(i).getFields());
		testExamples = new JList<ArrayList<Feature>>(features);
		testExamples.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		testExamples.addMouseListener(new DeselectTestExampleListener());

		//add JLists to panel
		add(buildScrollableExampleList(trainingExamples, "Training Examples"));
		add(buildScrollableExampleList(testExamples, "Test Examples"));
	}
	
	/**takes a JList and wraps it in a scrollable panel with raised border*/
	private JScrollPane buildScrollableExampleList(JList<ArrayList<Feature>> list, String title){
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), 
						title, TitledBorder.LEFT, TitledBorder.TOP)));
		scrollPane.setViewportView(list);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		return scrollPane;
	}

	/**
	 * update problem set and JLists. Used when importing a new problem set
	 * 
	 * @param problem new problem set to watch with JLists
	 */
	protected void updateProblem(Problem problem){
		this.problem = problem;

		DefaultListModel<ArrayList<Feature>> features = new DefaultListModel<>();
		int numberTrainingExamples = problem.getNumberOfTrainingExamples();
		for(int i = 0; i < numberTrainingExamples; i++)
			features.addElement(problem.getTrainingExample(i).getFields());
		trainingExamples.setModel(features);

		features = new DefaultListModel<>();
		int numTestExamples = problem.getNumberOfTestExamples();
		for(int i = 0; i < numTestExamples; i++)
			features.addElement(problem.getTestExample(i).getFields());
		testExamples.setModel(features);
	}

	/*listener on Jlist for selecting/deselecting items*/
	private class DeselectTrainingExampleListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			JList<?> list = (JList<?>)e.getSource();
			MachineLearningFramework m = (MachineLearningFramework)SwingUtilities.getRoot(list);
			int index = list.locationToIndex(e.getPoint());

			if(m.getSelectedTrainingExample() == index){
				list.clearSelection();
				m.setSelectedTrainingExample(-1);
			}else{
				m.setSelectedTrainingExample(index);
			}
		}
	}

	/*listener on Jlist for selecting/deselecting items*/
	private class DeselectTestExampleListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			JList<?> list = (JList<?>)e.getSource();
			MachineLearningFramework m = (MachineLearningFramework)SwingUtilities.getRoot(list);
			int index = list.locationToIndex(e.getPoint());

			if(m.getSelectedTestExample() == index){
				list.clearSelection();
				m.setSelectedTestExample(-1);
			}else{
				m.setSelectedTestExample(index);
			}
		}
	}
}