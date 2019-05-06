package simpleFeatureDistanceStrategies;

/**
 * @author Luke Newton
 *
 */
public class StringSimilarityIgnoreCase implements SimpleDistanceStrategy {
	private static final long serialVersionUID = 4552245911865636723L;

	/**compare two features to find distance between them*/
	@Override
	public double calculateDistance(Object obj1, Object obj2) {
		String str1 = ((String)obj1).toLowerCase();
		String str2 = ((String)obj2).toLowerCase();
		
		int distance = 0;
		int maxSize =  Math.min(str1.length(), str2.length());
		distance += Math.max(str1.length(), str2.length()) - maxSize; //add num extra chars
		
		for (int i = 0; i < maxSize; i++) { //compare each char of each string at same indices
			if (str1.charAt(i) != str2.charAt(i)) distance++;
		}
		
		if (str1.length() < str2.length()) distance *= -1; //if string 2 is longer, return a negative
		
		return distance;
	}
}
