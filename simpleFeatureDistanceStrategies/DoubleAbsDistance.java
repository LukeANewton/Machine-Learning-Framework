package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class DoubleAbsDistance implements SimpleDistanceStrategy {
	private static final long serialVersionUID = -1185085211939044019L;

	/**compare two features to find distance between them*/
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		return Math.abs((double)obj1 - (double)obj2);
	}
}
