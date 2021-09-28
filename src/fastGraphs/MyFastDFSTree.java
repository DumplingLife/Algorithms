package fastGraphs;

import java.util.HashSet;

import test.MyTimer;

public class MyFastDFSTree {
	public static void main(String[] args) {
		Graph graph = new Graph(4, 5, new int[] {0,0,0,1,2}, new int[] {1,2,3,2,3});
		DFSTree tree = new DFSTree(graph);
		int breakpoint = 0;
	}
	
	public static class DFSTree {
		int N, M;
		int[] children, backEdges;
		int[] parent, depth, childrenHead, backEdgesHead;
		public DFSTree(Graph graph) {
			MyTimer timer = new MyTimer();
			//initialize everything
			N = graph.N;
			M = graph.M;
			parent = new int[N];
			children = new int[N-1];
			backEdges = new int[M-N+1];
			childrenHead = new int[N];
			backEdgesHead = new int[N];
			depth = new int[N];
			
			timer.lap("initialize");
			
			//build part 1
			depth[0] = 0;
			for(int i=0; i<N; i++) {
				parent[i] = -1;
			}
			int[] childrenCount = new int[N];
			int[] backEdgesCount = new int[N];
			HashSet<Integer> tempTree1 = new HashSet<>();
			tempTree1.add(0);

			timer.lap("build part 1 setup");
			
			buildPart1(0, graph, tempTree1, childrenCount, backEdgesCount);

			timer.lap("build part 1");
			System.out.println(bp1Interations);
			
			//use childrenCount, backEdgeCount to find heads and initialize counters
			childrenHead[0] = 0;
			int[] childrenCounters = new int[N];
			for(int i=1; i<N; i++) {
				childrenHead[i] = childrenHead[i-1] + childrenCount[i-1];
				childrenCounters[i] = childrenHead[i];
			}
			backEdgesHead[0] = 0;
			int[] backEdgesCounters = new int[N];
			for(int i=1; i<N; i++) {
				backEdgesHead[i] = backEdgesHead[i-1] + backEdgesCount[i-1];
				backEdgesCounters[i] = backEdgesHead[i];
			}
			timer.lap("get heads and counters");
						
			//fill children and backEdges
			for(int node=0; node<N; node++) {
				for(int edgeInd=graph.head[node]; edgeInd<(node==N-1 ? 2*graph.M : graph.head[node+1]); edgeInd++) {
					int endpoint = graph.endpoints[edgeInd];
					//endpoint is child
					if(depth[endpoint] == depth[node]+1) {
						children[childrenCounters[node]++] = endpoint;
					}
					//endpoint is backEdge (check that its not parent or descendant)
					else if(depth[endpoint] < depth[node]-1) {
						backEdges[backEdgesCounters[node]++] = endpoint;
					}
				}
			}
			timer.lap("fill children and backEdges");
		}
		
		//for each node: count # children, count # back-edges, find parent, calculate depth
		//depth is needed to find out what is a back-edge
		static int bp1Interations;
		public void buildPart1(int node, Graph graph, HashSet<Integer> tree, int[] childrenCount, int[] backEdgeCount) {
			for(int edgeInd=graph.head[node]; edgeInd<(node==N-1 ? 2*graph.M : graph.head[node+1]); edgeInd++) {
				bp1Interations++;
				int endpoint = graph.endpoints[edgeInd];
				if(tree.contains(endpoint)) {
					if(parent[node] != endpoint && depth[endpoint] < depth[node]) backEdgeCount[node]++;
				}
				else {
					childrenCount[node]++;
					tree.add(endpoint);
					parent[endpoint] = node;
					depth[endpoint] = depth[node]+1;
					buildPart1(endpoint, graph, tree, childrenCount, backEdgeCount);
				}
			}
		}
	}
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
