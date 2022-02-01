package datastructures;

public class MyDSU {
	public static void main(String[] args) {
		int[] arr = {0, 1, 2, 3, 4, 5};
		D[] dArr = new D[arr.length];
		for(int i = 0;i<arr.length;i++) {
			dArr[i] = new D(arr[i]);
		}
		DSU dsu = new DSU(dArr);
		dsu.union(0, 1);
		dsu.union(1, 3);
		dsu.union(3, 4);
		System.out.println(dsu.data[dsu.find(4)].sum);
		System.out.println(dsu.sizes[dsu.find(4)]);
	}
	
	static class D {
		int sum;
		public D(int sum) {
			this.sum = sum;
		}
	}
	//mutate parent
	public static void mergeData(D parent, D child) {
		parent.sum += child.sum;
	}
	static class DSU {
		D[] data;
		int[] parents;
		int[] sizes;
		public DSU(D[] data) {
			this.data = data;
			int size = data.length;
			data = new D[size];
			parents = new int[size];
			sizes = new int[size];
			for(int i = 0;i<size;i++) {
				parents[i] = -1;
				sizes[i] = 1;
			}
		}
		public int find(int a) {
			if(parents[a] == -1) return a;
			int ret = find(parents[a]);
			//path compression
			parents[a] = ret;
			return ret;
		}
		public D union(int a, int b) {
			int aRoot = find(a);
			int bRoot = find(b);
			if(aRoot == bRoot) return data[aRoot];
			
			int largeRoot = aRoot;
			int smallRoot = bRoot;
			if(sizes[aRoot] < sizes[bRoot]) {
				largeRoot = bRoot;
				smallRoot = aRoot;
			}
			
			parents[smallRoot] = largeRoot;
			sizes[largeRoot] += sizes[smallRoot];
			mergeData(data[largeRoot], data[smallRoot]);
			return data[largeRoot];
		}
	}
}
