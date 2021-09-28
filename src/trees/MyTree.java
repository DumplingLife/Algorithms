/*
 * Template tree class created with an underlying graph in O(N)
 */

package trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;


public class MyTree {
	public static void main(String[] args) {
		TempGraph graph = new TempGraph(3);
		graph.addEdge(0, 1);
		graph.addEdge(1, 2);
		Tree tree = new Tree(graph);
		int a = 0;
	}
	
	
	public static class Tree {
		Node[] nodes;
		class Node {
			Node parent;
			HashSet<Node> children = new HashSet<>();
			int index;
			public Node(int index) {
				this.index = index;
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
				for(int child : graph.nodes[node].neighbors) {
					//the node is actually the parent
					if(processed.contains(child)) continue;
					
					nodes[node].children.add(nodes[child]);
					nodes[child].parent = nodes[node];
					queue.add(child);
				}
				processed.add(node);
			}
		}
	}
	public static class TempGraph {
		Node[] nodes;
		class Node {
			HashSet<Integer> neighbors = new HashSet<>();
		}
		
		public TempGraph(int size) {
			nodes = new Node[size];
			for(int i = 0;i<nodes.length;i++) {
				nodes[i] = new Node();
			}
		}
		public void addEdge(int i, int j) {
			nodes[i].neighbors.add(j);
			nodes[j].neighbors.add(i);
		}
	}
}


