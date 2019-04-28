/**
 * 
 */
package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class CharacterEqualsIgnoreCase implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 875718718876069346L;

	/* (non-Javadoc)
	 * @see simpleDistanceFunctions.SimpleDistanceFunction#calculateDistance(java.lang.Object, java.lang.Object)
	 */
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		if (Character.toLowerCase((char)obj1) == Character.toLowerCase((char)obj2))
			return 0;
		return 1;
	}

}
