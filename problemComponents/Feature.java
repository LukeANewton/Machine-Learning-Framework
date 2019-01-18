package problemComponents;

import java.io.Serializable;

/**
 * Interface for simple and composite features for Composite design pattern.
 * 
 * @author Luke Newton
 */
public interface Feature extends Serializable{
	/** return the contents of this feature */
	public Object getContents();
	/*calculate the distaance between this feature and another */
	public double calculateDistance(Feature otherFeature);
}
