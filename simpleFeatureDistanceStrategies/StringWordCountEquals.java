/**
 * 
 */
package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class StringWordCountEquals implements SimpleDistanceStrategy {
	/* (non-Javadoc)
	 * @see simpleDistanceFunctions.SimpleDistanceFunction#calculateDistance(java.lang.Object, java.lang.Object)
	 */
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		String str1 = (String)obj1;
		String str2 = (String)obj2;
		
		if(str1.split(" ").length == str2.split(" ").length)
			return 0;
		return 1;
	}

}
