/**
 * 
 */
package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class StringEqualsIgnoreCase implements SimpleDistanceStrategy {
	/* (non-Javadoc)
	 * @see simpleDistanceFunctions.SimpleDistanceFunction#calculateDistance(java.lang.Object, java.lang.Object)
	 */
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		if(((String) obj1).equalsIgnoreCase((String) obj2)) 
			return 0;
		return 1;
	}

}
