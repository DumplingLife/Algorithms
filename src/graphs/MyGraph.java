package graphs;

import java.util.HashSet;

public class MyGraph {
	static class Graph {
		Node[] nodes;
		class Node {
			HashSet<Node> neighbors = new HashSet<>();
		}
		public Graph(int size) {
			nodes = new Node[size];
			for(int i = 0;i<size;i++) {
				nodes[i] = new Node();
			}
		}
		public void addEdge(int a, int b) {
			nodes[a].neighbors.add(nodes[b]);
			nodes[b].neighbors.add(nodes[a]);
		}
	}
}
