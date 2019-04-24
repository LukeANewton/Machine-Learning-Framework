package problemComponents;

import simpleFeatureDistanceStrategies.BooleanDistance;
import simpleFeatureDistanceStrategies.CharacterEquals;
import simpleFeatureDistanceStrategies.DoubleDistance;
import simpleFeatureDistanceStrategies.IntegerDistance;
import simpleFeatureDistanceStrategies.SimpleDistanceStrategy;
import simpleFeatureDistanceStrategies.StringEquals;

/**
 * Represents the most basic type of field: A whole number, floating point, String, 
 * character, or boolean. Any type of feature can be built from a combination of these. 
 * 
 * A simple feature also contains the strategy used to compare two features of like composition.
 * 
 * The class contains getters and setters for these two fields, as well as a method for parsing 
 * a string to the appropriate SimpleFeature type.
 * 
 * @author Luke Newton
 */
public class SimpleFeature implements Feature {
	//autogenerated serialization ID for saving object to a file
	private static final long serialVersionUID = -3360092969424577352L;
	//the field value assocated with feature
	private Object contents;
	//the strategy used to compare to other features
	private SimpleDistanceStrategy distanceFunction;
	
	/**
	 * Constructor
	 * 
	 * @param feature the contents (value) of the feature will contain
	 * @param function the strategy used to compare two features of like content type
	 */
	public SimpleFeature(Object feature, SimpleDistanceStrategy function){
		contents = feature;
		distanceFunction = function;
	}
	
	/**
	 * Constructor. Gives default strategy based on feature type
	 * 
	 * @param feature the contents (value) of the feature will contain
	 */
	public SimpleFeature(Object feature){
		SimpleFeature x = parseSimpleFeature(feature.toString());
		contents = x.getContents();
		distanceFunction = x.getDistanceFunction();
	}
	
	/* (non-Javadoc)
	 * @see problemComponents.Feature#calculateDistance(java.lang.Object)
	 */
	@Override
	public double calculateDistance(Feature otherFeature) {
		return distanceFunction.calculateDistance(this.contents, otherFeature.getContents());
	}
	
	@Override
	public String toString() {
		return contents.toString();
	}

	@Override
	public boolean equals(Object o){
		return contents.equals(((Feature) o).getContents());
	}

	/* (non-Javadoc)
	 * @see problemComponents.Feature#getContents()
	 */
	@Override
	public Object getContents() {
		return contents;
	}

	/** set contents of this feature to a new value */
	public void setContents(Object contents) {
		this.contents = contents;
	}

	/** return the strategy used to compare two features of this contents type */
	public SimpleDistanceStrategy getDistanceFunction() {
		return distanceFunction;
	}

	/** update the strategy used to compare two features of this contents type */
	public void setDistanceFunction(SimpleDistanceStrategy distanceFunction) {
		this.distanceFunction = distanceFunction;
	}
	
	/**
	 * convert a string value into an appropriate basic field type.
	 *Basic field types are: integer, double, character, string, boolean
	 * 
	 * @param s the input to convert into a SimpleFeature
	 * @return the type of feature contained within the input string. 
	 * Includes value and distance function that works with value
	 */
	public static SimpleFeature parseSimpleFeature(String s){
		if(s.equals("")){
			return null;
		}

		//first try to make a double
		try{
			//if this works, we have either a double or integer
			double n = Double.parseDouble(s);
			//check if its an integer
			if(!s.contains(".")){
				//if there is no decimal, we have an integer
				return new SimpleFeature((int) n, new IntegerDistance());
			}
			//by now we know we have a double
			return new SimpleFeature(n, new DoubleDistance());
		} catch (NumberFormatException e) {
			//System.out.println("not an int or double: " + s);
		}
		//by now, we know its not any kind of number

		//if string is one character long and not a number, its a character
		if(s.length() == 1){
			return new SimpleFeature(s.charAt(0), new CharacterEquals());
		}

		//if string says 'true' or 'false', its a boolean
		if(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")){
			return new SimpleFeature(Boolean.parseBoolean(s), new BooleanDistance());
		}

		//otherwise its a string
		return new SimpleFeature(s, new StringEquals());
	}
	
}