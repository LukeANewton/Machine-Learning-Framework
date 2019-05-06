package exampleDistanceCombinationStrategies;

/**
 * @author Luke Newton
 *
 */
public class NumberOfSimilarFeatures implements ExampleDistanceStrategy {
	private static final long serialVersionUID = 31380462582020671L;

	/**combines the individual feature distances of each data point into one value*/
	@Override
	public double[] combineDistances(Double[][] distances, int unknownIndex) {
		double[] pointDistances = new double[distances.length];
		
		//get a distance for a point by based on the number of features in the training point that are equal to those in the test point
		//(more zero distances results in a lower point distance)
		for(int pointNum = 0; pointNum < distances.length; pointNum++){
			pointDistances[pointNum] = distances.length + 1;
			for(int attributeNum = 0; attributeNum < distances[0].length; attributeNum++){
				if(distances[pointNum][attributeNum] == null || attributeNum == unknownIndex)
					continue;
				
				if(distances[pointNum][attributeNum] == 0 && pointDistances[pointNum] > 0)
					pointDistances[pointNum]--;
			}
			//while the training example may be identical to a test, a distance of zero means
			// a divide by zero later, so the closest distance we can get is only close to zero
			if(pointDistances[pointNum] == 0) pointDistances[pointNum] = 1;
		}
		return pointDistances;
	}

}
