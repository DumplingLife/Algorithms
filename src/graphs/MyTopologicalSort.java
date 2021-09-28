package graphs;

import java.util.HashSet;
import java.util.Stack;

public class MyTopologicalSort
{
	public static void main(String[] args) {
		Graph graph = new Graph(4);
		graph.addEdge(1, 0);
		graph.addEdge(1, 2);
		int[] res = graph.topSort();
		for(int i : res) {
			System.out.print(i + " ");
		}
	}
	
	static class Graph {
		Node[] nodes;
		class Node {
			int id;
			HashSet<Node> outEdges = new HashSet<>();
			HashSet<Node> inEdges = new HashSet<>();
			public Node(int id) {
				this.id = id;
			}
		}
		
		public Graph(int size) {
			nodes = new Node[size];
			for(int i = 0;i<size;i++) {
				nodes[i] = new Node(i);
			}
		}
		public void addEdge(int a, int b) {
			nodes[a].outEdges.add(nodes[b]);
			nodes[b].inEdges.add(nodes[a]);
		}
		//return topological sort, arbitrary tie-break
		public int[] topSort() {
			int[] res = new int[nodes.length];
			Stack<Node> settled = new Stack<>();
			for(Node node : nodes) {
				if(node.inEdges.isEmpty()) settled.add(node);
			}
			int resCounter = 0;
			while(!settled.isEmpty()) {
				Node node = settled.pop();
				res[resCounter] = node.id;
				resCounter++;
				//here, you could remove outEdges from nodes[nodeId], but this isn't neccessary
				//note that if you choose to do this, don't do it inside the loop, this will
				//error out because you modify the HashSet when it is doing a for-each loop
				for(Node endpoint : node.outEdges) {
					endpoint.inEdges.remove(node);
					if(endpoint.inEdges.isEmpty()) settled.add(endpoint);
				}
			}
			return res;
		}
	}
}

