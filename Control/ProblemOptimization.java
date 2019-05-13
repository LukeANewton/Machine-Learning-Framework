package Control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import compositeFeatureDistanceStrategies.*;
import exampleDistanceCombinationStrategies.*;
import problemComponents.CompositeFeature;
import problemComponents.Feature;
import problemComponents.Problem;
import problemComponents.TestExample;
import problemComponents.TrainingExample;
import simpleFeatureDistanceStrategies.*;

/**
 * Class containing methods to optimize a problem's prediction accuracy by selecting the appropriate
 * strategies, weights, and number of nearest neighbors.
 * 
 * @author Luke Newton
 */
public class ProblemOptimization {
	//random number generator for optimization method
	private Random r;
	//the size of the population pool for tthe optimization algorithm
	private int populationSize;
	//the chance of mutation occuring in a sample from population
	private double mutationRate = 0.001;
	//the number of iterations of the genetic algorithm to perform
	private int numberOfGenerations;
	//list of all example strategies
	private static final ExampleDistanceStrategy[] exampleStrategies = 
		{new EuclidianDistance(), new exampleDistanceCombinationStrategies.ManhattanDistance(), new SimpleSummation(), new NumberOfSimilarFeatures()};
	//list of all composite strategies
	private static final CompositeDistanceStrategy[] compositeStrategies = {new EuclideanDistance(), new compositeFeatureDistanceStrategies.ManhattanDistance(),
			new CompositeEquivalence(), new NumberSimilarFeatures(), new ChebyshevDistance()};
	//list of all character strategies
	private static final SimpleDistanceStrategy[] characterStrategies = {new CharacterEquals(), new CharacterEqualsIgnoreCase(), 
			new CharacterDistance(), new CharacterDistanceIgnoreCase(), new CharacterAbsDistance(), new CharacterAbsDistanceIgnoreCase()};
	//list of all string strategies
	private static final SimpleDistanceStrategy[] stringStrategies = {new StringEquals(), new StringEqualsIgnoreCase(), new StringSimilarity(), 
			new StringSimilarityIgnoreCase(), new StringLengthEquals(), new StringLengthDistance(), new StringWordCountEquals(), new StringWordCountDistance()};
	//list of all integer strategies
	private static final SimpleDistanceStrategy[] integerStrategies = {new IntegerDistance(), new IntegerAbsDistance()};
	//list of all double strategies
	private static final SimpleDistanceStrategy[] doubleStrategies = {new DoubleDistance(), new DoubleAbsDistance()}; 

	/**Constructor*/
	public ProblemOptimization(int populationSize, int numberOfGenerations, double mutationRate, int seed){
		this.populationSize = populationSize;
		this.numberOfGenerations = numberOfGenerations;
		this.mutationRate = mutationRate;
		r = new Random(seed);
	}

	/**
	 * optimizes a problem's prediction accuracy by setting appropriate strategies and weights.
	 * Implemented using a genetic algorithm
	 * 
	 * @param problem the problem set to optimize
	 * @return the number of nearest neighbors to use in prediction
	 */
	public ProblemConfiguration optimizePrredictionConfiguration(Problem originalProblem){
		//the current best configuration for a problem set
		String incumbentSolution = null;
		//the score of the incumbent solution
		double incumbentAccuracy = 0;
		//the population pool
		String[] populationPool = new String[populationSize];
		//the score of each element in the populaiton pool
		double[] populationScore = new double[populationSize];
		//indicator of whether each element shall reproduce and continue to the next generation
		int[] reproductionCount = new int[populationSize];

		//create duplicate problem to perform optimization with, that uses training data as test cases
		Problem problem = new Problem(originalProblem.getNumberOfFields(), originalProblem.getFieldNames());
		ArrayList<TrainingExample> examples = originalProblem.getTrainingExamples();
		int index = 0;
		//use 70% of the total training examples as training data, other 30% used for test examples
		for(; index < examples.size() * 0.7; index++)
			problem.addTrainingExample(examples.get(index).getFields());
		for(; index < examples.size(); index++)
			problem.addTestExample(examples.get(index).getFields());
		
		//fill population pool with initial values
		for(int i = 0; i < populationSize; i++)
			populationPool[i] = generateRandomConfiguration(problem);

		//iterate over method for each generation
		for(int generation = 0; generation < numberOfGenerations; generation++){
			System.out.println("generation " + generation +" start");

			//score each configuration
			for(int i = 0; i < populationSize; i++)
				populationScore[i] = 1 + getConfigurationAccuracy(decodeConfiguration(populationPool[i], problem));	

			//record current best score
			for(int i = 0; i < populationSize; i++){
				if(populationScore[i] > incumbentAccuracy){
					incumbentSolution = populationPool[i];
					incumbentAccuracy = populationScore[i];
				}
			}
			//normalize scores
			populationScore = normalizeScores(populationScore);

			//determine which configurations take part in crossover
			double[] wheel = buildRouletteWheel(populationScore);
			double spin;
			for(int i = 0; i < populationSize; i++)
				reproductionCount[i] = 0;
			for(int i = 0; i < populationSize; i++){
				spin = r.nextDouble();
				for(int j = 0; j < populationSize; j++){
					if(spin < wheel[j]){
						reproductionCount[i]++;
						break;
					}
				}
			}
			//perform crossover on those selected to reproduce
			ArrayList<String> newPopulationPool = new ArrayList<>();
			String config1, config2;
			int firstCrossoverConfigIndex, secondCrossoverConfigIndex;
			int crossoverPoint, pivotIndex1, pivotIndex2;
			while(newPopulationPool.size() < populationSize){
				//get 2 configurations to crossover
				firstCrossoverConfigIndex = getConfigIndexToCrossOver(reproductionCount);
				reproductionCount[firstCrossoverConfigIndex]--;
				secondCrossoverConfigIndex = getConfigIndexToCrossOver(reproductionCount);
				reproductionCount[secondCrossoverConfigIndex]--;	
				config1 = populationPool[firstCrossoverConfigIndex];
				config2 = populationPool[secondCrossoverConfigIndex];
				//perform crossover
				crossoverPoint = getRandomIndexInConfiguration(config1);	
				pivotIndex1 = nthOccurenceOf(config1, "-", crossoverPoint);
				pivotIndex2 = nthOccurenceOf(config2, "-", crossoverPoint);
				
				newPopulationPool.add(config1.substring(0, pivotIndex1) + config2.substring(pivotIndex2));
				newPopulationPool.add(config2.substring(0, pivotIndex2) + config1.substring(pivotIndex1));
			}
			//copy new pool over for next generation
			for(int i = 0; i < populationSize; i++)
				populationPool[i] = newPopulationPool.get(i);
			
			//perform mutation phase on new pool
			for(int i = 0; i < populationSize; i++){
				if(r.nextDouble() < mutationRate)
					populationPool[i] = mutate(populationPool[i]);
			}
		}

		System.out.println(incumbentSolution);
		System.out.println(incumbentAccuracy - 1);
		return decodeConfiguration(incumbentSolution, originalProblem);
	}

	/**
	 * finds the nth occurence of the substring in string s
	 * 
	 * @param s the string to search for occurences
	 * @param substring the substring being searched for within s
	 * @param n the number of occurences we are looking for substring in s
	 * @return the index position in s of the nth occurence of substring
	 */
	private int nthOccurenceOf(String s, String substring, int n) {
		int position = s.indexOf(substring);
		while (n > 0 && position != -1){
			position = s.indexOf(substring, position + 1);
			n--;
		}
		return position;
	}

	/**
	 * The mutation function used in optimization. If triggered, a strategy/weight is altered in the problem configuration
	 * 
	 * @param config the problem configuration string to alter
	 * @return the modified configuration string
	 */
	private String mutate(String config) {
		int index = getRandomIndexInConfiguration(config);
		switch(index){
		case 0://example strategy
			config = replaceCharacter(config, Integer.toString(r.nextInt(exampleStrategies.length)), index);
			break;
		case 1://composite strategy
			config = replaceCharacter(config, Integer.toString(r.nextInt(compositeStrategies.length)), index);
			break;
		case 2://character strategy
			config = replaceCharacter(config, Integer.toString(r.nextInt(characterStrategies.length)), index);
			break;
		case 3://double strategy
			config = replaceCharacter(config, Integer.toString(r.nextInt(doubleStrategies.length)), index);
			break;
		case 4://integer strategy
			config = replaceCharacter(config, Integer.toString(r.nextInt(integerStrategies.length)), index);
			break;
		case 5://string strategy
			config = replaceCharacter(config, Integer.toString(r.nextInt(stringStrategies.length)), index);
			break;
		default://weight or k
			int charValue = Character.getNumericValue(config.charAt(index));
			if(charValue < 5)
				config = replaceCharacter(config, Integer.toString(charValue + 5), index);
			else
				config = replaceCharacter(config, Integer.toString(charValue - 5), index);
		}
		return config;
	}

	/**
	 * replaces the character in s at index position with provided substring
	 * 
	 * @param s the string within which to replcae a character
	 * @param c the substring to swap into the string
	 * @param index the position within the string of the character to replace
	 * @return the original string s with the character at index position replcaed with substring c
	 */
	private String replaceCharacter(String s, String c, int index){
		String[] splitString = s.split("-");
		splitString[index] = c;
		
		StringBuffer newString = new StringBuffer();
		for(int i = 0; i < splitString.length; i++){
			newString.append(splitString[i]);
			if(i < splitString.length - 1)
				newString.append("-");
		}
		return newString.toString();
	}

	/**returns a random position section within a configuration string*/
	private int getRandomIndexInConfiguration(String config) {
		return r.nextInt(config.split("-").length - 1);
	}

	/**returns a random, non-zero value index from an input reproduction count array*/
	private int getConfigIndexToCrossOver(int[] reproductionCount) {
		int i;
		do{
			i = r.nextInt(reproductionCount.length);
		}while(reproductionCount[i] == 0);

		return i;
	}

	/**
	 * creates tranforms an input array into an array of running summations,
	 * where the first output index is equal to the first input index and 
	 * last output index is the summation of all element in the input. Used
	 * to map random number generated to a particular index
	 * 
	 * @param scores the input normalized scores
	 * @return an array of cummulative sums of the input array
	 */
	private double[] buildRouletteWheel(double[] scores){
		double[] wheel = new double[scores.length];
		for(int i = 0; i < scores.length; i++){
			wheel[i] = scores[0];
			for(int j = 1; j <= i; j++)
				wheel[i] += scores[j];
		}
		return wheel;
	}

	/**
	 * normalizes input array by dividing each element by the sum of all values
	 * 
	 * @param scores the input array
	 * @return the normalized array
	 */
	private double[] normalizeScores(double[] scores){
		double sum = scores[0];
		for(int i = 1; i < scores.length; i++)
			sum += scores[i];
		for(int i = 0; i < scores.length; i++)
			scores[i] /= sum;
		return scores;
	}

	/**main function to run program*/
	public static void main(String[] args){
		ArrayList<TestExample> testExamples = new ArrayList<>();
		ArrayList<TrainingExample> trainingExamples = new ArrayList<>();
		//read in whole file
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("adult2.data"));
			String line = null;
			while((line=br.readLine()) != null){
				if(line.equals(""))
					continue;
				String[] splitLine = line.split(", ");
				ArrayList<Feature> features = new ArrayList<>();
				for(int i = 0; i < splitLine.length; i++)
					features.add(CompositeFeature.parseFeature(splitLine[i]));	

				TrainingExample t = new TrainingExample(features);
				trainingExamples.add(t);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//move 30% of records to test examples
		for(int i = 0; i < trainingExamples.size()*0.3; i++){
			testExamples.add(trainingExamples.get(i).toTestExample());
			trainingExamples.remove(i);
		}

		Problem problem = new Problem(trainingExamples.get(1).getNumberOfFields());
		problem.setTrainingExamples(trainingExamples);
		problem.setTestExamples(testExamples);

		ProblemOptimization opt = new ProblemOptimization(100, 50, 0.001, 123456789);
		ProblemConfiguration pc = opt.optimizePrredictionConfiguration(problem);
		problem = pc.getProblem();
		int k = pc.getK();
		problem.resetAccuracy();
		
		for(int i = 0; i < testExamples.size(); i++){
			Object prediction = Prediction.getPrediction(k, problem, i);
			System.out.println("Test Example " + i + ": " + problem.getTestExample(i));
			System.out.println("     Prediction: " + prediction);
			System.out.println("     Actual Value: " + problem.getTestExample(i).getUnknownFeature());
			problem.updateAccuracy(prediction, problem.getTestExample(i).getUnknownFeature());
		}
		System.out.println("Accuracy: " + problem.getAccuracy());
		try {
			problem.serializedExport("adult-wage.data");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("done");
	}

	/**generates a random configuration of strategies and weights encodes them into a string*/
	private String generateRandomConfiguration(Problem problem){
		StringBuffer s = new StringBuffer();

		//encode example distance strategy
		s.append(r.nextInt(exampleStrategies.length) + "-");
		//encode composite distance strategy
		s.append(r.nextInt(compositeStrategies.length) + "-");
		//encode character distance strategy
		s.append(r.nextInt(characterStrategies.length) + "-");
		//encode double distance strategy
		s.append(r.nextInt(doubleStrategies.length) + "-");
		//encode integer distance strategy
		s.append(r.nextInt(integerStrategies.length) + "-");
		//encode string distance strategy
		s.append(r.nextInt(stringStrategies.length) + "-");
		//encode weights
		for(int i = 0; i < problem.getNumberOfFields(); i++)
			s.append(r.nextInt(100) + "-");
		//encode number of nearest neighbors
		s.append(r.nextInt(problem.getNumberOfTrainingExamples()) + 1);

		return s.toString();
	}

	/**
	 * get the accuracy of a predition configuration by prediction each test example
	 * 
	 * @param pc the problem configuration containing all test examples, strategies, and number of nearest neighbors to use
	 * @return the accuracy of the problem configuration
	 */
	private double getConfigurationAccuracy(ProblemConfiguration pc){
		pc.getProblem().resetAccuracy();
		int numTestExamples = pc.getProblem().getNumberOfTestExamples();
		for(int i = 0; i < numTestExamples; i++){
			if(pc.getProblem().getTestExample(i).getUnknownFeature() == null)
				continue; //if there is no value to compare to, cant use this example in accuracy calculation 
			Object prediction = Prediction.getPrediction(pc.getK(), pc.getProblem(), i);

			pc.getProblem().updateAccuracy(prediction, pc.getProblem().getTestExample(i).getUnknownFeature());
		}
		return pc.getProblem().getAccuracy();
	}

	/**
	 * converts the string representation of a problem configuration and applies the encoded strategies to a problem
	 * 
	 * @param configuration the string containing encoded strategies and number of neighbors
	 * @param problem the problem set to appliy the encoded strategies to
	 * @return the problem set with strategies applies to it and the number of nearest neighbors to use
	 */
	private ProblemConfiguration decodeConfiguration(String configuration, Problem problem){
		String[] splitConfig = configuration.split("-");

		int exampleIndex = Integer.parseInt(splitConfig[0]);
		int compositeIndex = Integer.parseInt(splitConfig[1]);
		int characterIndex = Integer.parseInt(splitConfig[2]);
		int doubleIndex = Integer.parseInt(splitConfig[3]);
		int integerIndex = Integer.parseInt(splitConfig[4]);
		int stringIndex = Integer.parseInt(splitConfig[5]);

		problem.setStrategies(exampleStrategies[exampleIndex], compositeStrategies[compositeIndex], 
				characterStrategies[characterIndex], doubleStrategies[doubleIndex], integerStrategies[integerIndex],
				stringStrategies[stringIndex]);

		double[] weights = new double[problem.getNumberOfFields()];
		int i = 6;
		for(; i < weights.length + 6; i++)
			weights[i-6] = Double.parseDouble(splitConfig[i]);
		problem.setWeights(weights);

		return new ProblemConfiguration(problem, Integer.parseInt(splitConfig[i]));
	}
}
