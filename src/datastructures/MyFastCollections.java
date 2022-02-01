/*
 * HashMap, ArrayList, LinkedList
 * 
 * On the side, the speed is compared to Java collections
 * 
 * If there is a cosntructor, it will be described
 */

package datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MyFastCollections {
	public static void main(String[] args) {
		
		final int N = 10000000;

		int[] arr = new int[N];
		FastArrayList fastAL = new FastArrayList();
		ArrayList AL = new ArrayList(N+1);
		FastLinkedList fastLL = new FastLinkedList();
		FastHashMap fastMap = new FastHashMap(N);
		HashMap<Integer,Integer> map = new HashMap<>(N);
		
		long start = System.nanoTime();
		
		for(int i = 0;i<N;i++) {
			//arr[i] = i; //112
			//AL.add(i); //473
			fastAL.add(i); //293
			//fastLL.add(i); //1290
			//fastMap.put(i,i); //1143
			//map.put(i,i); //1011
		}

	    long end = System.nanoTime();
	    System.out.println((end - start)/1000000);
	    System.out.println(fastAL.get(138457));
	    int breakpoint = 0;
	}
	
	//2x faster than ArrayList
	static class FastArrayList {
		public int[] arr;
		public int pointer = 0;
		public FastArrayList() {
			arr = new int[8];
		}
		public void add(int ele) {
			if(pointer >= arr.length) {
				arr = Arrays.copyOf(arr, arr.length*2);
			}
			arr[pointer] = ele;
			pointer++;
		}
		public int get(int index) {
			return arr[index];
		}
	}
	
	//HashMap
	static class FastHashMap {
		public FastArrayList[] keyBuckets;
		public FastArrayList[] valueBuckets;
		public int size;
		//we use int instead of Integer, so we can't return null. Must return some arbitrary int
		public static final int GET_NOT_FOUND = -1734923957;
		
		//specify size = # buckets
		public FastHashMap(int size) {
			this.size = size;
			keyBuckets = new FastArrayList[size];
			valueBuckets = new FastArrayList[size];
			/*
			for(int i = 0;i<size;i++) {
				keyBuckets[i] = new FastArrayList(incSize);
				valueBuckets[i] = new FastArrayList(incSize);
			}
			*/
		}
		public void put(int key, int value) {
			int index = key % size;
			if(keyBuckets[index] == null) {
				keyBuckets[index] = new FastArrayList();
				valueBuckets[index] = new FastArrayList();
			}
			keyBuckets[index].add(key);
			valueBuckets[index].add(value);
		}
		public int get(int key) {
			int index = key % size;
			int[] temp = keyBuckets[index].arr;
			for(int i = 0;i<temp.length;i++) {
				if(temp[i] == index) return valueBuckets[index].get(i);
			}
			return GET_NOT_FOUND;
		}
	}

	//HashSet uses HashMap internally. Since my FastHashMap is barely any faster, my HashSet
	//will barely be faster (and probably slower since Java has optimizations)


	//LinkedList
	//There is an iterator
	static class FastLinkedList implements Iterable<LinkedListNode> {	
		LinkedListNode root = null;
		LinkedListNode last = null;
		public void add(int value1) {
			LinkedListNode node = new LinkedListNode(null, last, value1);
			
			if(root == null) root = node;
			else last.next = node;
			
			last = node;
		}
		@Override
		public Iterator<LinkedListNode> iterator() {
			return new Iterator<LinkedListNode>() {
				//create a imaginary node that points to root to avoid null check
				LinkedListNode node = new LinkedListNode(root, null, 0);
				@Override
				public boolean hasNext() {
					return node.next != null;
				}
				@Override
				public LinkedListNode next() {
					node = node.next;
					return node;
				}
			};
		}
	}
	static class LinkedListNode {
		LinkedListNode next;
		LinkedListNode prev;
		int value1;
		public LinkedListNode(LinkedListNode next, LinkedListNode prev, int value1) {
			this.next = next;
			this.prev = prev;
			this.value1 = value1;
		}
	}
}





