package generateFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class GenerateFile {
	public static void main(String[] args) throws IOException {
		//enter config here
		final int minVal = -100000;
		final int maxVal = 100000;
		final int numberOfLines = 25000;

		BufferedWriter writer = new BufferedWriter(new PrintWriter("gold_2013_Nov_p2_25000.in", "UTF-8"), 10000000);
		
		//write the first line manually
		writer.write(numberOfLines + " 50000");
		writer.newLine();
		
		for(int i = 0;i<numberOfLines;i++) {
			writer.write(random(minVal, maxVal));
			writer.newLine();
		}
		writer.close();
	}
	public static String random(int lower, int upper) {
		int temp1 = (int) (Math.floor(Math.random() * (upper - lower + 1)) + lower);
		int temp2 = (int) (Math.floor(Math.random() * (upper - lower + 1)) + lower);
		if(1L*temp1*temp1 + 1L*temp2*temp2 < 1L*50000*50000) return random(lower, upper);
		else return temp1 + " " + temp2;
	}
}
