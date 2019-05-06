package simpleFeatureDistanceStrategies;

import java.io.Serializable;

/**
 * @author Luke Newton
 *
 */
public interface  SimpleDistanceStrategy extends Serializable {
	/**compare two features to find distance between them*/
	public double calculateDistance(Object obj1, Object obj2);
}
