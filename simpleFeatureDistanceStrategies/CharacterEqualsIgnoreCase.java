package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class CharacterEqualsIgnoreCase implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 875718718876069346L;

	/**compare two features to find distance between them*/
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		if (Character.toLowerCase((char)obj1) == Character.toLowerCase((char)obj2))
			return 0;
		return 1;
	}
}
