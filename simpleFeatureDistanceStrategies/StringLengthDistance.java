/**
 * 
 */
package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class StringLengthDistance implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 1183291121641963777L;

	/* (non-Javadoc)
	 * @see simpleDistanceFunctions.SimpleDistanceFunction#calculateDistance(java.lang.Object, java.lang.Object)
	 */
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		return Math.abs(obj1.toString().length() - obj2.toString().length());
	}

}
