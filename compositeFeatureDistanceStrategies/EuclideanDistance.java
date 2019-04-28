/**
 * 
 */
package compositeFeatureDistanceStrategies;

import java.util.ArrayList;

import problemComponents.CompositeFeature;
import problemComponents.Feature;

/**
 * @author Luke Newton
 *
 */
public class EuclideanDistance implements CompositeDistanceStrategy {
	private static final long serialVersionUID = -9017616718850870403L;

	/* (non-Javadoc)
	 * @see compositeDistanceFunctions.CompositeDistanceFunction#calculateDistance(problemComponents.CompositeFeature, problemComponents.CompositeFeature)
	 */
	@Override
	public double calculateDistance(CompositeFeature feature1, CompositeFeature feature2) {
		double distance = 0;
		ArrayList<Feature> features1 = feature1.getContents();
		ArrayList<Feature> features2 = feature2.getContents();
		int maxSize =  Math.min(features1.size(), features2.size());
		
		for(int i = 0; i < maxSize; i++){
			distance += Math.pow(features1.get(i).calculateDistance(features2.get(i)), 2);
		}
		
		return Math.sqrt(distance);
	}

}
