/**
 * 
 */
package exampleDistanceCombinationStrategies;

/**
 * @author Luke Newton
 *
 */
public class NumberOfSimilarFeatures implements ExampleDistanceStrategy {

	/* (non-Javadoc)
	 * @see exampleDistanceCombinationFunctions.ExampleDistanceStrategy#combineDistances(java.lang.Double[][], int)
	 */
	@Override
	public double[] combineDistances(Double[][] distances, int unknownIndex) {
		double[] pointDistances = new double[distances.length];
		
		//get a distance for a point by based on the number of features in the training point that are equal to those in the test point
		//(more zero distances results in a lower point distance)
		for(int pointNum = 0; pointNum < distances.length; pointNum++){
			pointDistances[pointNum] = distances.length;
			for(int attributeNum = 0; attributeNum < distances[0].length; attributeNum++){
				if(distances[pointNum][attributeNum] == null || attributeNum == unknownIndex)
					continue;
				
				if(distances[pointNum][attributeNum] == 0 && pointDistances[pointNum] > 0)
					pointDistances[pointNum]--;
			}
			//while the training example may be identical to a test, a distance of zero means
			// a divide by zero later, so the closest distance we can get is only close to zero
			if(pointDistances[pointNum] == 0) pointDistances[pointNum] = 0.1;
		}
		return pointDistances;
	}

}
