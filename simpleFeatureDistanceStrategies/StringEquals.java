package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class StringEquals implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 2807014714344619100L;

	/**compare two features to find distance between them*/
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		if(((String) obj1).equals(obj2))
			return 0;
		return 1;
	}
}
