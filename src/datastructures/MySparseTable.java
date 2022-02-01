package datastructures;

public class MySparseTable {
	public static void main(String[] args) {
		SparseTable sparseTable = new SparseTable(5);
		sparseTable.build(new int[] {0, 3, 2, 10, 1, 4});
		System.out.println(sparseTable.query(2, 4));
	}
	static class SparseTable {
		int[][] table;
		public SparseTable(int size) {
			table = new int[log2floor(size)+1][size];
		}
		public void build(int[] arr) {
			//i = 0
			for(int j = 0;j<table[0].length;j++) {
				table[0][j] = arr[j];
			}
			for(int i = 1;i<table.length;i++) {
				int intervalLen = 1 << i; //2 ^ i
				for(int j = 0;j<table[i].length - intervalLen;j++) {
					table[i][j] = f(table[i-1][j], table[i-1][j + intervalLen/2]);
				}
			}
		}
		public int query(int l, int r) {
			int intervalLen = r-l+1;
			int i = log2floor(intervalLen);
			return f(table[i][l], table[i][r - intervalLen + 1]);
		}
		public static int log2floor(int x) {
			return 31 - Integer.numberOfLeadingZeros(x);
		}
		
		public int f(int a, int b) {
			return Math.min(a, b);
		}
		
	}
}
