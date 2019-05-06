package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class DoubleDistance implements SimpleDistanceStrategy {
	private static final long serialVersionUID = -5451817936257094247L;

	/**compare two features to find distance between them*/
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		return (double)obj1 - (double)obj2;
	}
}
