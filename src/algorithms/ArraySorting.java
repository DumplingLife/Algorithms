package algorithms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ArraySorting
{
	public static void main(String[] args) throws IOException
	{
		int[] arr = {1,3,6,6,2,7,5,10,1,10};
		bucketSort(arr, 10);
		int breakpoint = 0;
	}
	
	public static void quickSort(int[] arr) {
		quickSortRecur(arr, 0, arr.length-1);
	}
	//modifies the range l...r in arr to be sorted (quicksort)
	public static void quickSortRecur(int[] arr, int l, int r) {
		if(l >= r) return;
		
		int piv = arr[r];
		int smallCounter = l;
		for(int i = l;i<r;i++) {
			if(compare(arr[i], piv) == -1) {
				swap(arr, smallCounter, i);
				smallCounter++;
			}
		}
		
		swap(arr, smallCounter, r);
		
		quickSortRecur(arr, l, smallCounter-1);
		quickSortRecur(arr, smallCounter+1, r);
	}
	public static void swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	public static int compare(int a, int b) {
		if(a < b) return -1;
		else if(a == b) return 0;
		else return 1;
	}
	//shuffle array, for average case quicksort
	public static void shuffle(int[] arr) {
	    Random random = new Random();
	    for(int i = arr.length-1; i>0; i--)
	    {
	      int index = random.nextInt(i + 1);
	      swap(arr, index, i);
	    }
	}
	
	
	public static void mergeSort(int[] arr) {
		mergeSortRecur(arr, 0, arr.length-1);
	}
	//modifies the range l...r in arr to be sorted (mergesort)
	public static void mergeSortRecur(int[] arr, int l, int r) {
		if(l == r) return;
		
		int m = (l+r)/2;
		//recursive calls
		mergeSortRecur(arr, l, m);
		mergeSortRecur(arr, m+1, r);
		
		//MERGE
		//create a and b equal to the left and right half of arr
		int[] a = new int[m-l+1];
		int[] b = new int[r-m];
		for(int i = 0;i<a.length;i++) {
			a[i] = arr[l + i];
		}
		for(int i = 0;i<b.length;i++) {
			b[i] = arr[l + i + a.length];
		}
		
		//pointers
		int x = 0;
		int y = 0;
		while(x < a.length && y < b.length) {
			if(compare(a[x], b[y]) == -1) {
				arr[l+x+y] = a[x];
				x++;
			}
			else {
				arr[l+x+y] = b[y];
				y++;
			}
		}
		//add remaining values
		while(x < a.length) {
			arr[l+x+y] = a[x];
			x++;
		}
		while(y < b.length) {
			arr[l+x+y] = b[y];
			y++;
		}
	}
	
	
	public static void countingSort(int[] arr, int max) {
		int[] count = new int[max+1];
		for(int ele : arr) {
			count[ele]++;
		}
		
		int arrPointer = 0;
		for(int i = 0;i<count.length;i++) {
			for(int j = 0;j<count[i];j++) {
				arr[arrPointer] = i;
				arrPointer++;
			}
		}
	}
	
	public static int[] stableCountingSort(int[] arr, int max) {
		int[] cumulativeCount = new int[max+1];
		for(int ele : arr) {
			cumulativeCount[ele]++;
		}
		for(int i = 1;i<cumulativeCount.length; i++) {
			cumulativeCount[i] += cumulativeCount[i-1];
		}
		
		int[] ret = new int[arr.length];
		for(int i = arr.length-1; i>=0; i--) {
			int ele = arr[i];
			ret[cumulativeCount[ele] - 1] = ele;
			cumulativeCount[ele]--;
		}
		return ret;
	}
	
	
	
	public static int[] countingSortForRadix(int[] arr, int base, int placeValue) {
		final int max = base;
		int[] cumulativeCount = new int[max];
		for(int ele : arr) {
			cumulativeCount[getDigit(ele, base, placeValue)]++;
		}
		for(int i = 1;i<cumulativeCount.length; i++) {
			cumulativeCount[i] += cumulativeCount[i-1];
		}
		
		int[] ret = new int[arr.length];
		for(int i = arr.length-1; i>=0; i--) {
			int ele = arr[i];
			int eleDigit = getDigit(ele, base, placeValue);
			ret[cumulativeCount[eleDigit] - 1] = ele;
			cumulativeCount[eleDigit]--;
		}
		return ret;
	}
	public static int getDigit(int x, int base, int placeValue) {
		return (x/placeValue) % base;
	}
	public static int[] radixSort(int[] arr, int base) {
		final int max = 1000000;
		
		for(int placeValue = 1; placeValue <= max; placeValue *= base) {
			arr = countingSortForRadix(arr, base, placeValue);
		}
		return arr;
	}
	
	//if arr is int[], countingSort will be better. But this lets you have a object arr, with some sortBy field
	public static void bucketSort(int[] arr, int max) {
		List<Integer>[] count = new LinkedList[max+1];
		for(int i=0; i<count.length; i++) {
			count[i] = new LinkedList<Integer>();
		}
		for(int ele : arr) {
			count[ele].add(ele);
		}
		
		int arrPointer = 0;
		for(int i = 0;i<count.length;i++) {
			for(int j = 0;j<count[i].size();j++) {
				arr[arrPointer] = count[i].get(j);
				arrPointer++;
			}
		}
	}
}
