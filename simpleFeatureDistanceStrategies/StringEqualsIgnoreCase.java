package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class StringEqualsIgnoreCase implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 6356952585139469119L;

	/**compare two features to find distance between them*/
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		if(((String) obj1).equalsIgnoreCase((String) obj2)) 
			return 0;
		return 1;
	}
}
