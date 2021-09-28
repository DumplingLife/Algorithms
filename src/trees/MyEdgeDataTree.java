package trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class MyEdgeDataTree
{
	public static void main(String[] args) {
		TempGraph graph = new TempGraph(3);
		graph.addEdge(0, 1, 1);
		graph.addEdge(1, 2, 2);
		Tree tree = new Tree(graph);
		int a = 0;
	}
	
	static class Tree {
		Node[] nodes;
		class Node {
			Edge parent;
			HashSet<Edge> children = new HashSet<Edge>();
			int index;
			public Node(int index) {
				this.index = index;
			}
		}
		class Edge {
			Node endpoint;
			int data;
			public Edge(Node endpoint, int data) {
				this.data = data;
				this.endpoint = endpoint;
			}
		}
		
		public Tree(TempGraph graph) {
			final int root = 0;
			nodes = new Node[graph.nodes.length];
			for(int i = 0;i<nodes.length;i++) {
				nodes[i] = new Node(i);
			}
			
			Queue<Integer> queue = new LinkedList<>();
			HashSet<Integer> processed = new HashSet<>();
			queue.add(root);
			
			while(!queue.isEmpty()) {
				int node = queue.poll();
				for(TempGraph.Edge edge : graph.nodes[node].neighbors) {
					int child = edge.endpoint;
					
					if(processed.contains(child)) continue;
					
					nodes[node].children.add(new Edge(nodes[child], edge.data));
					nodes[child].parent = new Edge(nodes[node], edge.data);
					queue.add(child);
				}
				processed.add(node);
			}
		}
	}
	static class TempGraph {
		Node[] nodes;
		class Node{
			HashSet<Edge> neighbors = new HashSet<Edge>();
		}
		class Edge {
			int endpoint;
			int data;
			public Edge(int endpoint, int data) {
				this.data = data;
				this.endpoint = endpoint;
			}
		}
		
		public TempGraph(int size) {
			nodes = new Node[size];
			for(int i = 0;i<nodes.length;i++) {
				nodes[i] = new Node();
			}
		}
		public void addEdge(int i, int j, int data) {
			nodes[i].neighbors.add(new Edge(j, data));
			nodes[j].neighbors.add(new Edge(i, data));
		}
	}

}

