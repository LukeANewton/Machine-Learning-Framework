/**
 * 
 */
package simpleFeatureDistanceStrategies;

import java.io.Serializable;

/**
 * @author Luke Newton
 *
 */
public interface  SimpleDistanceStrategy extends Serializable {
	public double calculateDistance(Object obj1, Object obj2);
}
