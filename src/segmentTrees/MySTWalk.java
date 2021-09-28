package segmentTrees;

public class MySTWalk {
	public static void main(String[] args) {
		SegmentTree st = new SegmentTree(new int[] {1,4,3,2,6,4});
		System.out.println(st.walk(100));
	}
	
	static class SegmentTree {
		int[] t;
		int size;
		
		public SegmentTree(int[] arr) {
			t = new int[4*arr.length];
			size = arr.length;
			build(1, 0, arr.length-1, arr);
		}
		
		//find first i such that arr[i] >= x
		public int walk(int x) {
			return walk(1, 0, size-1, x);
		}
		private int walk(int index, int L, int R, int x) {
			//if leaf
			if(L == R) return L;
			
			int M = (L+R)/2;
			if(t[2*index+1] >= x) return walk(2*index+1, M, R+1, x);
			else return walk(2*index, L, M, x);
		}

		public int query(int l, int r) {
			return query(1, 0, size-1, l, r);
		}
		private int query(int index, int L, int R, int l, int r) {
			if(l <= L && R <= r) return t[index];
			
			int M = (L + R)/2;
			if(M+1 > r) return query(2*index, L, M, l, r);
			if(M < l) return query(2*index+1, M+1, R, l, r);
			return merge(query(2*index, L, M, l, r), query(2*index+1, M + 1, R, l, r));
		}

		public void update(int index, int val) {
			update(1, 0, size-1, index, val);
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
		private int merge(int a, int b) {
			return Math.max(a, b);
		}
	}
}
