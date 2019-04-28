/**
 * 
 */
package compositeFeatureDistanceStrategies;

import problemComponents.CompositeFeature;

/**
 * @author Luke Newton
 *
 */
public class CompositeEquivalence implements CompositeDistanceStrategy {
	private static final long serialVersionUID = 4817886592858489018L;

	/* (non-Javadoc)
	 * @see compositeDistanceFunctions.CompositeDistanceFunction#calculateDistance(problemComponents.CompositeFeature, problemComponents.CompositeFeature)
	 */
	@Override
	public double calculateDistance(CompositeFeature feature1, CompositeFeature feature2) {
		if(feature1.equals(feature2))
			return 0;
		return 1;
	}

}
