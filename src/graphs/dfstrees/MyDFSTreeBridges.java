/*
 * Added find bridges method and highest back-edge in descendents for Node
 */

package graphs.dfstrees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MyDFSTreeBridges {
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
			Node parent = null;
			HashSet<Node> children = new HashSet<>();
			HashSet<Node> backEdges = new HashSet<>();
			int index;
			
			int depth = -1;
			int smallestBackEdgeDepth = -1;
			
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
			findDepths(nodes[root]);
			findBridges(nodes[root]);
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
		
		//set every node's depth
		public void findDepths(Node node) {
			if(node.parent == null) node.depth = 0;
			else node.depth = node.parent.depth + 1;
				
			for(Node child : node.children) {
				findDepths(child);
			}
		}
		
		public void findBridges(Node node) {
			int smallestBackEdgeDepth = Integer.MAX_VALUE;
			for(Node child : node.children) {
				findBridges(child);
				smallestBackEdgeDepth = Math.min(smallestBackEdgeDepth, child.smallestBackEdgeDepth);
			}
			for(Node backEdgeEndpoint : node.backEdges) {
				smallestBackEdgeDepth = Math.min(smallestBackEdgeDepth, backEdgeEndpoint.depth);
			}
			
			node.smallestBackEdgeDepth = smallestBackEdgeDepth;
			if(node.parent != null && node.smallestBackEdgeDepth >= node.depth) {
				//there is a bridge between node and node.parent
				System.out.println("bridge: " + node.index + " " + node.parent.index);
			}
		}
	}
	
	static class Graph {
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
