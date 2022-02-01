package segmenttrees.tests;

import java.util.Arrays;

public class BruteForceRQMin {
	long[] arr;
	public BruteForceRQMin(long[] arr) {
		this.arr = arr;
	}
	public BruteForceRQMin(int size) {
		arr = new long[size];
		Arrays.fill(arr, Long.MAX_VALUE);
	}
	public void updateRange(int L, int R, int x) {
		for(int i=L; i<=R; i++) {
			arr[i] = Math.min(arr[i], x);
		}
	}
	public long query(int L, int R) {
		long min = Long.MAX_VALUE;
		for(int i=L; i<=R; i++) {
			min = Math.min(min, arr[i]);
		}
		return min;
	}
}
