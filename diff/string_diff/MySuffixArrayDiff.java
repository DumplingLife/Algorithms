/*
 * 4-5x slower than my other suffix arr
 */

package string_diff;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class MySuffixArrayDiff {
	public static void main(String[] args) {
		SuffixArrayDiff suffixArrDiff = new SuffixArrayDiff("cbaacaa");
		for(int ind : suffixArrDiff.suffixArr) {
			System.out.println(suffixArrDiff.s.substring(ind));
		}
	}
	
	public static class SuffixArrayDiff {
		String s;
		public int[] suffixArr;
		public SuffixArrayDiff(String inputStr) {
			s = inputStr;
			inputStr += "$";
			
			long[] rank = new long[inputStr.length()];
			for(int i=0; i<inputStr.length(); i++) {
				rank[i] = inputStr.charAt(i);
			}
			compressArr(rank);
			
			for(int len=2; len<=inputStr.length(); len *= 2) {
				long[] newRank = new long[inputStr.length()];
				for(int i=0; i<inputStr.length(); i++) {
					newRank[i] = inputStr.length()*rank[i] + rank[(i+len/2)%inputStr.length()];
				}
				rank = compressArr(newRank);
			}
			
			suffixArr = new int[inputStr.length()-1];
			for(int i=0; i<inputStr.length(); i++) {
				if(rank[i] == 0) continue;
				suffixArr[(int) (rank[i]-1)] = i;
			}
		}
		public long[] compressArr(long[] arr) {
			Integer[] indexes = new Integer[arr.length];
			for(int i=0; i<indexes.length; i++) {
				indexes[i] = i;
			}
			Arrays.sort(indexes, Comparator.comparingLong(index -> arr[index]));
			long[] tempArr = new long[arr.length];
			for(int i=0; i<indexes.length; i++) {
				if(i != 0 && arr[indexes[i-1]] == arr[indexes[i]]) tempArr[indexes[i]] = tempArr[indexes[i-1]];
				else tempArr[indexes[i]] = i;
			}
			return tempArr;
			
			/*
			long[] tempArr = arr.clone();
			HashMap<Long, Integer> compressMap = new HashMap<>();
			Arrays.sort(tempArr);
			int counter = 0;
			for(int i=0; i<tempArr.length; i++) {
				if(i>0 && tempArr[i] == tempArr[i-1]) continue;
				compressMap.put(tempArr[i], counter);
				counter++;
			}
			for(int i=0; i<arr.length; i++) {
				arr[i] = compressMap.get(arr[i]);
			}
			*/
		}
	}
}
