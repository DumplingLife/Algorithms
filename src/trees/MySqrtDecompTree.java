/*
 * Nodes have data
 * 
 * currently set to check if a value exists
 */

package trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;


public class MySqrtDecompTree {
	public static void main(String[] args) {
		TempGraph graph = new TempGraph(100000);
		for(int i = 0;i<=99998;i++) {
			graph.addEdge(i, i+1);
		}

		int[] data = new int[100000];
		Tree tree = new Tree(graph, data);
		
		boolean temp = tree.query(2, 5, 7);
		
		int a = 0;
	}
	
	
	static class Tree
	{
		final int sqrtH = 100;
		Node[] nodes;
		class Node
		{
			Node parent;
			HashSet<Node> children = new HashSet<>();
			int index;
			int data;
			int depth;
			
			Node sqrtParent = null;
			HashSet<Integer> value = null;
			
			public Node(int index, int data) {
				this.index = index;
				this.data = data;
			}
			@Override
			public String toString() {
				String valueString;
				if(value == null) valueString = "-";
				else valueString = value.toString();
				return String.format("%s %s %s", index, data, valueString);
			}
		}
		
		//create structure and build
		public Tree(TempGraph graph, int[] data) {
			final int root = 0;
			nodes = new Node[graph.nodes.length];
			for(int i = 0;i<nodes.length;i++) {
				nodes[i] = new Node(i, data[i]);
			}
			nodes[root].depth = 0;
			
			Queue<Integer> queue = new LinkedList<>();
			HashSet<Integer> processed = new HashSet<>();
			queue.add(root);
			
			while(!queue.isEmpty()) {
				int node = queue.poll();
				for(int child : graph.nodes[node].neighbors) {
					//the node is actually the parent
					if(processed.contains(child)) continue;
					
					Node nodeTemp = nodes[node];
					Node childTemp = nodes[child];
					nodeTemp.children.add(childTemp);
					childTemp.parent = nodeTemp;
					childTemp.depth = nodeTemp.depth + 1;
					if(childTemp.depth % sqrtH == 0) {
						buildNode(childTemp);
					}
					queue.add(child);
				}
				processed.add(node);
			}
			
		}
		
		//calculate value and sqrt parent for a single sqrt node by traversing up
		private void buildNode(Node node) {
			HashSet<Integer> value = new HashSet<Integer>();
			Node originalNode = node;
			for(int i = 0;i<sqrtH;i++) {
				value.add(node.data);
				node = node.parent;
			}
			originalNode.value = value;
			originalNode.sqrtParent = node;
		}
	
		public boolean query(int uIndex, int vIndex, int x) {
			Node u = nodes[uIndex];
			Node v = nodes[vIndex];
			
			//get u and v onto same depth
			while(u.depth > v.depth) {
				if(u.depth % sqrtH == 0 && u.sqrtParent.depth >= v.depth) {
					if(u.value.contains(x)) return true;
					u = u.sqrtParent;
				}
				else {
					if(u.data == x) return true;
					u = u.parent;
				}
			}
			while(v.depth > u.depth) {
				if(v.depth % sqrtH == 0 && v.sqrtParent.depth >= u.depth) {
					if(v.value.contains(x)) return true;
					v = v.sqrtParent;
				}
				else {
					if(v.data == x) return true;
					v = v.parent;
				}
			}
			
			//finish
			//compare references is okay
			while(u != v) {
				if(u.depth % sqrtH == 0 && u.sqrtParent != v.sqrtParent) {
					if(u.value.contains(x) || v.value.contains(x)) return true;
					u = u.sqrtParent;
					v = v.sqrtParent;
				}
				else {
					if(u.data == x || v.data == x) return true;
					u = u.parent;
					v = v.parent;
				}
			}
			if(u.data == x) return true;
			return false;
		}
	}
	
	static class TempGraph
	{
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
