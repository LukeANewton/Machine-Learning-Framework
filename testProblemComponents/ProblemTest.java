package testProblemComponents;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

import problemComponents.CompositeFeature;
import problemComponents.Feature;
import problemComponents.Problem;
import problemComponents.TestExample;
import problemComponents.TrainingExample;

public class ProblemTest {
	Problem problem;
	ArrayList<TrainingExample> trainingExamples;
	ArrayList<TestExample> testExamples;
	ArrayList<String> fieldNames;
	double[] weights;

	@Before
	public void Setup(){
		trainingExamples = new ArrayList<>();
		
		ArrayList<Feature> newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("2"));
    	newPoint.add(CompositeFeature.parseFeature("3"));
    	newPoint.add(CompositeFeature.parseFeature("4"));
    	trainingExamples.add(new TrainingExample(newPoint));
    	
		testExamples = new ArrayList<>();;
		
		newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("5"));
    	newPoint.add(CompositeFeature.parseFeature("6"));
    	newPoint.add(CompositeFeature.parseFeature("7"));
    	testExamples.add(new TestExample(newPoint));
    	
		fieldNames = new ArrayList<>(Arrays.asList("a", "b", "c"));
		weights = new double[]{100, 100, 100};
		
		problem = new Problem(3, fieldNames);
		problem.setWeights(weights);
		problem.setTrainingExamples(trainingExamples);
		problem.setTestExamples(testExamples);
	}

	@Test
	public void testGetTrainingExamples(){
		assertTrue(problem.getTrainingExamples().equals(trainingExamples));
	}
	
	@Test
	public void testGetTrainingExample(){
		assertTrue(problem.getTrainingExample(0).equals(trainingExamples.get(0)));
	}
	
	@Test
	public void testSetTrainingExamples(){
		ArrayList<Feature> newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("2"));
    	newPoint.add(CompositeFeature.parseFeature("3"));
    	newPoint.add(CompositeFeature.parseFeature("4"));
    	trainingExamples.add(new TrainingExample(newPoint));
		problem.setTrainingExamples(trainingExamples);
    	
		assertTrue(problem.getTrainingExamples().equals(trainingExamples));
	}
	
	@Test
	public void testGetTestExamples(){
		assertTrue(problem.getTestExamples().equals(testExamples));
	}
	
	@Test
	public void testGetTestExample(){
		assertTrue(problem.getTestExample(0).equals(testExamples.get(0)));
	}
	
	@Test
	public void testSetTestExamples(){
		ArrayList<Feature> newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("2"));
    	newPoint.add(CompositeFeature.parseFeature("3"));
    	newPoint.add(CompositeFeature.parseFeature("4"));
    	testExamples.add(new TestExample(newPoint));
		problem.setTestExamples(testExamples);
    	
		assertTrue(problem.getTestExamples().equals(testExamples));
	}
	
	@Test
	public void testGetWeights(){
		assertTrue(problem.getWeights().equals(weights));
	}
	
	@Test
	public void testGetWeight(){
		assertTrue(problem.getWeight(0) == 100);
	}
	
	@Test
	public void testSetWeights(){
		weights = new double[]{2, 45, 8};
		problem.setWeights(weights);
		
		assertTrue(problem.getWeights().equals(weights));
	}
	
	@Test
	public void testGetFieldNames(){
		assertTrue(problem.getFieldNames().equals(fieldNames));
	}
	
	@Test
	public void testGetFieldName(){
		assertTrue(problem.getFieldName(0).equals("a"));
	}
	
	@Test
	public void testSetFieldNames(){
		fieldNames = new ArrayList<>(Arrays.asList("2", "d", "w"));
		problem.setFieldNames(fieldNames);
    	
		assertTrue(problem.getFieldNames().equals(fieldNames));
	}
	
	@Test
	public void testGetNumberOfFields(){
		assertTrue(problem.getNumberOfFields() == 3);
	}
	
	@Test
	public void testGetNumberOfTrainingExamples(){
		assertTrue(problem.getNumberOfTrainingExamples() == 1);
	}
	
	@Test
	public void testGetNumberOfTestExamples(){
		assertTrue(problem.getNumberOfTestExamples() == 1);
	}
	
	@Test
	public void testRemoveTrainingExample(){
		problem.removeTrainingExample(0);
		assertTrue(problem.getNumberOfTrainingExamples() == 0);
	}
	
	@Test
	public void testRemoveTestExample(){
		problem.removeTestExample(0);
		assertTrue(problem.getNumberOfTestExamples() == 0);
	}
	
	@Test
	public void testAddTrainingExample(){
		ArrayList<Feature> newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("7"));
    	newPoint.add(CompositeFeature.parseFeature("8"));
    	newPoint.add(CompositeFeature.parseFeature("9"));
    	problem.addTrainingExample(newPoint);
		
		assertTrue(problem.getNumberOfTrainingExamples() == 2);
	}
	
	@Test
	public void testAddTestExample(){
		ArrayList<Feature> newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("2"));
    	newPoint.add(CompositeFeature.parseFeature("3"));
    	newPoint.add(CompositeFeature.parseFeature("4"));
    	problem.addTestExample(newPoint);
		
		assertTrue(problem.getNumberOfTestExamples() == 2);
	}
	
	@Test
	public void testImportEqualsExport() throws Exception{
		ArrayList<String> n1 = new ArrayList<>(Arrays.asList("coordinates", "sq.ft.", "age", "price"));
    	//weights for prediction
    	double[] w1 = {100, 100, 100, 0};
    	//initialize problem
    	Problem problem = new Problem(4, n1);
    	problem.setWeights(w1);
    	
    	ArrayList<Feature> newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("(12, 25)"));
    	newPoint.add(CompositeFeature.parseFeature("1200"));
    	newPoint.add(CompositeFeature.parseFeature("new"));
    	newPoint.add(CompositeFeature.parseFeature("500000"));
    	problem.addTrainingExample(newPoint);
    	
    	newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("(10, 50)"));
    	newPoint.add(CompositeFeature.parseFeature("1000"));
    	newPoint.add(CompositeFeature.parseFeature("old"));
    	newPoint.add(CompositeFeature.parseFeature("300000"));
    	problem.addTrainingExample(newPoint);
    	
    	newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("(30, 100)"));
    	newPoint.add(CompositeFeature.parseFeature("800"));
    	newPoint.add(CompositeFeature.parseFeature("new"));
    	newPoint.add(CompositeFeature.parseFeature("400000"));
    	problem.addTrainingExample(newPoint);
    	
    	//add point to estimate
    	newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("(15, 20)"));
    	newPoint.add(CompositeFeature.parseFeature("1000"));
    	newPoint.add(CompositeFeature.parseFeature("new"));
    	newPoint.add(CompositeFeature.parseFeature("?"));
    	problem.addTestExample(newPoint);
    	
    	problem.serializedExport();
 
		Problem problem2 = new Problem(problem.getNumberOfFields());
		problem2.serializedImport();
		
		assertTrue(problem.toString().equals(problem2.toString()));
	}
	
	@Test
	public void testProblemEqualsSelf(){
		ArrayList<String> n1 = new ArrayList<>(Arrays.asList("coordinates", "sq.ft.", "age", "price"));
    	//weights for prediction
    	double[] w1 = {100, 100, 100, 0};
    	//initialize problem
    	Problem problem = new Problem(4, n1);
    	problem.setWeights(w1);
    	
    	ArrayList<Feature> newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("(12, 25)"));
    	newPoint.add(CompositeFeature.parseFeature("1200"));
    	newPoint.add(CompositeFeature.parseFeature("new"));
    	newPoint.add(CompositeFeature.parseFeature("500000"));
    	problem.addTrainingExample(newPoint);
    	
    	newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("(10, 50)"));
    	newPoint.add(CompositeFeature.parseFeature("1000"));
    	newPoint.add(CompositeFeature.parseFeature("old"));
    	newPoint.add(CompositeFeature.parseFeature("300000"));
    	problem.addTrainingExample(newPoint);
    	
    	newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("(30, 100)"));
    	newPoint.add(CompositeFeature.parseFeature("800"));
    	newPoint.add(CompositeFeature.parseFeature("new"));
    	newPoint.add(CompositeFeature.parseFeature("400000"));
    	problem.addTrainingExample(newPoint);
    	
    	//add point to estimate
    	newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("(15, 20)"));
    	newPoint.add(CompositeFeature.parseFeature("1000"));
    	newPoint.add(CompositeFeature.parseFeature("new"));
    	newPoint.add(CompositeFeature.parseFeature("?"));
    	problem.addTestExample(newPoint);
		
		assertTrue(problem.equals(problem));
	}
}

