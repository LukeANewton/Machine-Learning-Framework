package Control;

import java.util.ArrayList;

import exampleDistanceCombinationStrategies.ExampleDistanceStrategy;
import problemComponents.Feature;
import problemComponents.Problem;
import problemComponents.TestExample;
import problemComponents.TrainingExample;

/**
 * Class containing methods for obtaining the k nearest neighbors of a point.
 * Contains methods which calculate distances between points in a known set and unknown set,
 * normalizing distances, sorting points based on distance, and getting a subset of points
 * containing only the k closest points to an unknown point
 * 
 * @author cameron rushton, madelyn krasnay, luke newton
 */
public class kNN {
	/**
	 * 
	 * @param k the number of closest neighbouring points to return
	 * @param problem the object containing all the components of the current data set
	 * @param indexOfPointToPredict the index value of the point in problem's test examples we are predicting
	 * @return 
	 */
	public static ArrayList<DistanceContentPair> getNearestNeighbors(int k, Problem problem, int indexOfPointToPredict){
		int numTrainingExamples = problem.getNumberOfTrainingExamples();
		int unknownIndex = problem.getUnknownTestFieldPosition();
		double[] weights = normalizeWeights(problem.getWeights());
		
		//get weighted point distances
		double[] pointDistances = getExampleDistances(weights, problem.getTrainingExamples(), problem.getTestExample(indexOfPointToPredict), problem.getExampleCombinationStrategy());
		
		//this next section gets the k closest points by sorting field values based on distances
		ArrayList<DistanceContentPair> distanceContentPairs = new ArrayList<>();
		for(int pointNum = 0; pointNum < numTrainingExamples; pointNum++){
			//build a list of point distances paired with the output value from that point
			distanceContentPairs.add(new DistanceContentPair(
					problem.getTrainingExample(pointNum).getField(unknownIndex).getContents() , pointDistances[pointNum]));
		}
		//sort the output-distance pairs from smallest distance to largest
		sortPairs(distanceContentPairs);
		//return only the first k entries in the sorted list for k closest outputs
		return new ArrayList<>(distanceContentPairs.subList(0, k));
	}
	
	/**
	 * converts all weights input by the user into normalized values that are relative to
	 * the largest weight specified.
	 * 
	 * @param weights user defined weightings for calculating an accurate prediction
	 * @return weight values normalized (between 0 and 1) in a double[]
	 */
	private static double[] normalizeWeights(double[] weights){
		double maxWeight= 0;
		//iterate through weights, getting max value to normalize
		for(int i = 0; i < weights.length; i++){
			if (weights[i] > maxWeight)
				maxWeight = weights[i];
		}
		//iterate through weights, normalizing
		for(int i = 0; i < weights.length; i++){
			weights[i] = weights[i] / maxWeight ;		
		}
		//return normalized weights
		return weights;
	}
	
	/**
	 * 
	 * @param examples DataSet containing the training examples use for the prediction
	 * @param targ DataSet containing the unknown example we are predicting a value for
	 * @param indexOfPointToPredict the index value of the point in targ we are predicting
	 * @param pointDistanceFunction specifies which distance function to use in calculating the distance of a point
	 * @return double array where each element is the distance of a point in examples to the point in targ
	 */
	private static double[] getExampleDistances(double[] weights, ArrayList<TrainingExample> examples, TestExample targ, ExampleDistanceStrategy exampleDistanceStrategy){
		//number of training examples
		int numPoints = examples.size();
		//number of fields in each training example
		int numAttribs = examples.get(0).getNumberOfFields();
		//index of the unknown value we are predicting
		int unknownIndex = targ.getUnknownFeaturePosition();

		//find normalized distances between unknown example and known examples
		Double[][] distances = getNormalizedDistances(examples, targ);

		//apply weightings to the distances
		for(int pointNum = 0; pointNum < numPoints; pointNum++){
			for(int attributeNum = 0; attributeNum < numAttribs; attributeNum++){
				if(distances[pointNum][attributeNum] == null) continue;
				
				distances[pointNum][attributeNum] *= weights[attributeNum];
			}
		}
		//calculate distance for a point based on problem strategy
		return exampleDistanceStrategy.combineDistances(distances, unknownIndex);
	}
	
	/**
	 * get all distances from an unknown point in 'testExampleToPredict' to all known points contained
	 * in 'examples'.
	 * 
	 * @param examples DataSet object containing all training examples.
	 * @param testExampleToPredict the test example we want to predict a value for
	 * @param indexOfPointToPredict the index value of the point in targ we are predicting
	 * @return a 2D grid of doubles, where the value at [i][j] corresponds to how far that value 
	 * is from the corresponding value in the unknown point, normalized
	 */
	private static Double[][] getNormalizedDistances(ArrayList<TrainingExample> examples, TestExample testExampleToPredict){
		//number of training examples
		int numPoints = examples.size();
		//number of fields in each training example
		int numAttribs = examples.get(0).getNumberOfFields();
		//distances to normalize
		Double[][] distances = getDistances(examples, testExampleToPredict);
		//divisors to eventually normalize distances with 
		double[] farthestDistances = new double[numAttribs];

		//1) GET DIVISORS TO NORMALIZE DISTANCES (largest distance for each field)
		//initialize divisors
		for(int i = 0; i < numAttribs; i++){
			farthestDistances[i] = 1;
		}
		//iterate through every distance, getting largest distances
		for(int pointNum = 0; pointNum < numPoints; pointNum++){
			for(int attributeNum = 0; attributeNum < numAttribs; attributeNum++){
				if(distances[pointNum][attributeNum] == null) continue;
				
				if(distances[pointNum][attributeNum] > farthestDistances[attributeNum])
					farthestDistances[attributeNum] = distances[pointNum][attributeNum];
			}
		}
		//2)NORMALIZE DISTANCES WITH DIVISORS OBTAINED PREVIOUSLY
		for (int i = 0; i < distances.length; i++){
			for (int j = 0; j < distances[0].length; j++){
				if(distances[i][j] == null) continue;
				
				distances[i][j] =distances[i][j] / farthestDistances[j];
			}
		}
		//return normalized distances
		return distances;
	}

	/**
	 * selection sort modified to sort an object based off one of its parameters. In this case, sorting objects which
	 * contain a field value and a distance by smallest to largest distance
	 * 
	 * @param distanceContentPairs list of content-distance pairs to sort
	 * @return returns the input distanceContentPairs list sorted from smallest to largest distance
	 */
	private static ArrayList<DistanceContentPair> sortPairs(ArrayList<DistanceContentPair> distanceContentPairs){
		//the object with the smallest distance in each iteration
		DistanceContentPair min;
		//the index value of the object with the smallest distance in each iteration
		int minIndex;

		for(int pointNum = 0; pointNum < distanceContentPairs.size(); pointNum++){
			//initialize minimum to be current element
			min = distanceContentPairs.get(pointNum);
			minIndex = pointNum;

			for(int i = pointNum; i < distanceContentPairs.size(); i++){
				//if we have a new minimum, update list
				if(min.getDistance() > distanceContentPairs.get(i).getDistance()){
					min = distanceContentPairs.get(i);
					minIndex = i;
				}
			}
			//swap min value with current index
			DistanceContentPair temp = distanceContentPairs.get(pointNum);
			distanceContentPairs.set(pointNum, min);
			distanceContentPairs.set(minIndex, temp);
		}
		//return sorted list
		return distanceContentPairs;
	}

	/**
	 * calculate distances between values in training examples and values in unknown point
	 * 
	 * @param examples DataSet containing all training examples
	 * @param targ DataSet containing unknown point
	 * @param indexOfPointToPredict the index value of the point in targ we are predicting
	 * @return a 2D grid of doubles, where the value at [i][j] corresponds to how far that value 
	 * is from the corresponding value in the unknown point
	 */
	private static Double[][] getDistances(ArrayList<TrainingExample> examples, TestExample exampleToPredict){
		//number of training examples
		int numPoints = examples.size();
		//number of fields in each training example
		int numFields =  examples.get(0).getNumberOfFields();
		//2D grid to eventually store distances
		Double[][] distances = new Double[numPoints][numFields];
		//the provided test exmaple, converted to a training exmaple so the field to predict is in the correct location
		TrainingExample convertedExampleToPredict = exampleToPredict.toTrainingExample();

		//iterate through each training example
		for(int pointNum = 0; pointNum < numPoints; pointNum++){
			//iterate through each feature within each training example
			for(int fieldNum = 0; fieldNum < numFields; fieldNum++){
				Feature feature = examples.get(pointNum).getField(fieldNum);
				
				//if contents of this feature is empty(ie. one input feature unknown by user when entered), skip it
				if(feature == null || convertedExampleToPredict.getField(fieldNum) == null){
					distances[pointNum][fieldNum] = null;
				//else, calculate distance using current feature's distance function
				}else{
					
					distances[pointNum][fieldNum] = feature.calculateDistance(convertedExampleToPredict.getField(fieldNum));
				}
			}
		}
		return distances;
	}
}