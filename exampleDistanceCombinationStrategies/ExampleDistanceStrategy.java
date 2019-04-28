/**
 * 
 */
package exampleDistanceCombinationStrategies;

import java.io.Serializable;

/**
 * @author Luke Newton
 *
 */
public interface ExampleDistanceStrategy extends Serializable {
	double[] combineDistances(Double[][] distances, int unknownIndex);
}
