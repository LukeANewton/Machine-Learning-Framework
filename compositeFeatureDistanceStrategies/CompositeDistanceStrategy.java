/**
 * 
 */
package compositeFeatureDistanceStrategies;

import problemComponents.CompositeFeature;

/**
 * @author Luke Newton
 *
 */
public interface CompositeDistanceStrategy {
	public double calculateDistance(CompositeFeature feature1, CompositeFeature feature2);
}
