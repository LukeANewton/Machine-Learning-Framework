package Control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.*;

import problemComponents.Problem;
import problemComponents.TestExample;

/**
 * Method for predicting the value of a field in an unknown point, based off a number of training examples. 
 * 
 * @author luke newton
 */
public class Prediction {	
	/**
	 * approximate a value for a field in an unknown point based off a series of training examples,
	 * and use of a kNN algorithm
	 * 
	 * @param k specifies how many nearest neighbors to return, generally more neighbors yields more accurate results
	 * @param problem the problem set with all training examples, weights, strategies, and test examples to predict a point attribute
	 * @param indexOfPointToPredict the index value of the test example in problem we are predicting
	 * @return an approximation of the missing value in the unknown point
	 */
	public static Object getPrediction(int k, Problem problem, int indexOfPointToPredict){
		//check if the test example exists in the training example set
		TestExample pointToPredict = problem.getTestExample(indexOfPointToPredict);
		for(int i = 0; i < problem.getNumberOfTrainingExamples(); i++){
			if(pointToPredict.equals(problem.getTrainingExample(i).toTestExample())){
				return problem.getTrainingExample(i).getField(problem.getNumberOfFields() - 1).getContents();
			}
		}	
		//index of the value to estimate, the last field in a point
		int unknownIndex = problem.getTestExample(indexOfPointToPredict).getUnknownFeaturePosition();

		//obtain the values from training examples which correspond to the unknown value in the unknown point, with distances
		ArrayList<DistanceContentPair> distanceContentPairs = kNN.getNearestNeighbors(k, problem, indexOfPointToPredict);

		//a sample field value from a training example which corresponds to field we are approximating
		//in the unknown point, to know what method to use to approximate value
		Object unknownAttribute = problem.getTrainingExample(0).getField(unknownIndex).getContents();

		//if the prediction is a number value, use a weighted average
		//----------------------------------------------------------------------------
		//a sum of the k nearest neighbors, where each neighbor value is multiplied by
		// 1/distance. Therefore, a training example's value has an influence on the
		//outcome which is inversely proportional to how similar it is to the point
		if(unknownAttribute instanceof Integer){
			double numPrediction = 0;
			for(int pointNum = 0; pointNum < k; pointNum++){
				numPrediction +=  (int)((int)distanceContentPairs.get(pointNum).getContent()
						/ distanceContentPairs.get(pointNum).getDistance());
			}
			return (int)numPrediction / k;
		} else if(unknownAttribute instanceof Double){
			double numPrediction = 0;
			for(int pointNum = 0; pointNum < k; pointNum++){
				numPrediction +=  ((double)distanceContentPairs.get(pointNum).getContent())
						/ distanceContentPairs.get(pointNum).getDistance();
			}
			return numPrediction / k;
		//if we are estimating a boolean, character, or string, we use a weighted vote/histogram approach
		//--------------------------------------------------------------------------------------------
		//we use 1/distance as the number of 'occurrences' of a string/character/boolean. After all
		//k nearest neighbors have had a 'vote'(their 1/distance added to the number of occurrences),
		//the string/character/boolean with the greatest occurrences is the predicted value
		} else if(unknownAttribute instanceof Boolean || unknownAttribute instanceof Character 
				|| unknownAttribute instanceof String){
			return getMaxOccurences(k, distanceContentPairs);
		}
		return null;
	}
	
	private static Object getMaxOccurences(int k, ArrayList<DistanceContentPair> distanceContentPairs){
		//build histogram
		HashMap<Object, Double> histogram = new HashMap<>();
		for(int pointNum = 0; pointNum < k; pointNum++){
			Object currentKey = distanceContentPairs.get(pointNum).getContent();
			double currentDistance = distanceContentPairs.get(pointNum).getDistance();
			if(histogram.containsKey(currentKey)){
				histogram.put(currentKey, histogram.get(currentKey) + (1/currentDistance));
			} else{
				histogram.put(currentKey, (1/currentDistance));
			}
		}
		//find the key with highest value in histogram and return that
		Iterator<Entry<Object, Double>> I = histogram.entrySet().iterator(); 
		Object prediction = distanceContentPairs.get(0).getContent();
		double maxOccurrences = 0;
		while(I.hasNext()){
			Entry<Object, Double> pair = I.next();
			double currentOccurrences = (double)pair.getValue();
			if(maxOccurrences < currentOccurrences){
				maxOccurrences = currentOccurrences;
				prediction = pair.getKey();
			}
		}
		return prediction;
	}
}