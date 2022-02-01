/*
 * good (same as regular prefix hasher)
 * verified: Algorithms/test/PrefixHasherComparison.java
 * 		10^5 length string, 10^5 queries, compared with original prefix hasher
 */

package string_diff;

public class MyPrefixHasherDiff {
	public static void main(String[] args) {
		PrefixHasherDiff prefixHasherDiff = new PrefixHasherDiff("abcd");
		System.out.println(prefixHasherDiff.hash(0, 1));
	}
	
	public static class PrefixHasherDiff {
		long[] prefixHashes;
		long[] pows;
		public PrefixHasherDiff(String s) {
			prefixHashes = new long[s.length()];
			prefixHashes[0] = s.charAt(0);
			for(int i=1; i<s.length(); i++) {
				prefixHashes[i] = 31*prefixHashes[i-1] + s.charAt(i);
			}
			
			pows = new long[s.length()+5];
			pows[0] = 1;
			for(int i=1; i<pows.length; i++) {
				pows[i] = pows[i-1]*31;
			}
		}
		public long hash(int l, int r) {
			return prefixHashes[r] - (l==0 ? 0 : prefixHashes[l-1] * pows[r-l+1]);
		}
	}
}
