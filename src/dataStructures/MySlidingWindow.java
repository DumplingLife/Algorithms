package dataStructures;

public class MySlidingWindow {

	
	static class SlidingWindow {
		int[] arr;
		int sum = 0;
		int l = 0;
		int r = -1;
		public SlidingWindow(int[] arr) {
			this.arr = arr;
		}
		public void growRight() {
			r++;
			sum += arr[r];
		}
		public void shrinkLeft() {
			sum -= arr[l];
			l--;
		}
	}
}
