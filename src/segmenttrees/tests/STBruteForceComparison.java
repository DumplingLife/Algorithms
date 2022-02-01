/*
 * Compare ST with brute force to find when to switch to brute force
 */

package segmenttrees.tests;

import test.MyTimer;
import segmenttrees.MySegmentTree.SegmentTree;

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
}
