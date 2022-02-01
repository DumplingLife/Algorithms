/*
 * for each column, specify a minVal and maxVal as an array
 */

package generatefile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerateColumns
{
	
	public static void main(String[] args) throws IOException {
		//enter config here
		final int[] minVal = {0, 0, 0};
		final int[] maxVal = {1, 1000000, 1000000};
		final int numberOfLines = 1000000;

		BufferedWriter writer = new BufferedWriter(new PrintWriter("stTest.in", "UTF-8"), 10000000);
		
		//write the first line manually
		writer.write(numberOfLines);
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
