package testProblemComponents;
import static org.junit.Assert.*;

import org.junit.Test;

import problemComponents.CompositeFeature;
import problemComponents.SimpleFeature;
import simpleFeatureDistanceStrategies.BooleanDistance;
import simpleFeatureDistanceStrategies.CharacterEquals;
import simpleFeatureDistanceStrategies.DoubleDistance;
import simpleFeatureDistanceStrategies.IntegerDistance;
import simpleFeatureDistanceStrategies.StringEquals;

/**
 * tests CompositeFeature's - and by extension, SimpleFeature's - parseFeature function
 * 
 * @author luke newton
 * @version 1
 */
public class FeatureParseTest {

	/**
	 * test for proper string identification. tests when strings are single words, multiple words, have other types
	 * in them but not formatted as a composite feature
	 */
	@Test
	public void testStringParse() {
		//one worded string
		assertTrue(CompositeFeature.parseFeature("test").getContents() instanceof String);
		//mulitworded strings
		assertTrue(CompositeFeature.parseFeature("test with many words").getContents() instanceof String);
		assertTrue(CompositeFeature.parseFeature("part of this test has a true in it").getContents() instanceof String);
		assertTrue(CompositeFeature.parseFeature("part of this test has a 1 in it").getContents() instanceof String);
		assertTrue(CompositeFeature.parseFeature("part of this test has a 34.235 in it").getContents() instanceof String);
	}
	
	/**
	 * test for character identification. test for uppercase, lowercase, special characters, empty space, and
	 * ensures a one digit number is not a character.
	 */
	@Test
	public void testCharacterParse() {
		//lowercase character
		assertTrue(CompositeFeature.parseFeature("t").getContents() instanceof Character);
		//uppercase character
		assertTrue(CompositeFeature.parseFeature("W").getContents() instanceof Character);
		//special character
		assertTrue(CompositeFeature.parseFeature("^").getContents() instanceof Character);
		//single character number should not be characters
		assertFalse(CompositeFeature.parseFeature("6").getContents() instanceof Character);
		//whitespace
		assertTrue(CompositeFeature.parseFeature(" ").getContents() instanceof Character);
	}
	
	/**
	 * test for boolean identification
	 */
	@Test
	public void testBooleanParse() {
		assertTrue(CompositeFeature.parseFeature("true").getContents() instanceof Boolean);
		assertTrue(CompositeFeature.parseFeature("false").getContents() instanceof Boolean);
	}
	
	/**
	 * test for integer identification
	 */
	@Test
	public void testIntgerParse() {
		//small integer test
		assertTrue(CompositeFeature.parseFeature("1").getContents() instanceof Integer);
		//large integer test
		assertTrue(CompositeFeature.parseFeature("15621454").getContents() instanceof Integer);
		//double with no decimal value should still be double
		assertFalse(CompositeFeature.parseFeature("5.0").getContents() instanceof Integer);
		//double should not be integer
		assertFalse(CompositeFeature.parseFeature("5.7").getContents() instanceof Integer);
		//negative integer test
		assertTrue(CompositeFeature.parseFeature("-2").getContents() instanceof Integer);
		//zero tests
		assertTrue(CompositeFeature.parseFeature("0").getContents() instanceof Integer);
		assertFalse(CompositeFeature.parseFeature("0.0").getContents() instanceof Integer);
	}
	
	/**
	 * test for double identification
	 */
	@Test
	public void testDoubleParse() {
		//integers should not be doubles
		assertFalse(CompositeFeature.parseFeature("15621454").getContents() instanceof Double);
		//large double test
		assertTrue(CompositeFeature.parseFeature("15621454.24534452346").getContents() instanceof Double);
		//double with no decimal value test
		assertTrue(CompositeFeature.parseFeature("5.0").getContents() instanceof Double);
		//positive double test
		assertTrue(CompositeFeature.parseFeature("5.7").getContents() instanceof Double);
		//negative double test
		assertTrue(CompositeFeature.parseFeature("-2.1").getContents() instanceof Double);
		//zero tests
		assertFalse(CompositeFeature.parseFeature("0").getContents() instanceof Double);
		assertTrue(CompositeFeature.parseFeature("0.0").getContents() instanceof Double);
	}
	
	/**
	 * test a basic composite feature
	 */
	@Test
	public void testPointParse(){
		CompositeFeature compFeature = (CompositeFeature) CompositeFeature.parseFeature("(1, 2)");
		
		//first feature in composite feature should be Integer (1)
		assertTrue(compFeature.getContents().get(0).getContents() instanceof Integer);
		assertTrue(((SimpleFeature)compFeature.getContents().get(0)).getDistanceFunction() instanceof IntegerDistance);
		
		//first feature in composite feature should be Integer (2)
		assertTrue(compFeature.getContents().get(1).getContents() instanceof Integer);
		assertTrue(((SimpleFeature)compFeature.getContents().get(1)).getDistanceFunction() instanceof IntegerDistance);
		
	}
	
	/**
	 * test a composite feature with various types
	 */
	@Test
	public void testCompositeParse(){
		CompositeFeature compFeature = (CompositeFeature) CompositeFeature.parseFeature("(1, true, test, 2, d, 4.7)");
		assertTrue(compFeature.getContents().size() == 6);
		
		//first feature in composite feature should be Integer (1)
		assertTrue(compFeature.getContents().get(0)instanceof SimpleFeature);
		assertTrue(compFeature.getContents().get(0).getContents() instanceof Integer);
		assertTrue(((SimpleFeature)compFeature.getContents().get(0)).getDistanceFunction() instanceof IntegerDistance);
		
		//second feature in composite feature should be boolean (true)
		assertTrue(compFeature.getContents().get(1)instanceof SimpleFeature);
		assertTrue(compFeature.getContents().get(1).getContents() instanceof Boolean);
		assertTrue(((SimpleFeature)compFeature.getContents().get(1)).getDistanceFunction() instanceof BooleanDistance);

		//third feature in composite feature should be string (test)
		assertTrue(compFeature.getContents().get(2)instanceof SimpleFeature);
		assertTrue(compFeature.getContents().get(2).getContents() instanceof String);
		assertTrue(((SimpleFeature)compFeature.getContents().get(2)).getDistanceFunction() instanceof StringEquals);
		
		//fourth feature in composite feature should be integer (2)
		assertTrue(compFeature.getContents().get(3)instanceof SimpleFeature);
		assertTrue(compFeature.getContents().get(3).getContents() instanceof Integer);
		assertTrue(((SimpleFeature)compFeature.getContents().get(3)).getDistanceFunction() instanceof IntegerDistance);
		
		//fifth feature in composite feature should be char (d)
		assertTrue(compFeature.getContents().get(4)instanceof SimpleFeature);
		assertTrue(compFeature.getContents().get(4).getContents() instanceof Character);
		assertTrue(((SimpleFeature)compFeature.getContents().get(4)).getDistanceFunction() instanceof CharacterEquals);
		
		//sixth feature in composite feature should be double (4.7)
		assertTrue(compFeature.getContents().get(5)instanceof SimpleFeature);
		assertTrue(compFeature.getContents().get(5).getContents() instanceof Double);
		assertTrue(((SimpleFeature)compFeature.getContents().get(5)).getDistanceFunction() instanceof DoubleDistance);
	}
	
	/**
	 * test for a composite feature with nested composite feature
	 */
	@Test
	public void testNestedCompositeParse(){
		CompositeFeature compFeature = (CompositeFeature) CompositeFeature.parseFeature("(1, true, test, (2, 3.0, false), d, 4.7)");
		
		//first feature in composite feature should be Integer (1)
		assertTrue(compFeature.getContents().get(0)instanceof SimpleFeature);
		assertTrue(compFeature.getContents().get(0).getContents() instanceof Integer);
		assertTrue(((SimpleFeature)compFeature.getContents().get(0)).getDistanceFunction() instanceof IntegerDistance);
		
		//second feature in composite feature should be boolean (true)
		assertTrue(compFeature.getContents().get(1)instanceof SimpleFeature);
		assertTrue(compFeature.getContents().get(1).getContents() instanceof Boolean);
		assertTrue(((SimpleFeature)compFeature.getContents().get(1)).getDistanceFunction() instanceof BooleanDistance);

		//third feature in composite feature should be string (test)
		assertTrue(compFeature.getContents().get(2)instanceof SimpleFeature);
		assertTrue(compFeature.getContents().get(2).getContents() instanceof String);
		assertTrue(((SimpleFeature)compFeature.getContents().get(2)).getDistanceFunction() instanceof StringEquals);
		
		//fourth feature should be nested composite feature
		assertTrue(compFeature.getContents().get(3) instanceof CompositeFeature);
		//first sub feature should be integer (2)
		assertTrue(((CompositeFeature)compFeature.getContents().get(3)).getContents().get(0).getContents() instanceof Integer);
		assertTrue(((SimpleFeature)((CompositeFeature)compFeature.getContents().get(3)).getContents().get(0)).getDistanceFunction() instanceof IntegerDistance);
		//second sub feature should double (3.0)
		assertTrue(((CompositeFeature)compFeature.getContents().get(3)).getContents().get(1).getContents() instanceof Double);
		assertTrue(((SimpleFeature)((CompositeFeature)compFeature.getContents().get(3)).getContents().get(1)).getDistanceFunction() instanceof DoubleDistance);
		//third sub feature should be boolean (false)
		assertTrue(((CompositeFeature)compFeature.getContents().get(3)).getContents().get(2).getContents() instanceof Boolean);
		assertTrue(((SimpleFeature)((CompositeFeature)compFeature.getContents().get(3)).getContents().get(2)).getDistanceFunction() instanceof BooleanDistance);
		
		//fifth feature in composite feature should be char (d)
		assertTrue(compFeature.getContents().get(4).getContents() instanceof Character);
		assertTrue(((SimpleFeature)compFeature.getContents().get(4)).getDistanceFunction() instanceof CharacterEquals);
		
		//sixth feature in composite feature should be double (4.7)
		assertTrue(compFeature.getContents().get(5).getContents() instanceof Double);
		assertTrue(((SimpleFeature)compFeature.getContents().get(5)).getDistanceFunction() instanceof DoubleDistance);
		
		
	}

}
