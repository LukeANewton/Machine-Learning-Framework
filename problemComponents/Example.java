package problemComponents;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * abstract class to pool the similarities between TestExamples and TrainingExamples.
 * 
 * Both have a collection of features to store the data point's contents, and methods to 
 * access those fields and size of the collection.
 * 
 * @author Luke Newton
 */
public abstract class Example implements Serializable{
	////autogenerated serialization ID for saving object to a file
	private static final long serialVersionUID = 5541151375965143044L;
	//collection of features representing the fields of this data point
	protected ArrayList<Feature> fields;
	
	/** returns the collection of fields for this data point */
	public ArrayList<Feature> getFields(){
		return fields;
	}
	
	/** returns the feature at specified index i within this data point */
	public Feature getField(int i){
		return fields.get(i);
	}
	
	/* returns the number of fields within this data point */
	public int getNumberOfFields(){
		return fields.size();
	}
	
	/*update the fields within this data point to a new collection */
	public void setFields(ArrayList<Feature> newFields){
		fields = newFields;
	}
}