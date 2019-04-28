/**
 * 
 */
package testControlElements;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import Control.Prediction;
import problemComponents.CompositeFeature;
import problemComponents.Feature;
import problemComponents.Problem;
import problemComponents.TestExample;
import problemComponents.TrainingExample;

/**
 * Test cases for the prediction method.
 * This is tricky to test, so we can only write tests for small sets.
 * 
 * @author Luke Newton
 *
 */
public class predictionTest {
	Problem problem;
	ArrayList<TrainingExample> trainingExamples;
	ArrayList<TestExample> testExamples;
	ArrayList<String> fieldNames;
	double[] weights;
	
	@Before
	public void Setup(){
		fieldNames = new ArrayList<>(Arrays.asList("a", "b"));
		weights = new double[]{100, 100};
		
		problem = new Problem(1, fieldNames);
		problem.setWeights(weights);
	}
	
	public void setSingleTestAndTrainingExamples(Object output){
		trainingExamples = new ArrayList<>();
		
		ArrayList<Feature> newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("2"));
    	newPoint.add(CompositeFeature.parseFeature(output.toString()));
    	trainingExamples.add(new TrainingExample(newPoint));
    	
		testExamples = new ArrayList<>();;
		
		newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("2"));
    	newPoint.add(CompositeFeature.parseFeature(""));
    	testExamples.add(new TestExample(newPoint));
		
		problem.setTrainingExamples(trainingExamples);
		problem.setTestExamples(testExamples);
	}
	
	@Test
	/**Test to ensure that the correct prediction is made with a single input string*/
	public void testSmallPredictionString(){
		setSingleTestAndTrainingExamples("bagel");
		Object result = Prediction.getPrediction(problem.getNumberOfTrainingExamples(), problem, 0);
		
		assertTrue(((String)result).equals("bagel"));
	}
	
	@Test
	/**Test to ensure that the correct prediction is made with a single input number*/
	public void testSmallPredictionNumber(){
		setSingleTestAndTrainingExamples("42");
		Object result = Prediction.getPrediction(problem.getNumberOfTrainingExamples(), problem, 0);
		
		assertTrue(result.equals(42));
	}
	
	@Test
	/**Test to ensure that when the test example being predicted is in the training data, the prediction returns the correct output*/
	public void testNumberPredictionWhenTestInTrainingSet(){
		trainingExamples = new ArrayList<>();
		ArrayList<Feature> newPoint = new ArrayList<>();
		for(int i = 0; i < 1000; i++){
			newPoint = new ArrayList<>();
	    	newPoint.add(CompositeFeature.parseFeature(Integer.toString(i)));
	    	newPoint.add(CompositeFeature.parseFeature(Integer.toString(i+1)));
	    	trainingExamples.add(new TrainingExample(newPoint));
		}
		problem.setTrainingExamples(trainingExamples);
    	
		testExamples = new ArrayList<>();;
		newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("3"));
    	newPoint.add(CompositeFeature.parseFeature(""));
    	testExamples.add(new TestExample(newPoint));
    	newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("7"));
    	newPoint.add(CompositeFeature.parseFeature(""));
    	testExamples.add(new TestExample(newPoint));
		problem.setTestExamples(testExamples);
		
		Object result = Prediction.getPrediction(problem.getNumberOfTrainingExamples(), problem, 0);
		
		assertTrue(result.equals(4));
	}
	
	
	@Test
	/**Test to ensure that when the test example being predicted is in the training data, the prediction returns the correct output*/
	public void testStringPredictionWhenTestInTrainingSet(){
		trainingExamples = new ArrayList<>();
		ArrayList<Feature> newPoint = new ArrayList<>();
		for(int i = 0; i < 1000; i++){
			newPoint = new ArrayList<>();
	    	newPoint.add(CompositeFeature.parseFeature(Integer.toString(i)));
	    	
	    	StringBuffer output = new StringBuffer();
	    	output.append("ba");
	    	for(int j = 0; j < i; j++){
	    		output.append("na");
	    	}
	  
	    	newPoint.add(CompositeFeature.parseFeature(output.toString()));
	    	trainingExamples.add(new TrainingExample(newPoint));
		}
		problem.setTrainingExamples(trainingExamples);
    	
		testExamples = new ArrayList<>();;
		newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("3"));
    	newPoint.add(CompositeFeature.parseFeature(""));
    	testExamples.add(new TestExample(newPoint));
    	newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("2"));
    	newPoint.add(CompositeFeature.parseFeature(""));
    	testExamples.add(new TestExample(newPoint));
		problem.setTestExamples(testExamples);
		
		Object result = Prediction.getPrediction(problem.getNumberOfTrainingExamples(), problem, 1);
		assertTrue(result.equals("banana"));
	
		result = Prediction.getPrediction(problem.getNumberOfTrainingExamples(), problem, 0);
		assertTrue(result.equals("bananana"));
	}
}
