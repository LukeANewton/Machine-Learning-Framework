package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class CharacterDistance implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 6557758914845621868L;

	/**compare two features to find distance between them*/
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		return (char)obj1 - (char)obj2;
	}
}
