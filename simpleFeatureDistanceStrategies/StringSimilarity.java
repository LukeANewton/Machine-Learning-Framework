/**
 * 
 */
package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class StringSimilarity implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 2725121923889622758L;

	/* (non-Javadoc)
	 * @see simpleDistanceFunctions.SimpleDistanceFunction#calculateDistance(java.lang.Object, java.lang.Object)
	 */
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		String str1 = (String)obj1;
		String str2 = (String)obj2;
		
		int distance = 0;
		int maxSize =  Math.min(str1.length(), str2.length());
		distance += Math.max(str1.length(), str2.length()) - maxSize; //add extra chars
		
		for (int i = 0; i < maxSize; i++) { //compare each char of each string at same indices
			if (str1.charAt(i) != str2.charAt(i)) 
				distance++;
		}
		
		if (str1.length() < str2.length()) 
			distance *= -1; //if string 2 is longer, return a negative
		
		return distance;
	}

}
