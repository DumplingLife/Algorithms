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
		final int[] minVal = {1, 1, 0};
		final int[] maxVal = {2_250_000, 2_250_000, 1000};
		final int numberOfLines = 9_000_000;
		
		BufferedWriter writer = new BufferedWriter(new PrintWriter("graph2M.in", "UTF-8"), 10000000);
		for(int i = 0;i<numberOfLines;i++) {
			for(int j = 0;j<minVal.length;j++) {
				writer.write(random(minVal[j], maxVal[j]) + " ");
			}
			writer.newLine();
		}
		writer.close();
	}
	public static int random(int lower, int upper) {
		return (int) (Math.floor(Math.random() * (upper - lower + 1)) + lower);
	}
}
