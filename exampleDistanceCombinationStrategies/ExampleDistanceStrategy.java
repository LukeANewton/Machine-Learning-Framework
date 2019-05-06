package exampleDistanceCombinationStrategies;

import java.io.Serializable;

/**
 * @author Luke Newton
 *
 */
public interface ExampleDistanceStrategy extends Serializable {
	/**combines the individual feature distances of each data point into one value*/
	double[] combineDistances(Double[][] distances, int unknownIndex);
}
