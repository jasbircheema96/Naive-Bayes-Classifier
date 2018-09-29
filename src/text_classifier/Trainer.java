package text_classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * @author jasbir
 *
 */

public class Trainer {

	/**
	 * Set of unique terms in the training data set
	 */
	private Set<String> uniqueVocab;
	/**
	 * File containing Training data
	 */
	private File trainDataFile;
	/**
	 * File containing Training labels
	 */
	private File trainLabelsFile;
	/**
	 * List of training labels
	 * Label for ith training example at ith index
	 */
	private List<String> labels;
	/**
	 * Map containing number of documents for each label
	 * (Label,Count)---- (key,value)
	 */
	private HashMap<String, Integer> numDocsPerLabel;
	/**
	 * A 2 dimensional hashmap
	 * First dimension contains (label,hashmap) pairs
	 * where the hashmap as value is the count of each term per label
	 */
	private HashMap<String, HashMap<String, Integer>> vocabRecord;
	/**
	 * A 2 dimensional hashmap
	 * First dimension contains(label,hashmap) pairs
	 * where the hashmap as value is the conditional probability of each term wrt label
	 * P(term=?/label=?) can be found using conditionalProb.get(label).get(term)
	 */
	private HashMap<String, HashMap<String, Double>> conditionalProb;
	
	/**
	 * priorProb represents P(C=?) 
	 * => Number of examples with label=? / total no. of examples
	 */
	private HashMap<String, Double> priorProb;

	public HashMap<String, HashMap<String, Double>> getConditionalProb() {
		return this.conditionalProb;
	}

	public HashMap<String, Double> getPriorProb() {
		return this.priorProb;
	}
	
	public Set<String> getUniqueVocab(){
		return this.uniqueVocab;
	}
	public Set<String> getUniqueLabels(){
		return this.vocabRecord.keySet();
	}

	Trainer(File traindata, File trainlabels) {
		this.trainDataFile = traindata;
		this.trainLabelsFile = trainlabels;
		this.labels = new ArrayList<String>();
		this.uniqueVocab = new HashSet<String>();
		this.numDocsPerLabel = new HashMap<String, Integer>();
		this.priorProb=new HashMap<String,Double>();
		this.vocabRecord = new HashMap<String, HashMap<String, Integer>>();
		this.conditionalProb = new HashMap<String, HashMap<String, Double>>();
	}

	public void train() {
		try {
			readLabels(trainLabelsFile);
			extractVocabulary(trainDataFile);
			docCountEachLabel();
			findCondProb();
			findPriorProb();
		} catch (FileNotFoundException e) {
			System.out.println("File reading error");
			e.printStackTrace();
		}
	}
	
	/**
	 * Finding prior  probabilites
	 * @link {@link Trainer#priorProb}
	 */
	private void findPriorProb() {
		Set<String> uniqueLabels=vocabRecord.keySet();
		for(String label:uniqueLabels) {
			priorProb.put(label,numDocsPerLabel.get(label)*1.0/ labels.size());
		}
	}

	
	/**
	 * Finding conditional probabilites
	 * @link {@link Trainer#conditionalProb}
	 */
	private void findCondProb() {
		conditionalProb.clear();
		long numOccurencesAllTermsGivenLabel = 0;
		long numOccurencesGivenTermGivenLabel=0;
		Set<String> uniqueLabels = vocabRecord.keySet();
		for (String label : uniqueLabels) {
			numOccurencesAllTermsGivenLabel = 0;
			for (long occurences : vocabRecord.get(label).values()) {
				numOccurencesAllTermsGivenLabel += occurences;
			}
			conditionalProb.put(label, new HashMap<String, Double>());
			for (String term : uniqueVocab) {
				if(vocabRecord.get(label).get(term)!=null)
					numOccurencesGivenTermGivenLabel=vocabRecord.get(label).get(term);
				else
					numOccurencesGivenTermGivenLabel=0;
				
				conditionalProb.get(label).put(term, (numOccurencesGivenTermGivenLabel + 1) * 1.0
						/ (uniqueVocab.size() + numOccurencesAllTermsGivenLabel));
			}
		}
	}

	
	/**
	 * Counting documents for each label
	 * @link{@link #numDocsPerLabel}
	 */
	private void docCountEachLabel() {
		numDocsPerLabel.clear();
		for (String label : labels) {
			if (numDocsPerLabel.containsKey(label)) {
				numDocsPerLabel.put(label, numDocsPerLabel.get(label) + 1);
			} else {
				numDocsPerLabel.put(label, 1);
			}
		}
	}

	/**
	 * @throws FileNotFoundException
	 * Generates {@link Trainer#vocabRecord} and @link {@link #uniqueVocab} data structures by reading the training data file
	 */
	private void extractVocabulary(File trainDataFile) throws FileNotFoundException {
		vocabRecord.clear();
		uniqueVocab.clear();
		Scanner sc = new Scanner(trainDataFile);
		String[] termsInExample;
		int i=0;
		while(sc.hasNextLine()) {
			termsInExample = sc.nextLine().split(" ");
			for (String term : termsInExample) {
				addTermToVocab(term, i);
			}
			i++;
		}
		sc.close();
	}

	/**
	 * Sub function for helping {@link Trainer#extractVocabulary(File)}
	 */
	private void addTermToVocab(String term, int exampleNo) {
		uniqueVocab.add(term);
		String label = labels.get(exampleNo);
		if(vocabRecord.containsKey(label)==false)
			vocabRecord.put(label, new HashMap<String,Integer>());
		if (vocabRecord.get(label).containsKey(term)) {
			vocabRecord.get(label).put(term, vocabRecord.get(label).get(term) + 1);
		} else {
			vocabRecord.get(label).put(term, 1);
		}
	}

	/**
	 * For reading train labels from {@link Trainer#trainLabelsFile} to {@link Trainer#labels}
	 */
	private void readLabels(File trainLabelsFile) throws FileNotFoundException {
		Scanner sc = new Scanner(trainLabelsFile);
		while (sc.hasNextLine()) {
			labels.add(sc.nextLine().trim());
		}
		sc.close();
	}
}
