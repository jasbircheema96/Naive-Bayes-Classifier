package text_classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
			
			Classifier classifier=new Classifier(testData,trainer);
			List<String> predictedLabels=classifier.classify();
		
			// the file passed as testData must be same for both classifier and reportGenerator
			// the corresponding labels must be used for each file
			ReportGenerator reportGenerator=new ReportGenerator();
			reportGenerator.generateReport(testData, testLabels, predictedLabels);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
