/**
 * 
 */
package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class BooleanDistance implements SimpleDistanceStrategy {
	private static final long serialVersionUID = -2962929463266474607L;

	/* (non-Javadoc)
	 * @see simpleDistanceFunctions.SimpleDistanceFunction#calculateDistance(java.lang.Object, java.lang.Object)
	 */
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		if((boolean) obj1 == (boolean) obj2) 
			return 0;
		return 1;
	}
}
