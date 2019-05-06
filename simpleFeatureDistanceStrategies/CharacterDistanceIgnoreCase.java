package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class CharacterDistanceIgnoreCase implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 849913155122575169L;

	/**compare two features to find distance between them*/
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		return Character.toLowerCase((char)obj1) - Character.toLowerCase((char)obj2);
	}
}
