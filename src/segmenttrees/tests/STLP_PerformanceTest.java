package segmenttrees.tests;

import java.util.Arrays;

import segmenttrees.MySTLazyProp;
import segmenttrees_practice.MyDynamicSTLPObjects;
import test.MyTimer;

public class STLP_PerformanceTest {
	public static void main(String[] args) {
		//10^16: 50674
		//10^15: 47006
		//10^14: 32025
		//10^13: 23931
		//10^12: 26617
		//10^11: 21729
		//10^10: 16224
		//10^9 : 11451
		//10^8 :  9902
		//10^7 :  4693
		//10^6 :  3848
		//10^5 :  2453
		final int reps = 10;
		final long N = 1_000_000_000_000_000L;
		final int M = 100_000;
		MyTimer timer = new MyTimer();
		int sum = 0;
		for(int rep=0; rep<reps; rep++) {
			/*
			int[] arr = new int[N];
			Arrays.fill(arr, Integer.MAX_VALUE);
			MySTLazyProp.SegmentTreeLazyProp st = new MySTLazyProp.SegmentTreeLazyProp(arr);
			*/
			MyDynamicSTLPObjects.SegmentTree st = new MyDynamicSTLPObjects.SegmentTree(N);
			for(int i=0; i<M; i++) {
				long L = (long) (N*Math.random());
				long R = (long) (N*Math.random());
				if(L > R) {
					long temp = L;
					L = R;
					R = temp;
				}
				if(Math.random() < 0.5) {
					st.update(L, R, (int) (1_000_000 * Math.random()));
				}
				else {
					sum += st.query(L, R);
				}
			}
			//System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		}
		System.out.println(sum);
		timer.lap(reps + " reps: ");
	}
}
