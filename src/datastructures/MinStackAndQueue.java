package datastructures;

import java.util.Arrays;

public class MinStackAndQueue {
	public static void main(String[] args) {
		MinQueue q = new MinQueue();

		long start = System.nanoTime();
		
		for(int i = 0;i<10000000;i++) {
			q.offer(1000*i % 1000000007);
		}
		
	    long end = System.nanoTime();
	    System.out.println((end - start)/1000000);
	    int breakpoint = 0;
	}
	
	static class MinStack {
		FastArrayList vals = new FastArrayList();
		FastArrayList mins = new FastArrayList();
		public void push(int item) {
			vals.add(item);
			mins.add(Math.min(item, getMin()));
		}
		public int pop() {
			mins.pop();
			return vals.pop();
		}
		public int getMin() {
			if(mins.size() == 0) return Integer.MAX_VALUE;
			else return mins.get(mins.size()-1);
		}
		public boolean isEmpty() {
			return vals.isEmpty();
		}
	}
	static class MinQueue {
		MinStack inbox = new MinStack();
		MinStack outbox = new MinStack();
		public void offer(int item) {
			inbox.push(item);
		}
		public int poll() {
			if(outbox.isEmpty()) {
				while(!inbox.isEmpty()) {
					outbox.push(inbox.pop());
				}
			}
			return outbox.pop();
		}
		public int getMin() {
			return Math.min(inbox.getMin(), outbox.getMin());
		}
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
		//for performance, this won't clear the value in the array, it just moves the pointer back
		public int pop() {
			pointer--;
			return arr[pointer]; 
		}
		public int get(int index) {
			return arr[index];
		}
		public int size() {
			return pointer;
		}
		public boolean isEmpty() {
			return pointer == 0;
		}
	}
}
