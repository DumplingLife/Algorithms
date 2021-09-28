/*
 * This is more of a class for convenience
 * 
 * Customizable st, but
 *    Q is empty (and omitted in the code)
 *    U and R are both the same as D
 *    updateMerge and queryMerge are both the same as merge
 *    queryFind just returns the leaf
 *    
 * 
 * Currently set to find min and index of min
 */
package segmentTrees;

public class MyDOnlyCustomizableST {
	
	
	static class D {
		int val;
		int ind;
	}
	static class SegmentTree {
		public D merge(D c1, D c2) {
			D res = new D();
			
			if(c1.val < c2.val) {
				res.val = c1.val;
				res.ind = c1.ind;
			}
			else {
				res.val = c2.val;
				res.ind = c2.ind;
			}
			
			return res;
		}
		
		D[] t;
		int size;
		
		public SegmentTree(D[] arr) {
			t = new D[4*arr.length];
			size = arr.length;
			build(1, 0, arr.length-1, arr);
		}
		public D query(int l, int r) {
			return query(1, 0, size-1, l, r);
		}
		public void update(int index, D val) {
			update(1, 0, size-1, index, val);
		}
		
		private D query(int index, int L, int R, int l, int r) {
			if(l <= L && R <= r) return t[index];
			
			int M = (L + R)/2;
			if(M+1 > r) return query(2*index, L, M, l, r);
			if(M < l) return query(2*index+1, M+1, R, l, r);
			return merge(query(2*index, L, M, l, r),
					     query(2*index+1, M + 1, R, l, r));
		}
		
		private void update(int index, int L, int R, int inputIndex, D val) {
			if(inputIndex < L || R < inputIndex) return;
			if(L == R) t[index] = val;
			else {
				int M = (L + R)/2;
				
				update(2*index, L, M, inputIndex, val);
				update(2*index+1, M+1, R, inputIndex, val);
				
				t[index] = merge(t[2*index], t[2*index+1]);
			}
		}
		
		private void build(int index, int L, int R, D[] arr)
		{
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
