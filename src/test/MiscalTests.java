//some small tests
package test;

import java.util.Stack;

public class MiscalTests
{
	public static void main(String[] args) {
		int reps = 5000000;
		int[] arr = new int[reps];
		for(int i=0; i<reps; i++) {
			arr[i] = (int) (Math.random() * 1000000);
		}
		Stack<Long> stack = new Stack<>();
		MyTimer timer = new MyTimer();
		for(int i=0; i<reps; i++) {
			stack.push((long) arr[i]);
		}
		int sum = 0;
		for(int i=0; i<reps; i++) {
			sum += stack.pop();
		}
		System.out.println(sum);
		timer.lap();
	}
}
