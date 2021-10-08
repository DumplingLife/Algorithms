package test;

import java.util.ArrayList;
import java.util.Arrays;

import algorithms.ArraySorting;

public class SortAccuracyTest {
	final static int size = 9_000_000;
	final static int max =  9_000_000;
	public static void main(String[] args) {
		int[] arr = new int[size];
		randomArray(arr);
		
		int[] arr1 = arr.clone();
		int[] arr2 = arr.clone();
		
		MyTimer timer = new MyTimer();
		ArraySorting.quickSort(arr1);
		timer.lap("quicksort");
		
		Arrays.sort(arr2);
		timer.lap("Arrays.sort");
		
		for(int i=0; i<size; i++) {
			if(arr1[i] != arr2[i]) System.out.println(i);
		}
	}
	public static int random(int lower, int upper) {
		return (int) (Math.floor(Math.random() * (upper - lower + 1)) + lower);
	}
	public static void randomArray(int[] arr) {
		for(int i = 0;i<arr.length;i++) {
			arr[i] = random(0, max);
		}
	}
}
