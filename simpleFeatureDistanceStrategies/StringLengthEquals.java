/**
 * 
 */
package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class StringLengthEquals implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 6560550432550112360L;

	/* (non-Javadoc)
	 * @see simpleDistanceFunctions.SimpleDistanceFunction#calculateDistance(java.lang.Object, java.lang.Object)
	 */
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		if (obj1.toString().length() == obj2.toString().length()) 
			return 0;
		return 1;
	}

}
