package Control;

import problemComponents.Problem;

/**
 * A class to encapsulate a configured problem with the number of nearest neighbors to use with prediction
 * 
 * @author Luke Newton
 */
public class ProblemConfiguration {
	//the problem set to use
	private Problem problem;
	//the number of nearest neighbors to use when predicting for the problem set
	private int k;
	
	/**Constructor */
	public ProblemConfiguration(Problem problem, int k) {
		this.problem = problem;
		this.k = k;
	}

	/**returns the problem set in the object*/
	public Problem getProblem() {
		return problem;
	}

	/**returns the number of nearest neighbors to use stored in the object*/
	public int getK() {
		return k;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public void setK(int k) {
		this.k = k;
	}
}
