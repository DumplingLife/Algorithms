package generateFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class GenerateFile {
	public static void main(String[] args) throws IOException {
		PrintWriter writer = new PrintWriter("test.in", "UTF-8");
		
		final int N = 1000000;
		for(int i = 0;i<N;i++) {
			writer.write((char) (Math.random()*26 + 97));
		}
		
		writer.flush();
	}
}
