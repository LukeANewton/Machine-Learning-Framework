package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class CharacterAbsDistanceIgnoreCase implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 1705185187097298053L;

	/**compare two features to find distance between them*/
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		return Math.abs(Character.toLowerCase((char)obj1)
				- Character.toLowerCase((char)obj2));
	}
}
