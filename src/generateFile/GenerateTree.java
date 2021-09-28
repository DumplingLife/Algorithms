package generateFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class GenerateTree {
	public static void main(String[] args) throws IOException {
		BufferedWriter writer = new BufferedWriter(new PrintWriter("tree5M.in", "UTF-8"), 10000000);
		final int N = 5000000;
		writer.write(N + "\n");
		
		for(int i=2; i<=N; i++) {
			//random number from 1 to i-1
			int rand = (int) (Math.random() * (i-1)) + 1;
			writer.write(rand + " " + i + "\n");
		}
		writer.flush();
		writer.close();
	}
}
