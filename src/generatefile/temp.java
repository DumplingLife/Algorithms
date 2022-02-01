package generatefile;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class temp {
	public static void main(String[] args) throws IOException {
		BufferedWriter writer = new BufferedWriter(new PrintWriter("temp.in", "UTF-8"), 10000000);
		writer.write(10000 + "\n");
		for(int i = 0;i<10000;i++) {
			writer.write(random(0,7000) + " ");
		}
		writer.flush();
	}
	public static int random(int lower, int upper) {
		return (int) (Math.floor(Math.random() * (upper - lower + 1)) + lower);
	}
}
