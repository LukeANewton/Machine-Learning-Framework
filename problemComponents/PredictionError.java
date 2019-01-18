package problemComponents;

import java.io.Serializable;

/**
 * An Object to handle the accuracy of predictions made against the known value that the program tries to predict.
 * Contains a constructor, getter for the accuracy value, and means to update the accuracy with a new pair of
 * prediction/known values. 
 * 
 * @author luke newton
 */
public class PredictionError implements Serializable{
	private static final long serialVersionUID = 4313676483047829378L;
	//the average accuracy of predictions
	private double accuracy;
	//number of predictions made
	private int numberOfPredictions;
	
	/**Constructor*/
	public PredictionError(){
		accuracy = 0;
		numberOfPredictions = 0;
	}

	/**returns the accuracy of the predictions we have been making so far*/
	public double getAccuracy() {
		return accuracy;
	}
	
	/**
	 * updates the accuracy value this object contains
	 * NOTE: Since this should only be used for computer generated predictions and user defined predictions,
	 * it should only be passed two Objects of the same type. There are no checks for this, so if it is used incorrectly
	 * it is likely that the program will crash due incompatible classes compared with equals().
	 * 
	 * @param programPrediction the output value predicted by the call to Prediction.getPrediction()
	 * @param knownPrediction the known output of the test example, what we are trying to predict
	 * @return the updated accuracy of our prediction model
	 */
	public double updateAccuracy(Object programPrediction, Object knownPrediction){
		/*update an average - generic algorithm*/
		//multiply by the number of predictions to get sum of individual equalities
		accuracy *= numberOfPredictions;
		//update sum of equalities - if they are equal, accuracy of THIS prediction is 1, add to sum
		if(programPrediction.equals(knownPrediction))
			accuracy++;
		//update number of predictions
		numberOfPredictions++;
		//re-divide by updated number of predictions
		accuracy /= numberOfPredictions;
		//Return new accuracy average
		return accuracy;
	}
}
