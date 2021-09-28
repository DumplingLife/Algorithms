package fastGraphs;

import java.util.HashSet;
import java.util.PriorityQueue;

public class MyFastDijkstra {
	public static void main(String[] args) {
		Graph graph = new Graph(4, 4, new int[] {0,0,0,1}, new int[] {1,2,3,2}, new int[] {2,10,2,2});
		graph.dijkstra();
		int breakpoint = 0;
	}
	
	public static class Graph {
		int N, M;
		int[] endpoints, edgeWeights;
		int[] head, dists;
		
		public Graph(int N, int M, int[] edgeArr1, int[] edgeArr2, int[] edgeWeightsInp) {
			this.N = N;
			this.M = M;	
			endpoints = new int[2*M];
			head = new int[N];
			
			edgeWeights = new int[2*M];
			dists = new int[N];
			
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
				edgeWeights[nodeEdgeCounters[a]-1] = edgeWeightsInp[i];
				edgeWeights[nodeEdgeCounters[b]-1] = edgeWeightsInp[i];
			}
			int breakpoint = 0;
		}
		
		public void dijkstra() {
			for(int i=0; i<N; i++) {
				dists[i] = Integer.MAX_VALUE;
			}
			dists[0] = 0;
			
			PriorityQueue<Integer> pq = new PriorityQueue<>((Integer a, Integer b) -> Integer.compare(dists[a], dists[b]));
			HashSet<Integer> settled = new HashSet<>();
			pq.add(0);
			while(!pq.isEmpty()) {
				int nodeInd = pq.poll();
				if(settled.contains(nodeInd)) continue;
				
				for(int edgeInd = head[nodeInd]; edgeInd < (nodeInd == N-1 ? 2*M : head[nodeInd+1]); edgeInd++) {
					int endpoint = endpoints[edgeInd];
					int newShortestDist = dists[nodeInd] + edgeWeights[edgeInd];
					if(newShortestDist < dists[endpoint]) {
						dists[endpoint] = newShortestDist;
						pq.add(endpoint);
					}
				}
				settled.add(nodeInd);
			}
		}
	}
}
