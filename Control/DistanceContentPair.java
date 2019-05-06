package Control;
/**
 * An object containing the value of an attribute in a known point, and how far that value 
 * is from the value in the unknown point. For use in getting k-nearest-neighbors
 * 
 * @author luke newton
 */
public class DistanceContentPair {
	//how far content value is from the unknown content value
	private double distance;
	//the content value of come known point
	private Object content;
	
	/**Constructor */
	public DistanceContentPair(Object content, double distance){
		this.content = content;
		this.distance = distance;
	}

	/** returns the distance in the pair*/
	public double getDistance() {
		return distance;
	}

	/**alter the distance in the pair*/
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**returns the attribute value in this pair*/
	public Object getContent() {
		return content;
	}

	/**alter the attribute value in this pair*/
	public void setContent(Object content) {
		this.content = content;
	}
	
	/**toString override*/
	public String toString(){
		return "Distance: " + distance + "   Content: " + content.toString();
	}
	
}
