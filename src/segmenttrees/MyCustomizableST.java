
package segmenttrees;

public class MyCustomizableST {
	public static void main(String[] args) {
		D[] dArr = new D[4];
		for(int i = 0;i<dArr.length;i++) {
			dArr[i] = new D();
		}
		dArr[0].val = new int[1];
		dArr[0].val[0] = 3;
		dArr[1].val = new int[1];
		dArr[1].val[0] = 1;
		dArr[2].val = new int[1];
		dArr[2].val[0] = 4;
		dArr[3].val = new int[1];
		dArr[3].val[0] = 5;
		
		SegmentTree st = new SegmentTree(dArr);
		Q q = new Q();
		q.x = 3;
		R x = st.query(0, 2, q);
		int temp = 0;
	}
	
	
	/*
	 * Currently set to # elements on range <= x
	 */
	static class D {
		int[] val;
	}
	static class U {
		//no updates, stuff in here shouldn't matter
	}
	static class Q {
		int x;
	}
	static class R {
		int val;
	}
	static class SegmentTree {
		public D merge(D c1, D c2) {
			D res = new D();
			res.val = new int[c1.val.length + c2.val.length];
			//merge sort merge
			int p1 = 0;
			int p2 = 0;
			while(p1 + p2 < c1.val.length + c2.val.length) {
				//if p1 is still in bounds (to prevent error) and (p2 out of bounds or less)
				if(p1 < c1.val.length && (p2 >= c2.val.length || c1.val[p1] < c2.val[p2])) {
					res.val[p1 + p2] = c1.val[p1];
					p1++;
				}
				if(p2 < c2.val.length && (p1 >= c1.val.length || c2.val[p2] < c1.val[p1])) {
					res.val[p1 + p2] = c2.val[p2];
					p2++;
				}
			}
			
			return res;
		}
		//updates not supported
		public D updateLeaf(D leafVal, U u) {
			System.out.println("Error: updateLeaf called");
			return new D();
		}
		public D updateMerge(D d, D c1, D c2, U u) {
			System.out.println("Error: updateMerge called");
			return new D();
		}

		public R queryFind(D d, Q q) {
			R res = new R();
			//binary search
			int l = -1;
			int r = d.val.length-1;
			while(l < r) {
				int m = (int) Math.ceil((l+r)/2.0);
				if(d.val[m] <= q.x) {
					l = m;
				}
				else r = m-1;
			}
			//plus 1 becuase l=2 represents 3 elements
			res.val = l+1;
			return res;
		}
		public R queryMerge(R r1, R r2) {
			R res = new R();
			//addition
			res.val = r1.val + r2.val;
			return res;
		}
		
		
		D[] t;
		int size;
		
		public SegmentTree(D[] arr) {
			t = new D[4*arr.length];
			size = arr.length;
			build(1, 0, arr.length-1, arr);
		}
		public R query(int l, int r, Q query) {
			return query(1, 0, size-1, l, r, query);
		}
		public void update(int index, U val) {
			update(1, 0, size-1, index, val);
		}
		
		private R query(int index, int L, int R, int l, int r, Q query) {
			if(l <= L && R <= r) return queryFind(t[index], query);
			
			int M = (L + R)/2;
			if(M+1 > r) return query(2*index, L, M, l, r, query);
			if(M < l) return query(2*index+1, M+1, R, l, r, query);
			return queryMerge(query(2*index, L, M, l, r, query),
					          query(2*index+1, M + 1, R, l, r, query));
		}
		
		private void update(int index, int L, int R, int inputIndex, U val) {
			if(inputIndex < L || R < inputIndex) return;
			if(L == R) t[index] = updateLeaf(t[index], val);
			else {
				int M = (L + R)/2;
				
				update(2*index, L, M, inputIndex, val);
				update(2*index+1, M+1, R, inputIndex, val);
				
				t[index] = updateMerge(t[index], t[2*index], t[2*index+1], val);
			}
		}
		
		private void build(int index, int L, int R, D[] arr) {
			if(L == R) t[index] = arr[L];
			else {
				int M = (L+R)/2;
				build(2*index, L, M, arr);
				build(2*index+1, M+1, R, arr);
				t[index] = merge(t[2*index], t[2*index+1]);
			}
		}
	}
}

