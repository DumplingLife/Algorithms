package datastructures.test;

import java.util.Arrays;

import datastructures.MyMultiLayerBlockDecomp.MultiLayerBlockDecomp;
import datastructures.test.MyMultiLayerBlockDecompAccuracyTest.SegmentTreeSum;
import test.MyTimer;

public class MyMultiLayerBlockDecompBenchmark {
	public static void main(String[] args) {
		final int N = 10_000_000;
		final int M = 10_000_000;
		int[] arr = new int[N];
		Arrays.fill(arr,0);
		long[] arrLong = new long[0];
		Arrays.fill(arrLong,0);
		
		int[] input = new int[3*M];
		int inputCounter = 0;
		for(int i=0; i<M; i++) {
			if(Math.random()<0.5) {
				input[inputCounter++] = 0;
				int l = (int) (Math.random()*N);
				int r = (int) (Math.random()*N);
				if(r<l) {
					int temp=l;
					l=r;
					r=temp;
				}
				input[inputCounter++] = l;
				input[inputCounter++] = r;
			}
			else {
				input[inputCounter++] = 1;
				int ind = (int) (Math.random()*N);
				int x = (int) (Math.random()*1000);
				input[inputCounter++] = ind;
				input[inputCounter++] = x;
			}
		}
		
		
		
		int sum = 0;

		MyTimer timer = new MyTimer();
		SegmentTreeSum st = new SegmentTreeSum(arr);
		inputCounter = 0;
		for(int i=0; i<M; i++) {
			if(input[inputCounter++] == 0) {
				int l = input[inputCounter++];
				int r = input[inputCounter++];
				sum += st.query(l, r);
			}
			else {
				int ind = input[inputCounter++];
				int x = input[inputCounter++];
				st.update(ind, x);
			}
		}
		timer.lap("st");
		
		MultiLayerBlockDecomp m = new MultiLayerBlockDecomp(arrLong);
		inputCounter = 0;
		for(int i=0; i<M; i++) {
			if(input[inputCounter++] == 0) {
				int l = input[inputCounter++];
				int r = input[inputCounter++];
				sum += m.query(l, r);
			}
			else {
				int ind = input[inputCounter++];
				int x = input[inputCounter++];
				m.update(ind, x);
			}
		}
		timer.lap("m");
		
		inputCounter = 0;
		for(int i=0; i<M; i++) {
			if(input[inputCounter++] == 0) {
				int l = input[inputCounter++];
				int r = input[inputCounter++];
				sum += l+r;
			}
			else {
				int ind = input[inputCounter++];
				int x = input[inputCounter++];
				sum += ind+x;
			}
		}
		timer.lap("overhead");
		
		System.out.println(sum);
	}
	
	static class SegmentTreeSum {
		int[] t;
		int size;
		
		public SegmentTreeSum(int[] arr) {
			t = new int[4*arr.length];
			size = arr.length;
			build(1, 0, arr.length-1, arr);
		}
		public int query(int l, int r) {
			return query(1, 0, size-1, l, r);
		}
		public void update(int index, int val) {
			update(1, 0, size-1, index, val);
		}
		
		private int query(int index, int L, int R, int l, int r) {
			if(l <= L && R <= r) return t[index];
			
			int M = (L + R)/2;
			if(M+1 > r) return query(2*index, L, M, l, r);
			if(M < l) return query(2*index+1, M+1, R, l, r);
			return merge(query(2*index, L, M, l, r), query(2*index+1, M + 1, R, l, r));
		}
		
		private void update(int index, int L, int R, int inputIndex, int val) {
			if(inputIndex < L || R < inputIndex) return;
			if(L == R) t[index] = val;
			else {
				int M = (L + R)/2;
				
				update(2*index, L, M, inputIndex, val);
				update(2*index+1, M+1, R, inputIndex, val);
				
				t[index] = merge(t[2*index], t[2*index+1]);
			}
		}
		
		private void build(int index, int L, int R, int[] arr) {
			if(L == R) t[index] = arr[L];
			else {
				int M = (L+R)/2;
				build(2*index, L, M, arr);
				build(2*index+1, M+1, R, arr);
				t[index] = merge(t[2*index], t[2*index+1]);
			}
		}
		private int merge(int a, int b) {
			return a+b;
		}
	}
}
