package algorithms;

import java.util.Arrays;

public class MoAlgorithm {
	static int[] arr;
	static int K; //sqrt(N)
	public static void main(String[] args) {
		arr = new int[] {1,2,3,4,5,6};
		K = 3;
		
		MoQuery[] queries = {
				new MoQuery(4,4),
				new MoQuery(0,1),
				new MoQuery(1,3),
				new MoQuery(2,3),
				new MoQuery(0,5),
		};
		Arrays.sort(queries, (a, b) -> {
			int aBlock = a.L/K;
			int bBlock = b.L/K;
			if(aBlock == bBlock) return a.R - b.R;
			else return aBlock - bBlock;
		});
		
		
		Mo mo = new Mo(0);
		for(MoQuery query : queries) {
			//TODO: doing resets here could speed up by like 30% to remove r backtracks
			while(mo.L < query.L) mo.incL();
			while(query.L < mo.L) mo.decL();
			while(mo.R < query.R) mo.incR();
			while(query.R < mo.R) mo.decR();
			System.out.printf("moquery(%s,%s): %s\n", query.L, query.R, mo.val);
		}
	}
	
	static class Mo {
		long val;
		int L, R;
		public Mo(int L) {
			this.L = L;
			this.R = L-1;
			val = 0;
		}
		public void incR() {
			val += arr[R+1];
			R++;
		}
		public void decR() {
			val -= arr[R];
			R--;
		}
		public void incL() {
			val -= arr[L];
			L++;
		}
		public void decL() {
			val += arr[L-1];
			L--;
		}
	}
	
	static class MoQuery {
		int L, R;
		public MoQuery(int l, int r) {
			L = l;
			R = r;
		}
		@Override
		public String toString() {
			return "("+L+","+R+")";
		}
	}
}
