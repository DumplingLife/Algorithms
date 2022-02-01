package segmenttrees_practice;

public class MyDynamicSTLPWalkObjects {
	//31 min
	public static void main(String[] args) {
		SegmentTree st = new SegmentTree(20);
		st.update(1, 2, 10);
		st.update(4, 5, 20);
		st.update(10, 12, 10);
		System.out.println(st.query(3, 9));
	}
	
	//min, lazy is replace
	static class SegmentTree {
		final long defaultVal = Long.MAX_VALUE;
		final long defaultLazyVal = Long.MAX_VALUE;
		class Node {
			long val = defaultVal;
			long lazyVal = defaultLazyVal;
			int l = -1, r = -1;
			Node leftChild = null, rightChild = null;
			public Node(int l, int r) {
				this.l = l;
				this.r = r;
			}
			public String toString() {
				return val + " " + l + " " + r;
			}
		}
		Node root;
		public SegmentTree(int size) {
			root = new Node(0, size-1);
		}
		public void add(int i, long x) {
			add(root, i, x);
		}
		private void add(Node u, int i, long x) {
			if(u.l == u.r) u.val = x;
			else {
				int m = (u.l+u.r)/2;
				if(i <= m) {
					u.leftChild = new Node(u.l, m);
					add(u.leftChild, i, x);
				}
				if(m+1 <= i) {
					u.rightChild = new Node(m+1, u.r);
					add(u.rightChild, i, x);
				}
				u.val = Math.min(u.leftChild == null ? defaultVal : u.leftChild.val, 
						         u.rightChild == null ? defaultVal : u.rightChild.val);
			}
		}
		public long query(int L, int R) {
			return query(root, L, R);
		}
		private long query(Node u, int L, int R) {
			if(u.r < L || R < u.l) return defaultVal;
			if(L <= u.l && u.r <= R) return u.val;
			else {
				return Math.min(u.leftChild == null ? defaultVal : query(u.leftChild, L, R),
								u.rightChild == null ? defaultVal : query(u.rightChild, L, R));
			}
		}
		public void update(int L, int R, int x) {
			update(root, L, R, x);
		}
		private void update(Node u, int L, int R, int x) {
			if(u.r < L || R < u.l) return;
			if(L <= u.l && u.r <= R) {
				u.val = Math.min(u.val, x);
				u.lazyVal = Math.min(u.lazyVal, x);
			}
			else {
				int m = (u.l+u.r)/2;
				u.leftChild = new Node(u.l, m);
				u.rightChild = new Node(m+1, u.r);
				update(u.leftChild, L, R, x);
				update(u.rightChild, L, R, x);
				u.val = Math.min(u.leftChild.val, u.rightChild.val);
			}
		}
	}
}
