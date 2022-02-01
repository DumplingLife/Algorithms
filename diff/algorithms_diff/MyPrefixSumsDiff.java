/*
 * not tested, but seems the same as original
 */

package algorithms_diff;

public class MyPrefixSumsDiff {
	public static void main(String[] args) {
		PrefixSums prefixSums = new PrefixSums(new int[] {1,2,3,4});
		System.out.println(prefixSums.query(1,3));
	}
	
	static class PrefixSums {
		int[] prefixSums;
		public PrefixSums(int[] arr) {
			prefixSums = new int[arr.length];
			prefixSums[0] = arr[0];
			for(int i=1; i<arr.length; i++) {
				prefixSums[i] = prefixSums[i-1] + arr[i];
			}
		}
		public int query(int l, int r) {
			return prefixSums[r] - (l==0 ? 0 : prefixSums[l-1]);
		}
	}
}
