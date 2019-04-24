package testProblemComponents;

import static org.junit.Assert.*;
import org.junit.Test;

import problemComponents.CompositeFeature;
import problemComponents.SimpleFeature;

public class SimpleFeatureTest {

	@Test
	public void testSimpleFeatureStringCreation() {
		String s = "Hello World";
		SimpleFeature simpleFeature = new SimpleFeature(s);
		assertTrue(simpleFeature.getContents().equals(s));
	}
	
	@Test
	public void testSimpleFeatureCharCreation() {
		char c = 'H';
		SimpleFeature simpleFeature = new SimpleFeature(c);
		assertTrue((char)simpleFeature.getContents() == c);
	}
	
	@Test
	public void testSimpleFeatureBooleanCreation() {
		boolean b = false;
		SimpleFeature simpleFeature = new SimpleFeature(b);
		assertTrue((boolean)simpleFeature.getContents() == b);
	}
	
	@Test
	public void testSimpleFeatureIntegerCreation() {
		int i = 42;
		SimpleFeature simpleFeature = new SimpleFeature(i);
		assertTrue((int)simpleFeature.getContents() == i);
	}
	
	@Test
	public void testSimpleFeatureDoubleCreation() {
		double d = 42.42;
		SimpleFeature simpleFeature = new SimpleFeature(d);
		assertTrue((double)simpleFeature.getContents() == d);
	}

	@Test
	public void testEqualityBetweenTwoStringSimpleFeatures(){
		String s1 = "Hello";
		String s2 = "World";
		SimpleFeature simpleFeature1 = new SimpleFeature(s1);
		SimpleFeature simpleFeature2 = new SimpleFeature(s2);
		assertFalse(simpleFeature1.equals(simpleFeature2));
	}
	
	@Test
	public void testEqualityBetweenTwoPrimitiveSimpleFeatures(){
		int i1 = 42;
		int i2 = 42;
		SimpleFeature simpleFeature1 = new SimpleFeature(i1);
		SimpleFeature simpleFeature2 = new SimpleFeature(i2);
		assertTrue(simpleFeature1.equals(simpleFeature2));
	}
	
	@Test
	public void testInqualityBetweenSimpleFeatureAndCompositeFeature(){
		String s1 = "Hello";
		SimpleFeature simpleFeature = new SimpleFeature(s1);
		CompositeFeature compositeFeature = new CompositeFeature();
		assertFalse(simpleFeature.equals(compositeFeature));
	}
	
}

