/**
 * 
 */
package compositeFeatureDistanceStrategies;

import java.io.Serializable;

import problemComponents.CompositeFeature;

/**
 * @author Luke Newton
 *
 */
public interface CompositeDistanceStrategy extends Serializable{
	public double calculateDistance(CompositeFeature feature1, CompositeFeature feature2);
}
