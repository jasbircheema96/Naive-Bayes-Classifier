package text_classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ReportGenerator {
	FileWriter reportWriter;
	ReportGenerator() throws IOException{
		this.reportWriter=new FileWriter("resources/results.txt");
	}
	
	public void generateReport(File testData,File testLabels,List<String> predictedLabels) throws FileNotFoundException, IOException {
		reportWriter.write("File name --- "+testData.getName()+" predictions .........");
		Scanner sc=new Scanner(testData);
		int lineCounter=0;
		while(sc.hasNextLine()) {
			//reportWriter.write(sc.nextLine());
			sc.nextLine();
			reportWriter.write(predictedLabels.get(lineCounter)+"\n");
			lineCounter++;
		}
		sc.close();
		reportWriter.write("Classification Accuracy   ----  "+this.getClassificationAccuracy(predictedLabels,testLabels));
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
		double accuracy=correctClassified*1.0/m;
		sc.close();
		return accuracy;
	}
}
