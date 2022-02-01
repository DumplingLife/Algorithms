/*
 * ArrayDeque, but you can use get(index)
 * Probably faster, but not tested
 */

package datastructures;

public class MyCustomArrayDeque {
	static class CustomArrayDeque<T> {
		T[] arr;
		int l, r;
		public CustomArrayDeque(int size) {
			arr = (T[]) new Object[2*size];
			l = size;
			r = size-1;
		}
		public T get(int ind) {
			return arr[l+ind];
		}
		public void set(int ind, T ele) {
			arr[l+ind] = ele;
		}
		public void addFirst(T ele) {
			arr[l-1] = ele;
			l--;
		}
		public void addLast(T ele) {
			arr[r+1] = ele;
			r++;
		}
		public void removeFirst() {
			l++;
		}
		public void removeLast() {
			r--;
		}
		public T getFirst() {
			return arr[l];
		}
		public T getLast() {
			return arr[r];
		}
		public int size() {
			return r-l+1;
		}
		public boolean isEmpty() {
			return size() == 0;
		}
	}
}
