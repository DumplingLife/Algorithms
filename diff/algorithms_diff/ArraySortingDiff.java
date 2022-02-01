/*
 * countingSort: not tested but looks the exact same, so should be good
 * radix sort: tested (9M array size), 2x slower
 */

package algorithms_diff;

import java.util.Arrays;

public class ArraySortingDiff {
	public static void main(String[] args) {
		long[] arr = new long[] {4,3,10006,1,10007,8,4,10003};
		int[] arr2 = new int[arr.length];
		for(int i=0; i<arr.length; i++) {
			arr2[i] = (int) (arr[i]/4);
		}
		arr = radixSort(arr, 8);
		System.out.println(Arrays.toString(arr));
	}
	
	public static void countingSort(int[] arr, int max) {
		int[] counts = new int[max+1];
		for(int ele : arr) {
			counts[ele]++;
		}
		int pointer = 0;
		for(int ele=0; ele<=max; ele++) {
			for(; counts[ele]>0; counts[ele]--) {
				arr[pointer] = ele;
				pointer++;
			}
		}
	}
	
	//return arr after sorted by sortBy (but do not sort sortBy)
	public static long[] stableCountingSort(long[] arr, int[] sortBy, int max) {
		int[] culCounts = new int[max+1];
		for(int ele : sortBy) {
			culCounts[ele]++;
		}
		for(int i=1; i<=max; i++) {
			culCounts[i] += culCounts[i-1];
		}
		
		long[] newArr = new long[arr.length];
		for(int i=arr.length-1; i>=0; i--) {
			newArr[culCounts[sortBy[i]]-1] = arr[i];
			culCounts[sortBy[i]]--;
		}
		return newArr;
	}
	
	public static long[] radixSort(long[] arr, long max) {
		final int base = 1024;
		for(long placeVal=1; placeVal<=max; placeVal*=base) {
			int[] sortBy = new int[arr.length];
			for(int i=0; i<arr.length; i++) {
				sortBy[i] = (int) ((arr[i]/placeVal) % base);
			}
			arr = stableCountingSort(arr, sortBy, base);
		}
		return arr;
	}
}
