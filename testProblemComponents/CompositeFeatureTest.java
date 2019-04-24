package testProblemComponents;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

import problemComponents.CompositeFeature;
import problemComponents.Feature;
import problemComponents.SimpleFeature;
import simpleFeatureDistanceStrategies.CharacterEquals;
import simpleFeatureDistanceStrategies.IntegerDistance;

/**
 * test cases for composite feature functions. tests for parsing function are in seperate
 * test file called FeatureParseTest
 * 
 * @author luke newton
 * @version 2
 */
public class CompositeFeatureTest {
	/*
	 * ensure size at initialization is zero
	 */
	@Test
	public void testCreationSize() {
		CompositeFeature c = new CompositeFeature();
		assertTrue(c.getContents().size() == 0);
	}
	
	/*
	 * ensure size increments on addition of a simple feature
	 */
	@Test
	public void testSizeOnSimpleAddition() {
		CompositeFeature c = new CompositeFeature();
		c.addFeature(SimpleFeature.parseSimpleFeature("1"));
		assertTrue(c.getContents().size() == 1);
		c.addFeature(SimpleFeature.parseSimpleFeature("test"));
		assertTrue(c.getContents().size() == 2);
		c.addFeature(SimpleFeature.parseSimpleFeature("t"));
		assertTrue(c.getContents().size() == 3);
		c.addFeature(SimpleFeature.parseSimpleFeature("4.6"));
		assertTrue(c.getContents().size() == 4);
		c.addFeature(SimpleFeature.parseSimpleFeature("true"));
		assertTrue(c.getContents().size() == 5);
	}
	
	/*
	 * ensure size increments on addition of a composite feature
	 */
	@Test
	public void testSizeOnCompositeAddition() {
		CompositeFeature c = new CompositeFeature();
		c.addFeature(CompositeFeature.parseFeature("(3, 2)"));
		assertTrue(c.getContents().size() == 1);
		c.addFeature(SimpleFeature.parseSimpleFeature("(3, (48, true), 6, (4, (3, 4)))"));
		assertTrue(c.getContents().size() == 2);
	}
	
	/*
	 * ensure getFeature() returns a correct feature
	 */
	@Test
	public void testFeatureSimpleRetrieval() {
		//create and fill a composite feature
		CompositeFeature c = new CompositeFeature();
		c.addFeature(SimpleFeature.parseSimpleFeature("1"));
		c.addFeature(SimpleFeature.parseSimpleFeature("test"));
		c.addFeature(SimpleFeature.parseSimpleFeature("t"));
		c.addFeature(SimpleFeature.parseSimpleFeature("4.6"));
		c.addFeature(SimpleFeature.parseSimpleFeature("true"));
		
		//test retrieval of index 0 integer
		assertTrue((int)c.getFeature(0).getContents() == 1);
		assertTrue(((SimpleFeature)c.getFeature(0)).getDistanceFunction() instanceof IntegerDistance);
		
		//test retrieval of index 2 character
		assertTrue((char)c.getFeature(2).getContents() == 't');
		assertTrue(((SimpleFeature)c.getFeature(2)).getDistanceFunction() instanceof CharacterEquals);
	}
	
	/*
	 * getFeature() should return null if invalid index supplied (< 0, or > size of feature)
	 */
	@Test
	public void testFeatureInvalidIndexRetrieval() {
		//create and fill a composite feature
		CompositeFeature c = new CompositeFeature();
		assertNull(c.getFeature(0));
		
		c.addFeature(SimpleFeature.parseSimpleFeature("1"));
		c.addFeature(SimpleFeature.parseSimpleFeature("test"));

		assertNull(c.getFeature(4));
		assertNull(c.getFeature(-1));
	}

	/*
	 * ensure getFeature() returns a correct feature
	 */
	@Test
	public void testFeatureCompositeRetrieval() {
		//create and fill a composite feature
		CompositeFeature c = new CompositeFeature();
		c.addFeature(CompositeFeature.parseFeature("1"));
		c.addFeature(CompositeFeature.parseFeature("(2, 3)"));
		c.addFeature(CompositeFeature.parseFeature("t"));
		c.addFeature(CompositeFeature.parseFeature("4.6"));
		c.addFeature(CompositeFeature.parseFeature("true"));
		
		//test retrieval of index 1 composite feature
		assertTrue(c.getFeature(1) instanceof CompositeFeature);
		//check value in index one of composite sub-feature is integer
		assertTrue(((ArrayList<Feature>)c.getFeature(1).getContents()).get(1).getContents() instanceof Integer);
	}
	
	@Test
	public void testInEqualityBetweenCompositeAndSimpleFeature(){
		String s1 = "Hello";
		SimpleFeature simpleFeature = new SimpleFeature(s1);
		CompositeFeature compositeFeature = new CompositeFeature();
		assertFalse(compositeFeature.equals(simpleFeature));
	}
	
	@Test
	public void testEqualityBetweenTwoCompositeFeatures(){
		CompositeFeature c1 = new CompositeFeature();
		CompositeFeature c2 = new CompositeFeature();
		c1.addFeature(CompositeFeature.parseFeature("(3, 2)"));
		c2.addFeature(CompositeFeature.parseFeature("(3, 2)"));
		
		assertTrue(c1.equals(c2));
	}
	
	@Test
	public void testInEqualityBetweenTwoCompositeFeatures(){
		CompositeFeature c1 = new CompositeFeature();
		CompositeFeature c2 = new CompositeFeature();
		c1.addFeature(CompositeFeature.parseFeature("(3, 2)"));
		c2.addFeature(CompositeFeature.parseFeature("(4, 2)"));
		
		assertFalse(c1.equals(c2));
	}
	
}

