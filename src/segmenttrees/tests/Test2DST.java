package segmenttrees.tests;

import segmenttrees.My2DST.SegmentTree;
import test.MyTimer;

public class Test2DST
{
	public static void main(String[] args) {
		int sum = 0;
		
		int[][] arr = new int[5000][5000];
		for(int i = 0;i<arr.length;i++) {
			for(int j = 0;j<arr[i].length;j++) {
				arr[i][j] = (int) (Math.random() * 100);
			}
		}
		
		MyTimer timer = new MyTimer();
		SegmentTree st = new SegmentTree(arr);
		for(int q=0; q<100000; q++) {
			if(Math.random() > 0.5) {
				//update
				int x = (int) (Math.random() * 100);
				int y = (int) (Math.random() * 100);
				int val = (int) (Math.random() * 100);
				st.update(x, y, val);
			}
			else {
				//query
				int lx = (int) (Math.random() * 100);
				int rx = (int) (Math.random() * 100);
				int ly = (int) (Math.random() * 100);
				int ry = (int) (Math.random() * 100);
				if(lx > rx) {
					int temp = lx;
					lx = rx;
					rx = temp;
				}
				if(ly > ry) {
					int temp = ly;
					ly = ry;
					ry = temp;
				}
				sum += st.query(lx, rx, ly, ry);
			}
		}
		timer.lap();
		
		System.out.println(sum);
	}
	
	static class BruteForce2D {
		int[][] arr;
		public BruteForce2D(int[][] arr) {
			this.arr = arr;
		}
		public int query(int xl, int xr, int yl, int yr) {
			int ret = 0;
			for(int x=xl; x<=xr; x++) {
				for(int y=yl; y<=yr; y++) {
					ret += arr[x][y];
				}
			}
			return ret;
		}
	}
}
