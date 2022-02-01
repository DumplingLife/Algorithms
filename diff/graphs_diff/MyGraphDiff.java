/*
 * not tested
 * almost exact same as original graph, except renamed size to N
 */

package graphs_diff;

import java.util.HashSet;

public class MyGraphDiff {
	public static void main(String[] args) {
		GraphDiff graph = new GraphDiff(5);
		graph.addEdge(0, 1);
	}
	
	public static class GraphDiff {
		Node[] nodes;
		class Node {
			HashSet<Node> neighbors = new HashSet<>();
		}
		public GraphDiff(int N) {
			nodes = new Node[N];
			for(int i=0; i<N; i++) {
				nodes[i] = new Node();
			}
		}
		public void addEdge(int a, int b) {
			nodes[a].neighbors.add(nodes[b]);
			nodes[b].neighbors.add(nodes[a]);
		}
	}
}
