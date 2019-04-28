/**
 * 
 */
package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class IntegerAbsDistance implements SimpleDistanceStrategy {
	private static final long serialVersionUID = -6432387596184672953L;

	/* (non-Javadoc)
	 * @see simpleDistanceFunctions.SimpleDistanceFunction#calculateDistance(java.lang.Object, java.lang.Object)
	 */
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		return Math.abs((int)obj1 - (int)obj2);
	}

}
