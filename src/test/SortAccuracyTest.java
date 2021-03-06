package test;

import java.util.Arrays;


public class SortAccuracyTest {
	final static int size = 9;
	final static int max =  9_000_000;
	public static void main(String[] args) {
		long[] arr = new long[size];
		randomArray(arr);
		
		long[] arr1 = arr.clone();
		long[] arr2 = arr.clone();
		
		MyTimer timer = new MyTimer();
		arr1 = radixSort(arr1, max);
		timer.lap("my sort");
		
		Arrays.sort(arr2);
		timer.lap("Arrays.sort");
		
		for(int i=0; i<size; i++) {
			if(arr1[i] != arr2[i]) System.out.println(i);
		}
	}
	public static int random(int lower, int upper) {
		return (int) (Math.floor(Math.random() * (upper - lower + 1)) + lower);
	}
	public static void randomArray(long[] arr) {
		for(int i = 0;i<arr.length;i++) {
			arr[i] = random(0, max);
		}
	}
	
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
