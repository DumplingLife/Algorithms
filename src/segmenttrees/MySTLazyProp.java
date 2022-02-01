
package segmenttrees;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MySTLazyProp
{
	static class MyReader 
    { 
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
	public static void main(String[] args) throws IOException
	{
		int[] arr = new int[100000];
		//array
		MyReader reader1 = new MyReader(new File("002_1_3_Z9.in"));
		//queries/updates
		MyReader reader2 = new MyReader(new File("003_4_3_Z0_Z3-_Z3-_Z9.in"));
		for(int i = 0;i<1000;i++) {
			arr[i] = reader1.nextInt();
		}
		SegmentTreeLazyProp st = new SegmentTreeLazyProp(arr);
		

		BufferedWriter writer = new BufferedWriter(new PrintWriter("001.out", "UTF-8"), 100000);
		for(int i = 0;i<1000;i++) {
			int queryId = reader2.nextInt();
			//make sure input is ordered
			int a = reader2.nextInt();
			int b = reader2.nextInt();
			int c = reader2.nextInt();
			if(a > b) {
				int aTemp = a;
				a = b;
				b = aTemp;
			}
			//query
			if(queryId == 0) {
				writer.write(st.query(a, b) + " ");
				writer.newLine();
			}
			//update
			if(queryId == 1) {
				st.update(a,b,c);
			}
		}
		writer.close();
	}
	
	
	public static class SegmentTreeLazyProp {
		int[] t;
		boolean[] lazyValExists;
		int[] lazyVals;
		int treeSize;
		
		public SegmentTreeLazyProp(int[] arr) {
			treeSize = arr.length;
			t = new int[4*treeSize];
			lazyValExists = new boolean[4*treeSize];
			lazyVals = new int[4*treeSize];
			build(arr, 1, 0, arr.length-1);
		}
		public int query(int l, int r) {
			return query(1, 0, treeSize-1, l, r);
		}
		public void update(int l, int r, int val) {
			update(1, 0, treeSize-1, l, r, val);
		}
		
		
		private int query(int index, int L, int R, int l, int r) {
			updateAndPush(index, L, R);
			
			if(l <= L && R <= r) return t[index];
			
			int M = (L + R)/2;
			if(M+1 > r) return query(2*index, L, M, l, r);
			if(M < l) return query(2*index+1, M + 1, R, l, r);
			return f(query(2*index, L, M, l, r), query(2*index+1, M + 1, R, l, r));
		}
		
		//update decomposition of l...r with lazy propagation
		private void update(int index, int L, int R, int l, int r, int val) {
			//if node is completely out of bounds, return
			if(R < l || r < L) return;
			
			//if the node is completely in bounds, update lazy values
			if(l <= L && R <= r) {
				//either update the value using g or set it
				if(lazyValExists[index]) lazyVals[index] = calculateNewLazyVal(lazyVals[index], val);
				else lazyVals[index] = val;
				
				lazyValExists[index] = true;
			}
			//else (the node overlaps)
			//push updates down, update both children, change this node's value
			else {	
				updateAndPush(index, L, R);
				int M = (L + R)/2;
				update(2*index, L, M, l, r, val);
				update(2*index+1, M+1, R, l, r, val);
				
				t[index] = f(calculateVal(2*index, L, M), calculateVal(2*index+1, M+1, R));
			}
		}
		
		private void build(int[] arr, int index, int l, int r)
		{
			if(l == r) t[index] = arr[l];
			else {
				int m = (l+r)/2;
				build(arr, 2*index, l, m);
				build(arr, 2*index+1, m+1, r);
				t[index] = f(t[2*index], t[2*index+1]);
			}
		}
		
		//update node and push updates
		private void updateAndPush(int index, int L, int R) {
			if(!lazyValExists[index]) return;

			t[index] = h(t[index], lazyVals[index], R-L+1);
			
			lazyValExists[index] = false;
			
			updateSingleUpdateVals(2*index, index);
			updateSingleUpdateVals(2*index+1, index);
		}
		//calculate (but don't change) a node's value
		//basically just use h unless lazyValExists is false
		private int calculateVal(int index, int L, int R) {
			return lazyValExists[index] ? 
					h(t[index], lazyVals[index], R-L+1) : t[index];
		}
		
		//calculate update value correctly by ignoring updateVal if updateNeeded is
		//false and change updateNeeded to true
		//assume updateNeeded[parent] = true
		private void updateSingleUpdateVals(int index, int parent) {
			if(index < t.length) {
				lazyVals[index] = lazyValExists[index] ? 
						calculateNewLazyVal(lazyVals[index], lazyVals[parent]) : lazyVals[parent];
				lazyValExists[index] = true;
			}
			
		}
		
		//customize
		//merge function
		private int f(int a, int b) {
			return Math.min(a, b);
			//return a+b;
		}
		//function that describes how lazy value should be updated
		//Assume both values exist, old value is a and new value is b
		private int calculateNewLazyVal(int old, int update) {
			//update is replace
			return update;
			
			//update is add
			//return old + update;
		}
		//function that describes what happens to val if all nodes are updated by lazyVal
		private int h(int oldVal, int lazyVal, int rangeLen) {
			//f = min, update is replace
			return lazyVal; //if nodes become updateVal, min is also updateVal
			
			//f = sum, lazy value = change of every node in range
			//return oldVal + lazyVal*rangeLen;
		}
		
		//util
		public void println() {
			System.out.println(Arrays.toString(t));
		}
	}
}

