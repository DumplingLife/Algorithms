package segmentTrees;

import java.util.Arrays;

public class MyDynamicST
{
	public static void main(String[] args) {
		DynamicSegmentTree st = new DynamicSegmentTree(10, 3);
		st.update(0, 10);
		st.update(2, 11);
		st.update(3, 12);
		int x = st.query(0, 2);
		int y = st.query(4, 9);
		System.out.println(x + " " + y);
	}
	
	/*
	 * Customize:
	 * f: main function
	 * defaultVal: the default value if there is no element found
	 * 		f(defaultVal, x) should equal x
	 */
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
			if(l <= L && R <= r) return data[index];
			
			int c1 = children[2*index];
			int c2 = children[2*index+1];

			int M = (L + R)/2;

			if(M+1 > r) return c1 == -1 ? defaultVal : query(c1, L, M, l, r); //return val1
			if(M < l) return c2 == -1 ? defaultVal : query(c2, M+1, R, l, r); //return val2
			//return f(val1, val2)
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
}

