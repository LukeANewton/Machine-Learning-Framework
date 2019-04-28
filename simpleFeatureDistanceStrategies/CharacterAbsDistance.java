/**
 * 
 */
package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class CharacterAbsDistance implements SimpleDistanceStrategy {
	private static final long serialVersionUID = -4660522593091406211L;

	/* (non-Javadoc)
	 * @see simpleDistanceFunctions.SimpleDistanceFunction#calculateDistance(java.lang.Object, java.lang.Object)
	 */
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		return Math.abs((char)obj1 - (char)obj2);
	}

}
