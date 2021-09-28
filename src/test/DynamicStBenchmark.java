package test;

import java.util.Arrays;

public class DynamicStBenchmark
{
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		methodToTime();
		long endTime = System.nanoTime();

		long duration = (endTime - startTime)/1000000;
		System.out.println(duration + "ms");
		
	}
	public static void methodToTime() {
		final int size = 1000000;
		final int numToAdd = 10000;
		DynamicSegmentTree st = new DynamicSegmentTree(size, numToAdd);
		//SegmentTree st = new SegmentTree(new int[size]);
		for(int i = 0;i<numToAdd;i++) {
			st.update((int) (Math.random() * size), (int) (Math.random() * 1000000));
		}
		int sum = 0;
		for(int i = 0;i<1000000;i++) {
			int l = (int) (Math.random() * size);
			int r = (int) (Math.random() * size);
			if(l > r) {
				int temp = l;
				l = r;
				r = temp;
			}
			sum += st.query(l, r);
		}
		System.out.println(sum);
		System.out.println(queryOperationsCount);
	}
	static int queryOperationsCount = 0;
	
	static class DynamicSegmentTree {
		int[] data;
		int[] children;
		int size;
		int indexCounter = 1;
		//the value if the node does not exist
		final int defaultVal = 0;
		
		public DynamicSegmentTree(int size, int numToAdd) {
			this.size = size;

			//max # nodes, derived with summations; H and x are temp variables
			int H = log2ceil(size) + 1;
			int x = log2ceil(numToAdd);
			int maxNodes = (int) (Math.pow(2, x)) * (H-x+1) - 1;
			
			data = new int[maxNodes];
			data[0] = f(defaultVal, defaultVal);
			children = new int[2*maxNodes];
			Arrays.fill(children, -1);
		}
		public int query(int l, int r) {
			return query(0, 0, size-1, l, r);
		}
		public void update(int index, int val) {
			update(0, 0, size-1, index, val);
		}
		
		private int query(int index, int L, int R, int l, int r) {
			queryOperationsCount++;
			if(l <= L && R <= r) return data[index];
			
			int c1 = children[2*index];
			int c2 = children[2*index+1];

			int M = (L + R)/2;
			
			if(M+1 > r) return c1 == -1 ? defaultVal : query(c1, L, M, l, r);
			if(M < l) return c2 == -1 ? defaultVal : query(c2, M+1, R, l, r);
			return f(c1 == -1 ? defaultVal : query(c1, L, M, l, r),
					 c2 == -1 ? defaultVal : query(c2, M+1, R, l, r));
		}
		
		private void update(int index, int L, int R, int inputIndex, int val) {
			if(L == R) data[index] = val;
			else {
				int M = (L + R)/2;
				int c1 = children[2*index];
				int c2 = children[2*index+1];
				int c1Data = c1 == -1 ? defaultVal : data[c1];
				int c2Data = c2 == -1 ? defaultVal : data[c2];
				
				//if left in bounds
				if(L <= inputIndex && inputIndex <= M) {
					//create left if not created
					if(c1 == -1) {
						children[2*index] = indexCounter;
						c1 = indexCounter;
						indexCounter++;
					}
					
					update(c1, L, M, inputIndex, val);
					c1Data = data[c1];
				}
				//if right in bounds
				if(M+1 <= inputIndex && inputIndex <= R) {
					//create right if not created
					if(c2 == -1) {
						children[2*index+1] = indexCounter;
						c2 = indexCounter;
						indexCounter++;
					}
					
					update(c2, M+1, R, inputIndex, val);
					c2Data = data[c2];
				}
				
				//update is only called when this node is in bounds, so one child must 
				//be in bounds, so don't need to worry about both being defaultVal
				data[index] = f(c1Data, c2Data);
			}
		}
		
		//merge function
		//min(a,b) for min SegmentTree, a + b for sum SegmentTree
		private int f(int a, int b) {
			return a+b;
		}
		
		//util
		private int log2ceil(int x) {
			if((x & (x - 1)) == 0)
				return 31 - Integer.numberOfLeadingZeros(x);
			else
				return 32 - Integer.numberOfLeadingZeros(x);
		}
	}
	
	static class SegmentTree {
		int[] t;
		int size;
		
		public SegmentTree(int[] arr) {
			t = new int[4*arr.length];
			size = arr.length;
			build(1, 0, arr.length-1, arr);
		}
		public int query(int l, int r) {
			return query(1, 0, size-1, l, r);
		}
		public void update(int index, int val) {
			update(1, 0, size-1, index, val);
		}
		
		private int query(int index, int L, int R, int l, int r) {
			queryOperationsCount++;
			if(l <= L && R <= r) return t[index];
			
			int M = (L + R)/2;
			if(M+1 > r) return query(2*index, L, M, l, r);
			if(M < l) return query(2*index+1, M+1, R, l, r);
			return merge(query(2*index, L, M, l, r), query(2*index+1, M + 1, R, l, r));
		}
		
		private void update(int index, int L, int R, int inputIndex, int val) {
			if(inputIndex < L || R < inputIndex) return;
			if(L == R) t[index] = val;
			else {
				int M = (L + R)/2;
				
				update(2*index, L, M, inputIndex, val);
				update(2*index+1, M+1, R, inputIndex, val);
				
				t[index] = merge(t[2*index], t[2*index+1]);
			}
		}
		
		private void build(int index, int L, int R, int[] arr)
		{
			if(L == R) t[index] = arr[L];
			else {
				int M = (L+R)/2;
				build(2*index, L, M, arr);
				build(2*index+1, M+1, R, arr);
				t[index] = merge(t[2*index], t[2*index+1]);
			}
		}
		//merge function
		private int merge(int a, int b) {
			return a+b;
		}
	}
}
