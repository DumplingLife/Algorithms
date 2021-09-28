/*
 * Compare ST with brute force to find when to switch to brute force
 */

package test;

public class STBruteForceComparison
{
	public static void main(String[] args) {
		int len = 160;
		int[] arr = new int[len];
		for(int i = 0;i<len;i++) {
			arr[i] = (int) (Math.random() * 100);
		}
		
		int Q = 20;
		int[] queryL = new int[Q];
		int[] queryR = new int[Q];
		for(int i = 0;i<Q;i++) {
			int l = (int) (Math.random() * len);
			int r = (int) (Math.random() * len);
			if(l > r) {
				int temp = l;
				l = r;
				r = temp;
			}
			queryL[i] = l;
			queryR[i] = r;
		}
		
		//make sure compiler doesn't optimize
		int sum = 0;
		
		MyTimer timer = new MyTimer();
		int reps = 100000;
		
		System.out.println("Empty loop");
		for(int repCounter = 0;repCounter < reps;repCounter++) {
			for(int i = 0;i<Q;i++) {
				sum += queryL[i] + queryR[i];
			}
		}
		timer.lap();
		
		System.out.println("Segment Tree");
		for(int repCounter = 0;repCounter < reps;repCounter++) {
			SegmentTree st = new SegmentTree(arr);
			for(int i = 0;i<Q;i++) {
				sum += st.query(queryL[i], queryR[i]);
			}
		}
		timer.lap();

		System.out.println("Brute Force");
		for(int repCounter = 0;repCounter < reps;repCounter++) {
			BruteForceRQ rq = new BruteForceRQ(arr);
			for(int i = 0;i<Q;i++) {
				sum += rq.query(queryL[i], queryR[i]);
			}
		}
		timer.lap();
		
		System.out.println(sum);
	}
	
	
	static class BruteForceRQ {
		int[] arr;
		public BruteForceRQ(int[] arr) {
			this.arr = arr;
		}
		public int query(int l, int r) {
			int ret = 0;
			for(int ele : arr) {
				ret += ele;
			}
			return ret;
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
