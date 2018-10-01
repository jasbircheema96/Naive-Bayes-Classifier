package text_classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class App {
	//To change the test and train data
	static File trainData = new File(Constants.TRAIN_DATA_LOC);
	static File trainLabels=new File(Constants.TRAIN_LABELS_LOC);
	static File testData=new File(Constants.TEST_DATA_LOC);
	static File testLabels=new File(Constants.TEST_LABELS_LOC);

	public static void main(String[] args) {	
		try {
			Trainer trainer=new Trainer(trainData,trainLabels);
			trainer.train();
			
			Classifier classifier=new Classifier(testData,trainer);
			List<String> predictedLabels=classifier.classify();
		
			// the same data file must be passed to classifier and reportGenerator
			ReportGenerator reportGenerator=new ReportGenerator(trainData);
			reportGenerator.generateReport(testData, testLabels, predictedLabels);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
