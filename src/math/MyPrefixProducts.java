//currently doing factorial becuase it's a common case, but you could modify to do prefix product of any array

package math;

public class MyPrefixProducts {
	public static void main(String[] args) {
		PrefixProducts pp = new PrefixProducts(5, 11);
		System.out.println(pp.rangeMultiply(3, 5));
	}
	
	static class PrefixProducts {
		//prefixProducts[i] = i!, prefixProducts[0] = 0! = 1
		long[] prefixProducts;
		int M;
		public PrefixProducts(int N, int M) {
			prefixProducts = new long[N+1];
			this.M = M;
			
			prefixProducts[0] = 1;
			for(int i=1; i<=N; i++) {
				prefixProducts[i] = (prefixProducts[i-1] * i) % M; 
			}
		}
		
		//1 <= a <= b
		public long rangeMultiply(int a, int b) {
			return divide(prefixProducts[b], prefixProducts[a-1]);
		}
		

		private long divide(long a, long b) {
			return (a * exp(b, M-2)) % M;
		}
		private long exp(long base, int exp) {
			long res = 1;
			while(exp > 0) {
				if(exp % 2 == 1) res = (res*base) % M;
				base = (base*base) % M;
				exp /= 2;
			}
			return res;
		}
	}
}
