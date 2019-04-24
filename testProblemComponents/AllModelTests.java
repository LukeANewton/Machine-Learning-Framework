/**
 * 
 */
package testProblemComponents;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Luke Newton
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ 
	CompositeFeatureTest.class, 
	FeatureParseTest.class, 
	ProblemTest.class, 
	SimpleFeatureTest.class 
})

public class AllModelTests {

}
