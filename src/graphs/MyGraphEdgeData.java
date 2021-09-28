package graphs;

import java.util.HashSet;

public class MyGraphEdgeData {
	
	static class GraphEdgeData {
		Node[] nodes;
		public GraphEdgeData(int size) {
			nodes = new Node[size];
		}
		public void addEdge(int a, int b, int data) {
			nodes[a].edges.add(new Edge(nodes[b], data));
			nodes[b].edges.add(new Edge(nodes[a], data));
		}
		
		class Edge {
			Node endpoint;
			int data;
			public Edge(Node node, int data) {
				this.endpoint = node;
				this.data = data;
			}
		}
		class Node {
			HashSet<Edge> edges = new HashSet<>();
		}
	}
}
