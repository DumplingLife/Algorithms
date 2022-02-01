package datastructures;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MyFastHashMap {
	static int x = 0;
	public static void main(String[] args) {
		final int entries = 1000000;
		long[] keyArr = new long[entries];
		long[] getArr = new long[entries];
		for(int i=0; i<entries; i++) {
			keyArr[i] = (long) (Long.MAX_VALUE * Math.random());
			getArr[i] = (long) (Long.MAX_VALUE * Math.random());
		}
		
		{
			long start = System.nanoTime();
			
			FastHashMap fastHashMap = new FastHashMap(5*entries);
			for(int i=0; i<entries; i++) {
				fastHashMap.put(keyArr[i], 1);
			}
			for(int i=0; i<entries; i++) {
				fastHashMap.get(getArr[i]);
			}
			
			long end = System.nanoTime();
			System.out.println((end-start)/1000000);
			System.out.println(x);
		}
		
		{
			long start = System.nanoTime();

			HashMap<Long, Long> hashMap = new HashMap<>();
			for(int i=0; i<entries; i++) {
				hashMap.put(keyArr[i], 1L);
			}
			for(int i=0; i<entries; i++) {
				hashMap.get(getArr[i]);
			}
			
			long end = System.nanoTime();
			System.out.println((end-start)/1000000);			
		}
		
	}
	
	//linear probing
	//key = any object (default long), value = any object (default long)
	//removal not supported
	static class FastHashMap {
		long[] keys;
		long[] vals;
		int size;
		public FastHashMap(int size) {
			this.size = size;
			keys = new long[size];
			vals = new long[size];
			Arrays.fill(keys, Long.MIN_VALUE);
			Arrays.fill(vals, Long.MIN_VALUE);
		}
		public void put(long key, long val) {
			int index=(int) (key % size);
			if(keys[index] == Long.MIN_VALUE) {
				keys[index] = key;
				vals[index] = val;
				return;
			}
			
			x++;
			for(;; index=(index+1)%size) {
				if(keys[index] == Long.MIN_VALUE) {
					keys[index] = key;
					vals[index] = val;
					return;
				}
			}
			
		}
		public long get(long key) {
			for(int index=(int) (key % size);; index=(index+1)%size) {
				if(keys[index] == key) return vals[index];
				else if(keys[index] != Long.MIN_VALUE) {
					return Long.MIN_VALUE;
				}
			}
		}
	}
}
