/**
 * 
 */
package exampleDistanceCombinationStrategies;

/**
 * @author Luke Newton
 *
 */
public class ManhattanDistance implements ExampleDistanceStrategy {

	/* (non-Javadoc)
	 * @see exampleDistanceCombinationFunctions.ExampleDistanceStrategy#combineDistances(java.lang.Double[][], int)
	 */
	@Override
	public double[] combineDistances(Double[][] distances, int unknownIndex) {
		double[] pointDistances = new double[distances.length];
		//sum all distances for one point by Manhattan distance (sum of absolute distances)
		for(int pointNum = 0; pointNum < distances.length; pointNum++){
			//all distances start at one to avoid division by zero later on
			pointDistances[pointNum] = 1;
			for(int attributeNum = 0; attributeNum < distances[0].length; attributeNum++){
				if(distances[pointNum][attributeNum] == null || attributeNum == unknownIndex)
					continue;
				
				pointDistances[pointNum] += Math.abs(distances[pointNum][attributeNum]);
			}
		}
		return pointDistances;
	}

}
