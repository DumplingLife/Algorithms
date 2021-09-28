package string;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import string.MyPrefixHasher.PrefixHasher;
import string.MySuffixArray.Triple;
import test.MyTimer;

public class MySuffixArrayPrefixHasher
{
	public static void main(String[] args) throws FileNotFoundException {
		MyTimer timer = new MyTimer();
		Scanner scan = new Scanner(new File("test.in"));
		//SuffixArrayPrefixHasher sa = new SuffixArrayPrefixHasher(scan.nextLine());
		SuffixArrayPrefixHasher sa = new SuffixArrayPrefixHasher("acab");
		System.out.println(Arrays.toString(sa.suffixArr));
		
		String substring = "acab";
		System.out.println(sa.find(substring));
		System.out.println(sa.s.contains(substring));
		timer.lap();
	}
	
	
	
	static class SuffixArrayPrefixHasher {
		public String s;
		public int N;
		public int[] suffixArr;
		public int[] rank;
		
		PrefixHasher hasher;
		
		public SuffixArrayPrefixHasher(String s) {
			this.s = s;
			N = s.length();
			suffixArr = findSuffixArr();
			
			//build rank by inversing suffixArr
			rank = new int[N];
			for(int i = 0;i<N;i++) {
				rank[suffixArr[i]] = i;
			}
			
			hasher = new PrefixHasher(s);
		}

		//BUILD
		//main build method
		public int[] findSuffixArr() {
			//create new temp string we will use in build only
			String str = s + "$";
			//L = N+1
			int L = str.length();
			int[] prevRankings = new int[L];
			//base case: l = 1
			{
				int[] aArr = new int[L];
				int[] bArr = new int[L];
				for(int i = 0;i<L;i++) {
					aArr[i] = str.charAt(i);
					//bArr[i] = 0, don't need to write this
				}
				prevRankings = getRankings(aArr, bArr);
			}
			//main loop
			for(int l = 2; l<L; l *= 2) {
				int[] aArr = new int[L];
				int[] bArr = new int[L];
				for(int i = 0; i<L; i++) {
					aArr[i] = prevRankings[i];
					bArr[i] = prevRankings[(i + l/2)%L];
				}
				prevRankings = getRankings(aArr, bArr);
			}
			//convert rankings into what we want (invert). Rankings should be unique now.
			//Go to N, not L, to skip the last suffix, which is "$"
			int[] ret = new int[N];
			for(int i = 0;i<N;i++) {
				//-1 here because all rankings are 1 higher, since "$.." places in first
				ret[prevRankings[i]-1] = i;
			}
			return ret;
		}
		
		//helper used in findSuffixArr
		private int[] getRankings(int[] aArr, int[] bArr) {
			int L = aArr.length;
			
			int[] originalIndArr = new int[L];
			for(int i = 0;i<L;i++) {
				originalIndArr[i] = i;
			}
			
			//radix sort
			//sort by bArr, then copy the results to the local variables
			Triple triple1 = sort(bArr, aArr, originalIndArr);
			bArr = triple1.val1;
			aArr = triple1.val2;
			originalIndArr = triple1.val3;
			
			//sort by aArr, then copy the results to the local variables
			Triple triple2 = sort(aArr, bArr, originalIndArr);
			aArr = triple2.val1;
			bArr = triple2.val2;
			originalIndArr = triple2.val3;
			
			//calculate rankings
			int[] rankings = new int[L];
			for(int i = 0;i<L;i++) {
				rankings[originalIndArr[i]] = i;
				//get ties
				if(i > 0 && aArr[i] == aArr[i-1] && bArr[i] == bArr[i-1]) {
					rankings[originalIndArr[i]] = rankings[originalIndArr[i-1]];
				}
			}
			
			
			return rankings;
		}
		
		//sort by sortBy, move elements around in all 3 arrays
		private Triple sort(int[] sortBy, int[] arr1, int[] arr2) {
			int L = sortBy.length;
			int[] newSortBy = new int[L];
			int[] newArr1 = new int[L];
			int[] newArr2 = new int[L];
			//if it's the first iteration, rankings will be ascii codes, not actual rankings, so do this
			int[] cumulativeCount = new int[Math.max(123, L)];
			for(int ele : sortBy) {
				cumulativeCount[ele]++;
			}
			for(int i = 1;i<cumulativeCount.length;i++) {
				cumulativeCount[i] += cumulativeCount[i-1];
			}
			for(int i = L-1; i>=0; i--) {
				int ele = sortBy[i];
				int cumulativeCountTemp = cumulativeCount[ele];
				newSortBy[cumulativeCountTemp - 1] = sortBy[i];
				newArr1[cumulativeCountTemp - 1] = arr1[i];
				newArr2[cumulativeCountTemp - 1] = arr2[i];
				cumulativeCount[ele]--;
			}
			
			return new Triple(newSortBy, newArr1, newArr2);
		}
			
		
		public boolean find(String substring) {
			PrefixHasher substringHasher = new PrefixHasher(substring);
			//binary search on suffixArr, using compare. nearest from the right to get the longer suffix 
			int l = 0;
			int r = N;
			while(l < r) {
				int m = (l+r)/2;
				if(compare(suffixArr[m], substringHasher) >= 0) {
					r = m;
				}
				else l = m+1;
			}
			//if length is larger and included
			if(substring.length() <= N - suffixArr[l] && 
			   substringHasher.hash(0, substring.length()-1) == hashSuffix(suffixArr[l], 0, substring.length()-1)) {
				return true;
			}
			else return false;
		}
		
		public int compare(int suffixPosition, PrefixHasher substringHasher) {			
			int suffixLength = N-suffixPosition;
			int substringLength = substringHasher.str.length();
			int smallerSize = Math.min(suffixLength, substringLength);
			if(hashSuffix(suffixPosition, 0, smallerSize-1) == substringHasher.hash(0, smallerSize-1)) {
				if(suffixLength < substringLength) return -1;
				if(suffixLength == substringLength) return 0;
				if(suffixLength > substringLength) return 1;
			}
			
			int l = 0;
			int r = smallerSize;
			while(l < r) {
				int m = (l+r)/2;
				if(hashSuffix(suffixPosition, 0, m) == substringHasher.hash(0, m)) {
					l = m+1;
				}
				else r = m;
			}
			//now l = r = place where they first differ		
			if(s.charAt(suffixPosition + l) < substringHasher.str.charAt(l)) return -1;
			else return 1;
		}
		
		//hash a suffix. Given l and r of the suffix, not of the string
		private long hashSuffix(int position, int l, int r) {
			return hasher.hash(position+l, position+r);
		}
	}
	
	static class Triple {
		int[] val1, val2, val3;
		public Triple(int[] a, int[] b, int[] c) {
			this.val1 = a;
			this.val2 = b;
			this.val3 = c;
		}
	}
	
	static class PrefixHasher
	{
		String str;
		long[] prefixHashes;
		final int B = 31;
		long[] expTable;
		public PrefixHasher(String str) {
			this.str = str;
			prefixHashes = new long[str.length()];
			expTable = new long[str.length()];
			build();
			buildExpTable();
		}
		public void build() {
			prefixHashes[0] = str.charAt(0); //get hashCode of first char by casting to int
			for(int i = 1;i<prefixHashes.length;i++) {
				prefixHashes[i] = prefixHashes[i-1] * B + str.charAt(i);
			}
		}
		public void buildExpTable() {
			expTable[0] = 1;
			for(int i = 1;i<expTable.length;i++) {
				expTable[i] = expTable[i-1] * B;
			}
		}
		//get hash of substring between l and r, inclusive
		public long hash(int l, int r) {
			if(l == 0) return prefixHashes[r];
			
			return prefixHashes[r] - prefixHashes[l-1] * expTable[r-l+1];
		}
	}
}
