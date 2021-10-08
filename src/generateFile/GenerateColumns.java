/*
 * for each column, specify a minVal and maxVal as an array
 */

package generateFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerateColumns
{
	
	public static void main(String[] args) throws IOException {
		//enter config here
		final int[] minVal = {-100000, -100000};
		final int[] maxVal = {100000, 100000};
		final int numberOfLines = 25000;

		BufferedWriter writer = new BufferedWriter(new PrintWriter("gold_2013_Nov_p2_25000.in", "UTF-8"), 10000000);
		
		//write the first line manually
		writer.write(numberOfLines + " 50000");
		writer.newLine();
		
		for(int i = 0;i<numberOfLines;i++) {
			for(int j = 0;j<minVal.length;j++) {
				writer.write(random(minVal[j], maxVal[j]) + " ");
			}
			writer.newLine();
		}
		writer.close();
	}
	//change the random method if there are more conditions
	public static int random(int lower, int upper) {
		return (int) (Math.floor(Math.random() * (upper - lower + 1)) + lower);
	}
}
