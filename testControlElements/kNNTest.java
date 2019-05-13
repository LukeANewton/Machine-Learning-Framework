
package testControlElements;

import org.junit.Before;
import org.junit.Test;

import predictionElements.DistanceContentPair;
import predictionElements.kNN;
import problemComponents.CompositeFeature;
import problemComponents.Feature;
import problemComponents.Problem;
import problemComponents.TestExample;
import problemComponents.TrainingExample;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Test cases for the k-nearest-neighbors algorithm. Contains tests to ensure
 * the correct number of neighbors are returned, and that the closest neighbors are returned
 * 
 * @author Luke Newton
 *
 */
public class kNNTest{
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
    	newPoint.add(CompositeFeature.parseFeature("2"));
    	trainingExamples.add(new TrainingExample(newPoint));
    	
    	newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("3"));
    	newPoint.add(CompositeFeature.parseFeature("3"));
    	trainingExamples.add(new TrainingExample(newPoint));
    	
    	newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("4"));
    	newPoint.add(CompositeFeature.parseFeature("4"));
    	trainingExamples.add(new TrainingExample(newPoint));
    	
		testExamples = new ArrayList<>();;
		
		newPoint = new ArrayList<>();
    	newPoint.add(CompositeFeature.parseFeature("2"));
    	newPoint.add(CompositeFeature.parseFeature("2"));
    	testExamples.add(new TestExample(newPoint));
    	
		fieldNames = new ArrayList<>(Arrays.asList("a", "b"));
		weights = new double[]{100, 100};
		
		problem = new Problem(1, fieldNames);
		problem.setWeights(weights);
		problem.setTrainingExamples(trainingExamples);
		problem.setTestExamples(testExamples);
	}
	
	@Test
	/*Test to ensure that the correct number of neighbors is retreived (should return k neighbors)*/
	public void testGatherCorrectNumberOfNeighbors(){
		ArrayList<DistanceContentPair> results;
		int k = 2;
		
		//running with 3 training examples and we want to get the nearest 2
		results = kNN.getNearestNeighbors(k, problem, 0);
		
		assertTrue(results.size() == k);
	}
	
	@Test
	/*Test to ensure that the correct neighbors are returned (here, the two closest ones)*/
	public void testGatherCorrectNeighbors(){
		ArrayList<DistanceContentPair> results;
		int k = 2;
		
		//running with 3 training examples and we want to get the nearest 2
		results = kNN.getNearestNeighbors(k, problem, 0);
		
		//we know the distance here should be 1 since the test problem has the same point in test and training examples
		assertTrue(results.get(0).getDistance() == 1);
		assertTrue(results.get(0).getContent().equals(2));
		//we do not know the distance of the other point, but it hould be larger than the first
		assertTrue(results.get(1).getDistance() > results.get(0).getDistance());
		assertTrue(results.get(1).getContent().equals(3));
	}
}
