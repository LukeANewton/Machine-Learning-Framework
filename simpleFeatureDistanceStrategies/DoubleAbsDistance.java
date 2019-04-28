/**
 * 
 */
package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class DoubleAbsDistance implements SimpleDistanceStrategy {
	private static final long serialVersionUID = -1185085211939044019L;

	/* (non-Javadoc)
	 * @see simpleDistanceFunctions.SimpleDistanceFunction#calculateDistance(java.lang.Object, java.lang.Object)
	 */
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		return Math.abs((double)obj1 - (double)obj2);
	}

}
