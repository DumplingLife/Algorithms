package test;

import java.util.Arrays;

public class STComparison {
	public static void main(String[] args) {
		int[] arr = new int[10000];
		Arrays.fill(arr, 0);
		SegmentTree st = new SegmentTree(arr);
		SparseSegmentTree sparseSt = new SparseSegmentTree(10000, 1000);
		
		int disagreeCount = 0;
		int queryCount = 0;
		for(int i = 0;i<1000;i++) {
			if(Math.random() < 0.5) {
				int ind = (int) (Math.random() * 10000);
				int val = (int) (Math.random() * 1000);
				//System.out.println("update: " + ind + " " + val);
				st.update(ind, val);
				sparseSt.update(ind, val);
			}
			else {
				queryCount++;
				int l = (int) (Math.random() * 10000);
				int r = (int) (Math.random() * 10000);
				if(l > r) {
					int temp = l;
					l = r;
					r = temp;					
				}
				//System.out.println("query: " + l + " " + r);
				
				int stRes = st.query(l, r);
				int sparseStRes = sparseSt.query(l, r);
				if(stRes != sparseStRes) {
					System.out.println("disagree " + stRes + " " + sparseStRes);
					disagreeCount++;
				}
			}
		}
		System.out.println("total queries: " + queryCount);
		System.out.println("disagree: " + disagreeCount);
	}
	
	static class SparseSegmentTree {
		int[] data;
		int[] children;
		int size;
		int indexCounter = 1;
		//the value if the node does not exist
		final int defaultVal = 0;
		
		public SparseSegmentTree(int size, int numToAdd) {
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
			if(l <= L && R <= r) return data[index];
			
			int c1 = children[2*index];
			int c2 = children[2*index+1];

			int M = (L + R)/2;
			int val1 = c1 == -1 ? defaultVal : query(c1, L, M, l, r);
			int val2 = c2 == -1 ? defaultVal : query(c2, M+1, R, l, r);
			
			if(M+1 > r) return val1;
			if(M < l) return val2;
			return f(val1, val2);
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
