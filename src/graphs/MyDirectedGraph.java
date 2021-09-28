package graphs;

import java.util.HashSet;

public class MyDirectedGraph {
	
	static class DirectedGraph {
		Node[] nodes;
		class Node {
			HashSet<Node> out = new HashSet<>();
		}
		public DirectedGraph(int size) {
			nodes = new Node[size];
			for(int i = 0;i<size;i++) {
				nodes[i] = new Node();
			}
		}
		public void addEdge(int a, int b) {
			nodes[a].out.add(nodes[b]);
		}
	}
}
