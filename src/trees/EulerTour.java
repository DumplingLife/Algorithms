package trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class EulerTour
{
	public static void main(String[] args) {
		TempGraph graph = new TempGraph(5);
		graph.addEdge(0, 1);
		graph.addEdge(1, 2);
		graph.addEdge(1, 3);
		graph.addEdge(0, 4);
		Tree tree = new Tree(graph);
		tree.nodes[0].val = 10;
		tree.nodes[1].val = 11;
		tree.nodes[2].val = 12;
		tree.nodes[3].val = 13;
		tree.nodes[4].val = 14;
		
		tree.findSubtreeSizes();
		tree.eulerTour();
		
		int breakpoint = 0;
	}
	
	static class Tree
	{
		Node[] nodes;
		int[] eulerTourArr;
		int eulerTourArrCounter = 0;
		class Node
		{
			Node parent;
			HashSet<Node> children = new HashSet<>();
			int index;
			int val;
			int eulerTourStartInd = -1;
			int subtreeSize = 1;
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
		
		public void findSubtreeSizes() {
			findSubtreeSizesHelper(nodes[0]);
		}
		public void findSubtreeSizesHelper(Node node) {
			//remember: default is 1
			for(Node child : node.children) {
				findSubtreeSizesHelper(child);
				node.subtreeSize += child.subtreeSize;
			}
		}
		
		public void eulerTour() {
			eulerTourArr = new int[nodes.length];
			eulerTourHelper(nodes[0]);
		}
		private void eulerTourHelper(Node node) {
			eulerTourArr[eulerTourArrCounter] = node.val;
			node.eulerTourStartInd = eulerTourArrCounter;
			eulerTourArrCounter++;
			for(Node child : node.children) {
				eulerTourHelper(child);
			}
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
