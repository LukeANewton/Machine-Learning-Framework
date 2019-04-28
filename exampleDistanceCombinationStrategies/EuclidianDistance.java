/**
 * 
 */
package exampleDistanceCombinationStrategies;

/**
 * @author Luke Newton
 *
 */
public class EuclidianDistance implements ExampleDistanceStrategy {
	private static final long serialVersionUID = -6300516453575014588L;

	/* (non-Javadoc)
	 * @see exampleDistanceCombinationFunctions.ExampleDistanceStrategy#combineDistances(java.lang.Double[][], int)
	 */
	@Override
	public double[] combineDistances(Double[][] distances, int unknownIndex) {
		double[] pointDistances = new double[distances.length];
		
		//sum all distances for one point by Euclidian distance (sum of squared distances, square rooted)
		for(int pointNum = 0; pointNum < distances.length; pointNum++){
			//all distances start at one to avoid division by zero later on
			pointDistances[pointNum] = 1;
			for(int attributeNum = 0; attributeNum < distances[0].length; attributeNum++){
				if(distances[pointNum][attributeNum] == null || attributeNum == unknownIndex)
					continue;
				
				pointDistances[pointNum] += Math.pow(distances[pointNum][attributeNum], 2);
			}
			pointDistances[pointNum] = Math.sqrt(pointDistances[pointNum]);
	
			//while the training example may be identical to a test, a distance of zero means
			// a divide by zero later, so the closest distance we can get is only close to zero
			if(pointDistances[pointNum] == 0) pointDistances[pointNum] = 0.1;
		}
		return pointDistances;
	}

}
