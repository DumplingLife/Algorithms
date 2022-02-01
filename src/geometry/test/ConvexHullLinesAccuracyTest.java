package geometry.test;

import java.util.Arrays;

import geometry.MyConvexHullLines.ConvexHull;

public class ConvexHullLinesAccuracyTest {
	public static void main(String[] args) {
		final int N = 500;
		final int M = 500;
		final int K = 1_000_000_000;
		final int reps = 100;
		for(int rep=0; rep<reps; rep++) {
			Pair[] pairs = new Pair[N];
			for(int i=0; i<N; i++) {
				pairs[i] = new Pair((int) (K * Math.random()), (int) (K * Math.random()));
			}
			Arrays.sort(pairs, (a,b) -> Long.compare(b.a, a.a));
			
			ConvexHull convexHull = new ConvexHull();
			for(int i=0; i<pairs.length; i++) {
				convexHull.addLine(pairs[i].a, pairs[i].b);
			}
			
			for(int i=0; i<M; i++) {
				int q = (int) (K * Math.random());
				long res1 = convexHull.query(q);
				long res2 = Long.MAX_VALUE;
				for(Pair pair : pairs) {
					res2 = Math.min(res2, pair.a * q + pair.b);
				}
				if(res1 != res2) {
					System.out.println(res1 + " " + res2 + " " + (res1 - res2));
				}
			}
		}
	}
	
	static class Pair {
		long a, b;
		public Pair(long a, long b) {
			this.a = a;
			this.b = b;
		}
		@Override
		public String toString() {
			return a + " " + b;
		}
	}
}
