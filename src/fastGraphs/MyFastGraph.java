package fastGraphs;

public class MyFastGraph {
	public static class Graph {
		int N, M;
		int[] endpoints;
		int[] head;
		
		public Graph(int N, int M, int[] edgeArr1, int[] edgeArr2) {
			this.N = N;
			this.M = M;
			endpoints = new int[2*M];
			head = new int[N];
			
			int[] nodeDegrees = new int[N];
			for(int ele : edgeArr1) {
				nodeDegrees[ele]++;
			}
			for(int ele : edgeArr2) {
				nodeDegrees[ele]++;
			}
			
			int[] nodeEdgeCounters = new int[N];
			head[0] = 0;
			for(int i=1; i<N; i++) {
				head[i] = head[i-1] + nodeDegrees[i-1];
				nodeEdgeCounters[i] = head[i];
			}
			
			for(int i=0; i<M; i++) {
				int a = edgeArr1[i];
				int b = edgeArr2[i];
				nodeEdgeCounters[a]++;
				nodeEdgeCounters[b]++;
				endpoints[nodeEdgeCounters[a]-1] = b;
				endpoints[nodeEdgeCounters[b]-1] = a;
			}
		}
	}
}
