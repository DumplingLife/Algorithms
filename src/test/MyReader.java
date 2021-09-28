package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MyReader { 
    BufferedReader br; 
    StringTokenizer st; 
    public MyReader(File file) throws FileNotFoundException  { 
        br = new BufferedReader(new InputStreamReader(new FileInputStream(file))); 
    } 
    public String next() throws IOException  { 
    	while (st == null || !st.hasMoreElements()) { 
        	st = new StringTokenizer(br.readLine());
    	} 
    	return st.nextToken(); 
    } 
    public int nextInt() throws NumberFormatException, IOException { 
    	return Integer.parseInt(next()); 
    }
    public long nextLong() throws NumberFormatException, IOException { 
    	return Long.parseLong(next()); 
    }
    public double nextDouble() throws NumberFormatException, IOException { 
        return Double.parseDouble(next()); 
    }
    public String nextLine() throws IOException {
        return br.readLine(); 
    } 
}
