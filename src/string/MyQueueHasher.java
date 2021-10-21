//sliding window hash

package string;

import java.util.ArrayDeque;
import java.util.Queue;

public class MyQueueHasher {
	public static void main(String[] args) {
		QueueHasher q = new QueueHasher();
		q.add('a');
		q.add('b');
		q.add('c');
		System.out.println(q.hash);
		q.poll();
		System.out.println(q.hash);
		System.out.println("bc".hashCode());
	}
	
	static class QueueHasher {
		long[] expTable;
		long hash = 0;
		final int B = 31;
		final int maxSize = 1000000;
		Queue<Character> queue;
		public QueueHasher() {
			queue = new ArrayDeque<>();
			
			expTable = new long[maxSize];
			expTable[0] = 1;
			for(int i = 1;i<expTable.length;i++) {
				expTable[i] = expTable[i-1] * B;
			}			
		}
		public void add(char c) {
			hash = hash*B + c;
			queue.add(c);
		}
		public void poll() {
			char c = queue.peek();
			hash -= expTable[queue.size()-1]*c;
			queue.poll();
		}
	}
}
