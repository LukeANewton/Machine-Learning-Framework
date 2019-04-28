/**
 * 
 */
package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class CharacterEquals implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 3877755064217337031L;

	/* (non-Javadoc)
	 * @see simpleDistanceFunctions.SimpleDistanceFunction#calculateDistance(java.lang.Object, java.lang.Object)
	 */
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		if(((char) obj1) == (char) obj2) 
			return 0;
		return 1;
	}
}
