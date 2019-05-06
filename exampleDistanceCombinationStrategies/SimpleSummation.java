package exampleDistanceCombinationStrategies;

/**
 * @author Luke Newton
 *
 */
public class SimpleSummation implements ExampleDistanceStrategy {
	private static final long serialVersionUID = -937276725165455857L;

	/**combines the individual feature distances of each data point into one value*/
	@Override
	public double[] combineDistances(Double[][] distances,  int unknownIndex) {
		double[] pointDistances = new double[distances.length];
		
		for(int pointNum = 0; pointNum < distances.length; pointNum++){
			pointDistances[pointNum] = 0;
			for(int attributeNum = 0; attributeNum < distances[0].length; attributeNum++){
				if(distances[pointNum][attributeNum] == null || attributeNum == unknownIndex)
					continue;
				
				pointDistances[pointNum] += distances[pointNum][attributeNum];
			}
			//all distances start must be non zero to avoid division by zero later
			if(pointDistances[pointNum] == 0) pointDistances[pointNum] = 1;
		}
		return pointDistances;
	}

}
