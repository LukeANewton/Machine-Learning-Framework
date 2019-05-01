package problemComponents;

import java.io.Serializable;

import simpleFeatureDistanceStrategies.SimpleDistanceStrategy;

/**
 * Interface for simple and composite features for Composite design pattern.
 * 
 * @author Luke Newton
 */
public interface Feature extends Serializable{
	/** return the contents of this feature */
	public Object getContents();
	/*calculate the distaance between this feature and another */
	public double calculateDistance(Feature otherFeature);
	/*update the distance function of a feature*/
	public void setDistanceFunction(SimpleDistanceStrategy distanceFunction, SimpleFeatureType simpleFeatureType);
}
