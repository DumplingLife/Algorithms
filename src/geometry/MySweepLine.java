//see OneNote for explanation

package geometry;

import java.util.Arrays;

public class MySweepLine {
	static SparseSegmentTree st;
	static int ans = 0;
	public static void main(String[] args) {
		/*
		lines:
		horizontal:
		(1,1) to (5,1)
		(2,2) to (7,2)
		vertical:
		(3,0) to (3,6)
		(6,0) to (6,6)
		answer: 3		
		*/
		sweepLineArr = new SweepLineObj[] {
			new HorLeftPoint(1,1),
			new HorRightPoint(5,1),
			new HorLeftPoint(2,2),
			new HorRightPoint(7,2),
			new VerLine(3,0,6),
			new VerLine(6,0,6)
		};
		st = new SparseSegmentTree(20, 5);
		sweep();
		System.out.println(ans);
	}
	
	//Sweep Line (copy all this, including sort)
	//current: finds # intersections between some horizontal/vertical lines
	static SweepLineObj[] sweepLineArr;
	
	static interface SweepLineObj {
		int getTrigger();
		int getPriority(); //same trigger, higher priority goes first
		void doAction();
	}
	public static void sweep() {
		quickSort(sweepLineArr);
		//might change this to loop through all x values for small grids, see gold_2019_feb_p3 sweep()
		for(SweepLineObj ele : sweepLineArr) {
			ele.doAction();
		}
	}
	//probably put sweep line classes here (instead of under sort)
	public static void quickSort(SweepLineObj[] arr) {
		quickSortRecur(arr, 0, arr.length-1);
	}
	public static void quickSortRecur(SweepLineObj[] arr, int l, int r) {
		if(l >= r) return;
		
		int pivotIndex = (l+r)/2;
		int smallCounter = l;
		for(int i = l;i<=r;i++) {
			if(compare(arr[i], arr[pivotIndex]) == -1) {
				if(smallCounter == pivotIndex) pivotIndex = i;
				swap(arr, smallCounter, i);
				smallCounter++;
			}
		}
		swap(arr, smallCounter, pivotIndex);
		
		quickSortRecur(arr, l, smallCounter-1);
		quickSortRecur(arr, smallCounter+1, r);
	}
	public static void swap(SweepLineObj[] arr, int a, int b) {
		SweepLineObj temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	public static int compare(SweepLineObj a, SweepLineObj b) {
		if(a.getTrigger() < b.getTrigger()) return -1;
		else if(a.getTrigger() == b.getTrigger()) {
			if(a.getPriority() > b.getPriority()) return -1;
			else if(a.getPriority() == b.getPriority()) return 0;
			else return 1;
		}
		else return 1;
	}
	
	
	//stop copying
	//CUSTOMIZE
	static class HorLeftPoint implements SweepLineObj {
		public int x, y;
		public HorLeftPoint(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int getTrigger() {
			return x;
		}
		public int getPriority() {
			return 1;
		}
		public void doAction() {
			st.update(y, 1);
		}
	}
	static class HorRightPoint implements SweepLineObj {
		public int x, y;
		public HorRightPoint(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int getTrigger() {
			return x;
		}
		public int getPriority() {
			return -1;
		}
		public void doAction() {
			st.update(y, 0);
		}
	}
	static class VerLine implements SweepLineObj {
		public int x, y1, y2;
		public VerLine(int x, int y1, int y2) {
			this.x = x;
			this.y1 = y1;
			this.y2 = y2;
		}
		public int getTrigger() {
			return x;
		}
		public int getPriority() {
			return 0;
		}
		public void doAction() {
			ans += st.query(y1, y2);
		}
	}
	
	
	
	//sparse segment tree
	//not always needed (depends on problem)
	static class SparseSegmentTree {
		int[] data;
		int[] children;
		int size;
		int indexCounter = 1;
		final int defaultVal = 0;
		
		public SparseSegmentTree(int size, int numToAdd) {
			this.size = size;

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
				
				if(L <= inputIndex && inputIndex <= M) {
					if(c1 == -1) {
						children[2*index] = indexCounter;
						c1 = indexCounter;
						indexCounter++;
					}
					
					update(c1, L, M, inputIndex, val);
					c1Data = data[c1];
				}
				if(M+1 <= inputIndex && inputIndex <= R) {
					if(c2 == -1) {
						children[2*index+1] = indexCounter;
						c2 = indexCounter;
						indexCounter++;
					}
					
					update(c2, M+1, R, inputIndex, val);
					c2Data = data[c2];
				}
				data[index] = f(c1Data, c2Data);
			}
		}
		private int f(int a, int b) {
			return a+b;
		}
		private int log2ceil(int x) {
			if((x & (x - 1)) == 0)
				return 31 - Integer.numberOfLeadingZeros(x);
			else
				return 32 - Integer.numberOfLeadingZeros(x);
		}
	}
}
