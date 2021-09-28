package algorithms;

public class Bitmask {
	static int K = 14;
	public static void main(String[] args) {
		loopThroughMask(5);
	}
	
	public static void loopThroughMask(int mask) {
		for(int i=0; i<K; i++) {
			if((mask & (1 << i)) > 0) System.out.println(i);
		}
	}
}
