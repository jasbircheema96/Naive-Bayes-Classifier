package text_classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ReportGenerator {
	FileWriter reportWriter;
	File trainData;
	ReportGenerator(File trainData) throws IOException{
		this.reportWriter=new FileWriter(Constants.REPORT_FILE_LOC);
		this.trainData=trainData;
	}
	
	public void generateReport(File testData,File testLabels,List<String> predictedLabels) throws FileNotFoundException, IOException {
		Scanner sc=new Scanner(testData);
		int lineCounter=0;
		reportWriter.write("Test data file used     ------ "+testData.getName()+"\n");
		reportWriter.write("Train data file  used   ------ "+trainData.getName()+"\n");
		reportWriter.write("num docs  in test data  ------ "+predictedLabels.size()+"\n");
		reportWriter.write("Classification Accuracy ------ "+this.getClassificationAccuracy(predictedLabels,testLabels)+"% \n");
		while(sc.hasNextLine()) {
			//reportWriter.write(sc.nextLine());
			sc.nextLine();
			reportWriter.write(predictedLabels.get(lineCounter)+"\n");
			lineCounter++;
		}
		sc.close();
		reportWriter.close();
	}
	
	private double getClassificationAccuracy(List<String> predictedLabels,File testLabels) throws FileNotFoundException {
		int correctClassified=0;
		int m=predictedLabels.size();
		Scanner sc=new Scanner(testLabels);
		for(int i=0;i<m;i++) {
			if(predictedLabels.get(i).equals(sc.nextLine().trim())) {
				correctClassified++;
			}
	
		}
		double accuracy=correctClassified*100.0/m;
		sc.close();
		return accuracy;
	}
}
