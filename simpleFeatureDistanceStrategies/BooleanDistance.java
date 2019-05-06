package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class BooleanDistance implements SimpleDistanceStrategy {
	private static final long serialVersionUID = -2962929463266474607L;

	/**compare two features to find distance between them*/
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		if((boolean) obj1 == (boolean) obj2) 
			return 0;
		return 1;
	}
}
