package graphs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MyDFSTree {
	public static void main(String[] args) {
		Graph graph = new Graph(5);
		graph.addEdge(0, 1);
		graph.addEdge(1, 2);
		graph.addEdge(2, 3);
		graph.addEdge(3, 1);
		graph.addEdge(0, 4);
		DFSTree tree = new DFSTree(graph);
		int breakpoint = 0;
	}
	
	static class DFSTree {
		Node[] nodes;
		
		class Node {
			Node parent;
			HashSet<Node> children = new HashSet<>();
			HashSet<Node> backEdges = new HashSet<>();
			int index;
			public Node(int index) {
				this.index = index;
			}
		}
		
		public DFSTree(Graph graph) {
			final int root = 0;
			nodes = new Node[graph.nodes.length];
			for(int i = 0;i<nodes.length;i++) {
				nodes[i] = new Node(i);
			}
			
			buildHelper(root, graph, new HashSet<Integer>());
		}
		
		public void buildHelper(int node, Graph graph, HashSet<Integer> tree) {
			tree.add(node);
			for(int neighbor : graph.nodes[node].neighbors) {
				if(nodes[neighbor].children.contains(nodes[node])) {
					
				}
				else if(tree.contains(neighbor)) {
					nodes[node].backEdges.add(nodes[neighbor]);
				}
				else {
					nodes[node].children.add(nodes[neighbor]);
					nodes[neighbor].parent = nodes[node];
					buildHelper(neighbor, graph, tree);
				}
			}
		}
	}
	
	public static class Graph {
		Node[] nodes;
		class Node {
			HashSet<Integer> neighbors = new HashSet<>();
		}
		public Graph(int size) {
			nodes = new Node[size];
			for(int i = 0;i<size;i++) {
				nodes[i] = new Node();
			}
		}
		public void addEdge(int a, int b) {
			nodes[a].neighbors.add(b);
			nodes[b].neighbors.add(a);
		}
	}
}
