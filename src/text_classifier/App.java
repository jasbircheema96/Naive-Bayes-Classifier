package text_classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class App {
	
	static File trainData = new File("resources/traindata.txt");
	static File trainLabels=new File("resources/trainlabels.txt");
	static File testData=new File("resources/testdata.txt");
	static File testLabels=new File("resources/testlabels.txt");
	
	
	public static void main(String[] args) {	
		try {
			Trainer trainer=new Trainer(trainData,trainLabels);
			trainer.train();
			Classifier classifier=new Classifier(testData,testLabels,trainer);
			List<String> predictedLabels=classifier.classify();
			System.out.println("Classification accuracy - "+ classifier.getClassificationAccuracy());
			//printLabels(predictedLabels);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void printLabels(List<String> predictedLabels) {
		for(String label:predictedLabels) {
			System.out.println(label);
		}
	}

}
