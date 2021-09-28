package dp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import test.MyTimer;

public class Knapsack {
	public static void main(String[] args) throws IOException {		
		File file = new File("knapsack.in"); 
		MyReader reader = new MyReader(file);
		int N = reader.nextInt();
		int W = reader.nextInt();
		Item[] items = new Item[N];
		for(int i = 0;i<N;i++) {
			items[i] = new Item(reader.nextInt(), reader.nextInt());
		}
		
		MyTimer timer = new MyTimer();
		
		//2D version, slower
		/*
		int[][] d = new int[N+1][W+1];
		for(int w=1; w<W+1; w++) {
			d[0][w] = Integer.MIN_VALUE;
		}
		for(int i=1; i<N+1; i++) {
			for(int w=0; w<W+1; w++) {
				int temp = w-items[i-1].w >= 0 ? items[i-1].v + d[i-1][w-items[i-1].w] : Integer.MIN_VALUE;
				d[i][w] = Math.max(temp, d[i-1][w]);
			}
		}
		*/
		
		//1D optimized version, 2x faster
		int[] prevD = new int[W+1];
		for(int w=1; w<W+1; w++) {
			prevD[w] = Integer.MIN_VALUE;
		}
		for(int i=1; i<N+1; i++) {
			int[] newD = new int[W+1];
			for(int w=0; w<W+1; w++) {
				int temp = w-items[i-1].w >= 0 ? items[i-1].v + prevD[w-items[i-1].w] : Integer.MIN_VALUE;
				newD[w] = Math.max(temp, prevD[w]);
			}
			prevD = newD;
		}
		
		timer.lap();
		
		System.out.println(prevD[W]);
	}
	
	
	static class Item {
		int w;
		int v;
		public Item(int w, int v) {
			this.w = w;
			this.v = v;
		}
	}
	
	
	static class MyReader { 
        BufferedReader br; 
        StringTokenizer st; 
        public MyReader(File file) throws FileNotFoundException  { 
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file))); 
        } 
        String next() throws IOException  { 
        	while (st == null || !st.hasMoreElements()) { 
            	st = new StringTokenizer(br.readLine());
        	} 
        	return st.nextToken(); 
        } 
        int nextInt() throws NumberFormatException, IOException { 
        	return Integer.parseInt(next()); 
        }
        long nextLong() throws NumberFormatException, IOException { 
        	return Long.parseLong(next()); 
        }
        double nextDouble() throws NumberFormatException, IOException { 
            return Double.parseDouble(next()); 
        }
        String nextLine() throws IOException {
            return br.readLine(); 
        } 
    }
}
