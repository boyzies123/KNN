package part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class KNNAlgorithm{
	private List <Wine> trainingData = new ArrayList <Wine>();
	private List <Wine> testingData = new ArrayList <Wine>();
	public KNNAlgorithm() {
		
	}
	/**
	 * Start the program
	 * @param trainingDataFileName file name of training data
	 * @param testingDataFileName file name of testing data
	 * @param k value of k for knn algorithm
	 */
	public void run(String trainingDataFileName, String testingDataFileName, String outputFileName, String k) {
		//load the training and test
		loadData(trainingDataFileName, trainingData);
		loadData(testingDataFileName, testingData);
		normalize(trainingData);
		normalize(testingData);
		startAlgorithm(Integer.parseInt(k), outputFileName, testingData);
		// Commented out testing with training data set. If needed, uncomment it to run.
		//startAlgorithm(Integer.parseInt(k), outputFileName, trainingData);
	}
	/**
	 * Normalize all values in dataset
	 * @param dataSet 
	 */
	public void normalize(List <Wine> dataSet) {
		//find the min and max value first
		List <Function <Wine, Double>> wineFunctions = List.of(w -> w.getAlcohol(), w -> w.getMalicAcid(), w -> w.getAsh(), w-> w.getAlcalinityOfAsh(), w -> w.getMagnesium(), w -> w.getPhenols(), w -> w.getFlavanoids(), w -> w.getNonFlavanoidPhenols(), w -> w.getProanthocyanins(), w-> w.getColorIntensity(), w -> w.getHue(), w-> w.getOD(), w-> w.getProline());
		Double max = null;
		Double min = null;
		Map <Wine, List<Consumer<Double>>> mapToSetterFunctions = new HashMap <Wine, List<Consumer<Double>>>();
		for (Wine w : dataSet) {
			mapToSetterFunctions.put(w, List.of((Double value) -> w.setAlcohol(value), (Double value) -> w.setMalicAcid(value), (Double value) -> w.setAsh(value), (Double value) -> w.setAlcalinityOfAsh(value), (Double value) -> w.setMagnesium(value), (Double value) -> w.setPhenols(value), (Double value) -> w.setFlavanoids(value), (Double value) -> w.setNonFlavanoidPhenols(value), (Double value) -> w.setProanthocyanins(value), (Double value) -> w.setColorIntensity(value), (Double value) -> w.setHue(value), (Double value) -> w.setOD(value), (Double value) -> w.setProline(value)));
		}
		for (int i = 0; i < wineFunctions.size(); i++) {
			Optional<Double> maxValue = dataSet.stream().map(wineFunctions.get(i)).max((x, y)-> Double.compare(x, y));
			Optional<Double> minValue = dataSet.stream().map(wineFunctions.get(i)).min((x, y) -> Double.compare(x, y));
			if (maxValue.isPresent()) {
				max = maxValue.get();
				
			}
			if (minValue.isPresent()) {
				min = minValue.get();
			}
			else {
				System.out.println("No value present");
				break;
			}
			for (Wine w : dataSet) {
				Double xNormalize = (wineFunctions.get(i).apply(w) - min) / (max - min);
				mapToSetterFunctions.get(w).get(i).accept(xNormalize);
			}
		}	
	}
	/**
	 * Essentially the testing/predicting for kNN
	 * @param k the k neighbours to consider for the algorithm
	 * @param outputFileName filename of output
	 * @param data the data set used for testing
	 */
	public void startAlgorithm(Integer k, String outputFileName, List <Wine> data) {
		//Keep track of the top k shortest distance from of every testing data point. For the output table.
		Map <Wine, List<Double>> shortestDistance = new LinkedHashMap <Wine, List<Double>>();
		//Keep a copy of original testing datas class to compare class results.
		List <Integer> originalTestingData = new ArrayList<Integer>();
		for (Wine wineTesting : data) {
			//First add the original class number to originalTestingData
			originalTestingData.add(wineTesting.getClassOfWine());
			//Map of wine to distance from the testing point
			Map <Wine, Double> distanceMap = new HashMap <Wine, Double>();
			for (Wine wineTraining : trainingData) {
				//Calculate distance from current testing point to all other training points
				Double distance = Math.sqrt((Math.pow(wineTesting.getAlcohol()-wineTraining.getAlcohol(), 2)) + (Math.pow(wineTesting.getMalicAcid() - wineTraining.getMalicAcid(), 2)) + (Math.pow(wineTesting.getAsh() - wineTraining.getAsh(), 2)) + (Math.pow(wineTesting.getAlcalinityOfAsh() - wineTraining.getAlcalinityOfAsh(), 2)) + (Math.pow(wineTesting.getMagnesium() - wineTraining.getMagnesium(), 2)) + (Math.pow(wineTesting.getPhenols() - wineTraining.getPhenols(), 2)) + (Math.pow(wineTesting.getFlavanoids() - wineTraining.getFlavanoids(), 2)) + (Math.pow(wineTesting.getNonFlavanoidPhenols() - wineTraining.getNonFlavanoidPhenols(), 2)) + (Math.pow(wineTesting.getProanthocyanins() - wineTraining.getProanthocyanins(), 2)) + (Math.pow(wineTesting.getColorIntensity() - wineTraining.getColorIntensity(), 2)) + (Math.pow(wineTesting.getHue() - wineTraining.getHue(), 2)) + (Math.pow(wineTesting.getOD() - wineTraining.getOD(), 2)) + (Math.pow(wineTesting.getProline() - wineTraining.getProline(), 2)));
				distanceMap.put(wineTraining, distance);
			}
			//sort the map by value so we can get the shortest distance
			Stream <Map.Entry<Wine, Double>> m = distanceMap.entrySet().stream()
			.sorted(Map.Entry.comparingByValue());
			List <Map.Entry<Wine, Double>> list = m.toList();
			//Loop k times and find which class is most common.
			List <Integer> classCount = new ArrayList <Integer>();
			shortestDistance.put(wineTesting, new ArrayList <Double>());
			for (int i = 0; i < k; i++) {
				classCount.add(list.get(i).getKey().getClassOfWine());
				//add the k shortest distances.
				shortestDistance.get(wineTesting).add(list.get(i).getValue());
			}
			int countOne = 0;
			int countTwo = 0;
			int countThree = 0;
			for (int i = 0; i < classCount.size(); i++) {
				if (classCount.get(i) == 1){
					countOne++;
				}
				else if (classCount.get(i) == 2) {
					countTwo++;
				}
				else {
					countThree++;
				}
			}
			//Finally assign the test point the class
			if (countOne > countTwo && countOne > countThree) {
				wineTesting.setClass(1);
			}
			else if (countTwo > countOne && countTwo > countThree) {
				wineTesting.setClass(2);
			}
			else if (countThree > countOne && countThree > countTwo) {
				wineTesting.setClass(3);
			}
			//tie breaker. Choose random class between the tie
			else {
				//https://stackoverflow.com/questions/5271598/java-generate-random-number-between-two-given-values
				Random r = new Random();
				if (countOne == countTwo) {
					wineTesting.setClass(r.nextInt(2) + 1);
				}
				else if (countOne == countThree) {
					wineTesting.setClass(r.nextInt(2) * 2 + 1);
				}
				//counttwo == countthree
				else {
					wineTesting.setClass(r.nextInt(2) + 2);
				}
			}
		}
		//Finding classification accuracy
		double trueValues = 0;
	    double falseValues = 0;
		for (int i = 0; i < originalTestingData.size(); i++) {
			if (data.get(i).getClassOfWine() == originalTestingData.get(i)) {
				trueValues++;
			}
			else {
				falseValues++;
			}
		}
		//Print the accuracy
		System.out.println("Classifcation Accuracy: " + (trueValues/(trueValues+falseValues))*100);
		writeToFile(k, shortestDistance, originalTestingData, outputFileName, data);
	}
	/**
	 * Put all relevant information into a csv file
	 * @param k for iteration purposes 
	 * @param distance each wine at the k nearest distances to other data
	 * @param originalTestingData the original class label of data
	 * @param output file name 
	 * @param data data set with new predicted class label
	 */
	public void writeToFile(Integer k, Map <Wine, List<Double>> distanceMap, List <Integer> originalTestingData, String outputFileName, List <Wine> data) {
		//With help from https://www.youtube.com/watch?v=wEdmfQ1-gOs
		int track = 0;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("y").append(",").append("predicted_y").append(",");
		for (int i = 1; i <= k; i++) {
			stringBuilder.append("distance" + i);
			if (i != k) {
				stringBuilder.append(",");
			}
		}
		stringBuilder.append("\n");
		for (Map.Entry<Wine, List<Double>> m : distanceMap.entrySet()) {
			stringBuilder.append(originalTestingData.get(track)).append(",").append(data.get(track).getClassOfWine()).append(",");
			for (int i = 0; i < k; i++) {
				if (i < k-1) {
					stringBuilder.append(m.getValue().get(i)).append(",");
				}
				else {
					stringBuilder.append(m.getValue().get(i));
				}
			}
			stringBuilder.append("\n");
			track++;
		}
		try {
			FileWriter writer = new FileWriter(outputFileName);
			writer.write(stringBuilder.toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Load a csv file and add it to relevant data structure. Essentially the training phase.
	 * @param dataFileName file name of data to read
	 * @param data for adding data after reading it from file
	 */
	public void loadData(String dataFileName, List <Wine> data) {
		//Read csv file
		//https://www.youtube.com/watch?v=zKDmzKaAQro
		try {
			BufferedReader reader = new BufferedReader(new FileReader(dataFileName));
			//read first line without storing as it is just attributes as first line
			reader.readLine();
			String line = reader.readLine();
			while (line != null) {
				String [] rows = line.split(",");
				Double [] row = new Double [rows.length];
				//convert the data to double and store it.
				for (int i = 0; i < rows.length; i++) {
					row[i] = (Double.parseDouble(rows[i]));
				}
				data.add(new Wine(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9], row[10], row[11], row[12], row[13]));
				//read next line in csv
				line = reader.readLine();
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[]args) {
		KNNAlgorithm algorithm = new KNNAlgorithm();
		algorithm.run(args[0], args[1], args[2], args[3]);
	}
}
