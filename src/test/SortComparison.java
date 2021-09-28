package test;

import java.util.ArrayList;
import java.util.Arrays;

import algorithms.ArraySorting;

public class SortComparison {
	final static int reps = 1;
	final static int size = 9_000_000;
	final static int max =  9_000_000;
	public static void main(String[] args) {
		int[][] arrs = new int[6*reps][size];
		for(int i = 0;i<arrs.length;i++) {
			randomArray(arrs[i]);
		}
		
		int arrsCounter = 0;
		MyTimer timer = new MyTimer();
		for(int i = 0;i<reps;i++) ArraySorting.quickSort(arrs[arrsCounter++]);
		timer.lap("quicksort");
		
		for(int i = 0;i<reps;i++) ArraySorting.countingSort(arrs[arrsCounter++], max);
		timer.lap("counting sort");
		
		for(int i = 0;i<reps;i++) ArraySorting.bucketSort(arrs[arrsCounter++], max);
		timer.lap("bucket sort");
		
		for(int i = 0;i<reps;i++) stableCountingSort(arrs[arrsCounter++], max);
		timer.lap("stable counting sort");
		
		for(int i = 0;i<reps;i++) ArraySorting.radixSort(arrs[arrsCounter++], 1024);
		timer.lap("radix sort");
		
		for(int i = 0;i<reps;i++) Arrays.sort(arrs[arrsCounter++]);
		timer.lap("Arrays.sort");
	}
	public static int random(int lower, int upper) {
		return (int) (Math.floor(Math.random() * (upper - lower + 1)) + lower);
	}
	public static void randomArray(int[] arr) {
		for(int i = 0;i<arr.length;i++) {
			arr[i] = random(0, max);
		}
	}
	
	
	public static void bucketSort(int[] arr, int max) {
		MyTimer timer = new MyTimer();
		FastArrayList[] count = new FastArrayList[max+1];
		for(int i=0; i<count.length; i++) {
			count[i] = new FastArrayList();
		}
		timer.lap("---1");
		for(int ele : arr) {
			count[ele].add(ele);
		}
		timer.lap("---2");
		
		int arrPointer = 0;
		for(int i = 0;i<count.length;i++) {
			for(int j = 0;j<count[i].size();j++) {
				arr[arrPointer] = count[i].get(j);
				arrPointer++;
			}
		}
		timer.lap("---3");
	}
	
	
	public static int[] stableCountingSort(int[] arr, int max) {
		MyTimer timer = new MyTimer();
		
		int[] cumulativeCount = new int[max+1];
		for(int ele : arr) {
			cumulativeCount[ele]++;
		}
		timer.lap("---1");
		
		for(int i = 1;i<cumulativeCount.length; i++) {
			cumulativeCount[i] += cumulativeCount[i-1];
		}
		timer.lap("---2");
		
		int[] ret = new int[arr.length];
		for(int i = arr.length-1; i>=0; i--) {
			int ele = arr[i];
			ret[--cumulativeCount[ele]] = ele;
		}
		timer.lap("---3");
		return ret;
	}
	
	static class FastArrayList {
		public int[] arr;
		public int pointer = 0;
		public FastArrayList() {
			arr = new int[8];
		}
		public void add(int ele) {
			if(pointer >= arr.length) {
				//grow
				arr = Arrays.copyOf(arr, arr.length*2);
			}
			arr[pointer] = ele;
			pointer++;
		}
		public int size() {
			return pointer;
		}
		public int get(int index) {
			return arr[index];
		}
	}
}
