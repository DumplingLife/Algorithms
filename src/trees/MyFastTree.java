package trees;

import java.util.LinkedList;
import java.util.Queue;

import test.MyTimer;

public class MyFastTree {
	public static void main(String[] args) {
		Graph graph = new Graph(5,4,new int[] {0,0,0,1}, new int[] {1,2,3,4});
		Tree tree = new Tree(graph);
		int breakpoint = 0;
	}
	
	public static class Tree {
		int N;
		int[] parent;
		int[] children;
		int[] head;
		public Tree(Graph graph) {
			N = graph.N;
			parent = new int[N];
			children = new int[N-1];
			head = new int[N];
			
			int[] numChildren = new int[N];
			for(int ele : graph.endpoints) {
				numChildren[ele]++;
			}
			for(int i=1; i<N; i++) {
				numChildren[i]--;
			}
			
			int[] nodeChildrenCounters = new int[N];
			head[0] = 0;
			for(int i=1; i<N; i++) {
				head[i] = head[i-1] + numChildren[i-1];
				nodeChildrenCounters[i] = head[i];
			}
			
			for(int i=0; i<N; i++) {
				parent[i] = -1;
			}
			
			int[] queue = new int[N];
			int queueGetCounter = 0;
			int queueSetCounter = 1;
			queue[0] = 0;
			while(queueGetCounter < N) {
				int node = queue[queueGetCounter++];
				
				for(int edgeInd=graph.head[node]; edgeInd<(node==N-1 ? 2*graph.M : graph.head[node+1]); edgeInd++) {
					int endpoint = graph.endpoints[edgeInd];
					if(parent[node] == endpoint) continue;
					
					parent[endpoint] = node;
					children[nodeChildrenCounters[node]++] = endpoint;
					
					queue[queueSetCounter++] = endpoint;
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
