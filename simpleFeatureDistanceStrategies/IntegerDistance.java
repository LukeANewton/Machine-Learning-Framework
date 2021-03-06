package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class IntegerDistance implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 1840397222210500678L;

	/**compare two features to find distance between them*/
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		return (int)obj1 - (int)obj2;
	}
}
