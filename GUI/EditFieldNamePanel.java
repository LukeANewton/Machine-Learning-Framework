package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A panel to edit the field names of a problem set
 * 
 * @author Luke Newton
 *
 */
public class EditFieldNamePanel extends UserInputContentPanel {
	private static final long serialVersionUID = 6843313390770519891L;

	/**Constructor*/
	public EditFieldNamePanel(MachineLearningFramework m) {
		super(m);
		createContent(m.getProblem().getNumberOfFields());
	}

	/**
	 * create and add the required content of this container
	 * 
	 * @param n the number of attribute/features in a training/test example
	 */
	private void createContent(int n){
		ArrayList<String> panelTitles = new ArrayList<>();
		for(int i = 0; i < n; i++)
			panelTitles.add("Enter name for field " + (i + 1) +": ");

		super.createContent(n, panelTitles);

		int numPanels = panels.size();
		for(int i = 0; i < numPanels; i++){
			String name = problem.getFieldName(i);

			if(name != null)
				panels.get(i).setTextFieldText(name);
		}
		doneButton.addActionListener(new AcceptNamesListener());
	}

	/*action listener for applying new weights to problem*/
	private class AcceptNamesListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ArrayList<String> names = new ArrayList<>();

			for(int i = 0; i < panels.size(); i++)
				names.add(panels.get(i).getInput());

			problem.setFieldNames(names);
			returnToDisplayScreen(e);
		}
	}

}
