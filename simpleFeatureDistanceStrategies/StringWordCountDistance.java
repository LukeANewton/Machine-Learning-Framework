package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class StringWordCountDistance implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 5080027213537895775L;

	/**compare two features to find distance between them*/
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		return Math.abs(((String)obj1).split(" ").length - 
				((String)obj1).split(" ").length);
	}
}
