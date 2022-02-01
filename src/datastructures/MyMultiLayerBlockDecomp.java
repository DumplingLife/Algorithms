package datastructures;

import java.util.Arrays;

public class MyMultiLayerBlockDecomp {
	public static void main(String[] args) {
		long[] arr = new long[150_000];
		for(int i=0; i<arr.length; i++) {
			arr[i] = i;
		}
		MultiLayerBlockDecomp m = new MultiLayerBlockDecomp(arr);
		System.out.println(m.query(5, 3205));
		m.update(3003, 1000);
		System.out.println(m.query(5, 3205));
	}
	
	//much harder implementation to try to make this flexible
	//Instead, if N = 200,000, choose K=6, numLayers = 7
	public static class MultiLayerBlockDecomp {
		final int K = 10;
		final int numLayers = 7;
		final int N = (int) Math.pow(K, numLayers);
		long[][] layers = new long[numLayers][];
		public MultiLayerBlockDecomp(long[] arr) {
			int numInLayer = N;
			layers[0] = Arrays.copyOf(arr, numInLayer);
			
			for(int layer=1; layer<numLayers; layer++) {
				numInLayer /= K;
				
				layers[layer] = new long[numInLayer];
				for(int i=0; i<layers[layer].length; i++) {
					for(int j=K*i; j<K*(i+1); j++) {
						layers[layer][i] += layers[layer-1][j];
					}
				}
			}
		}
		public void update(int i, long x) {
			long change = x - layers[0][i];
			for(int layer=0; layer<layers.length; layer++) {
				layers[layer][i] += change;
				i /= K;
			}
		}
		public long query(int l, int r) {
			long res = 0;
			int layer = 0;
			int blockSize = 1;
			for(; layer<numLayers; layer++, blockSize*=K) {
				while(r-l+1 >= blockSize && l % (K*blockSize) != 0) {
					res += layers[layer][l/blockSize];
					l += blockSize;
				}
			}
			for(; layer>=0; layer--, blockSize/=K) {
				while(r-l+1 >= blockSize) {
					res += layers[layer][l/blockSize];
					l += blockSize;
				}
			}
			return res;
		}
	}
}
