package segmenttrees_practice;

public class MyDynamicSTLPObjects {
	//57 min: correct
	public static void main(String[] args) {
		SegmentTree st = new SegmentTree(20);
		st.update(1, 2, 10);
		st.update(4, 5, 20);
		st.update(10, 12, 10);
		System.out.println(st.query(3, 13));
	}
	
	//min, lazy is replace
	public static class SegmentTree {
		final long defaultVal = Long.MAX_VALUE;
		final long defaultLazyVal = Long.MAX_VALUE;
		class Node {
			long val = defaultVal;
			long lazyVal = defaultLazyVal;
			long l = -1, r = -1;
			Node leftChild = null, rightChild = null;
			public Node(long l, long r) {
				this.l = l;
				this.r = r;
			}
			public String toString() {
				return val + " " + l + " " + r;
			}
		}
		Node root;
		public SegmentTree(long size) {
			root = new Node(0, size-1);
		}
		public long query(long L, long R) {
			return query(root, L, R);
		}
		private long query(Node u, long L, long R) {
			if(u.r < L || R < u.l) return defaultVal;
			if(L <= u.l && u.r <= R) return u.val;
			else {
				pushLazy(u);
				return Math.min(u.leftChild == null ? defaultVal : query(u.leftChild, L, R),
								u.rightChild == null ? defaultVal : query(u.rightChild, L, R));
			}
		}
		public void update(long L, long R, int x) {
			update(root, L, R, x);
		}
		private void update(Node u, long L, long R, int x) {
			if(u.r < L || R < u.l) return;
			if(L <= u.l && u.r <= R) {
				u.val = Math.min(u.val, x);
				u.lazyVal = Math.min(u.lazyVal, x);
			}
			else {
				pushLazy(u);
				update(u.leftChild, L, R, x);
				update(u.rightChild, L, R, x);
				u.val = Math.min(u.leftChild.val, u.rightChild.val);
			}
		}
		
		private void pushLazy(Node u) {
			if(u.leftChild == null) u.leftChild = new Node(u.l, (u.l+u.r)/2);
			if(u.rightChild == null) u.rightChild = new Node((u.l+u.r)/2+1, u.r);
			u.leftChild.lazyVal = Math.min(u.leftChild.lazyVal, u.lazyVal);
			u.leftChild.val = Math.min(u.leftChild.val, u.leftChild.lazyVal);
			u.rightChild.lazyVal = Math.min(u.rightChild.lazyVal, u.lazyVal);
			u.rightChild.val = Math.min(u.rightChild.val, u.rightChild.lazyVal);
			u.lazyVal = defaultLazyVal;
		}
	}
}
