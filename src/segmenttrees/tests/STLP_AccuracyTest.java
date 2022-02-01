package segmenttrees.tests;

import segmenttrees_practice.MyDynamicSTLPObjects;

public class STLP_AccuracyTest {
	public static void main(String[] args) {
		final int N = 1000;
		final int M = 100;
		BruteForceRQMin BFRQ = new BruteForceRQMin(N);
		MyDynamicSTLPObjects.SegmentTree STLP = new MyDynamicSTLPObjects.SegmentTree(N);
		for(int i=0; i<M; i++) {
			int L = (int) (N*Math.random());
			int R = (int) (N*Math.random());
			if(L > R) {
				int temp = L;
				L = R;
				R = temp;
			}
			if(Math.random() < 0.5) {
				int x = (int) (1_000_000_000 * Math.random());
				BFRQ.updateRange(L, R, x);
				STLP.update(L, R, x);
				//System.out.println("update: " + L + " " + R + " " + x);
			}
			else {
				long res1 = BFRQ.query(L, R);
				long res2 = STLP.query(L, R);
				//System.out.println("query: " + L + " " + R);
				if(res1 != res2) {
					System.out.println(res1 + " " + res2);
					System.exit(2);
				}
			}
		}
	}
}
