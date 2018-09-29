package text_classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
			Classifier classifier=new Classifier(testData,testLabels,trainer);
			List<String> predictedLabels=classifier.classify();
			writeToFile(predictedLabels,classifier);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToFile(List<String> predictedLabels,Classifier classifier) throws IOException {
		FileWriter fw=new FileWriter("resources/predictedLabels.txt");
		for(String predictedLabel:predictedLabels) {
			fw.write(predictedLabel+"\n");
		}
		fw.write("Classification Accuracy   ----  "+classifier.getClassificationAccuracy()   );
		fw.close();
	}

}
