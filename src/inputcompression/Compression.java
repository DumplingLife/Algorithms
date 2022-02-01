package inputcompression;

import java.util.HashMap;

public class Compression
{
	public static void main(String[] args) {
		int[] arr = {10, 25, 20, 20, 30, 40, 30};
		compress(arr);
		int breakpoint = 0;
	}
	
	public static HashMap<Integer, Integer> getCompressMap(int[] arr) {
		int[] arrCopy = arr.clone();
		quickSort(arrCopy);
		
		HashMap<Integer, Integer> ret = new HashMap<>();
		//smallest value is 0. If this needs to be 1, change this variable to 1
		int newValCounter = 0;
		for(int i = 0;i<arrCopy.length;i++) {
			if(i > 0 && arrCopy[i-1] == arrCopy[i]) continue;
			ret.put(arrCopy[i], newValCounter);
			newValCounter++;
		}
		return ret;
	}
	public static void compress(int[] arr) {
		HashMap<Integer, Integer> map = getCompressMap(arr);
		for(int i = 0;i<arr.length;i++) {
			arr[i] = map.get(arr[i]);
		}
	}
	
	
	
	
	public static void quickSort(int[] arr) {
		quickSortRecur(arr, 0, arr.length-1);
	}
	//modifies the range l...r in arr to be sorted (quicksort)
	public static void quickSortRecur(int[] arr, int l, int r) {
		if(l >= r) return;
		
		int pivotIndex = (l+r)/2;
		int smallCounter = l;
		for(int i = l;i<=r;i++) {
			if(compare(arr[i], arr[pivotIndex]) == -1) {
				//if pivot gets moved, update pivot index
				if(smallCounter == pivotIndex) pivotIndex = i;
				swap(arr, smallCounter, i);
				smallCounter++;
			}
		}
		//everything with index < smallCounter is smaller
		//everything with index >= smallCounter is greater than or equal to
		//swap pivot with smallCounter to get it in the right place
		swap(arr, smallCounter, pivotIndex);
		
		quickSortRecur(arr, l, smallCounter-1);
		quickSortRecur(arr, smallCounter+1, r);
	}
	//swaps positions a and b in arr
	public static void swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	//customize
	//-1 if a <= b, 0 if equal, 1 if a >= b
	public static int compare(int a, int b) {
		if(a < b) return -1;
		else if(a == b) return 0;
		else return 1;
	}
}
