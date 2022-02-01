/*
 * Template tree class created with an underlying graph in O(N)
 * Rewritten (pretty different from MyTree)
 * Each node has a value (not each edge)
 * 
 * use addEdge to add edges, each call will add 2 edges
 * 	i.e., addEdge(i,j,k) is the same as addEdge(j,i,k)
 * 
 * Customize
 * 	Specify node data type and function
 * 
 */

package trees;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class MyLCATree
{
	public static void main(String[] args) {
		TempGraph graph = new TempGraph(5);
		graph.addEdge(0, 1);
		graph.addEdge(1, 2);
		graph.addEdge(1, 3);
		graph.addEdge(3, 4);
		int[] data = {3, 4, 5, 6, 7};
		Tree tree = new Tree(graph, data);
		int lca = tree.findLCA(4, 3);
		int a = 0;
	}
	
	public static class Tree
	{
		Node[] nodes;
		int log2H;
		class Node
		{
			int[] ancestors;
			HashSet<Integer> children = new HashSet<Integer>();
			int value;
			int index; //debugging purposes
			int depth;
			public Node(int index, int value) {
				//this bound is correct, don't need to add 1
				log2H = log2ceil(nodes.length);
				ancestors = new int[log2H];
				Arrays.fill(ancestors, -1);
				
				this.index = index;
				this.value = value;
			}
		}
		
		public Tree(TempGraph graph, int[] values) {
			final int root = 0;
			nodes = new Node[graph.nodes.length];
			for(int i = 0;i<nodes.length;i++) {
				nodes[i] = new Node(i, values[i]);
			}
			
			//create the tree
			Queue<Integer> queue = new LinkedList<>();
			HashSet<Integer> processed = new HashSet<>();
			queue.add(root);
			while(!queue.isEmpty()) {
				int node = queue.poll();
				for(int child : graph.nodes[node].neighbors) {
					//the node is actually the parent
					if(processed.contains(child)) continue;
					
					nodes[node].children.add(child);
					nodes[child].ancestors[0] = node;
					nodes[child].depth = nodes[node].depth + 1;
					queue.add(child);
				}
				processed.add(node);
			}
			
			//create the ancestors sparse table
			queue = new LinkedList<>();
			queue.add(root);
			while(!queue.isEmpty()) {
				int node = queue.poll();
				for(int child : nodes[node].children) {
					Node childNode = nodes[child];
					for(int i = 1;i<=log2floor(childNode.depth);i++) {
						childNode.ancestors[i]
								= nodes[childNode.ancestors[i-1]].ancestors[i-1];
					}
					queue.add(child);
				}
			}
		}
		
		public int findLCA(int a, int b) {
			//if either is root (this will error out)
			if(nodes[a].depth == 0 || nodes[b].depth == 0) return 0;
			
			//make the nodes the same height
			while(nodes[a].depth > nodes[b].depth) {
				a = nodes[a].ancestors[log2floor(nodes[a].depth - nodes[b].depth)];
			}
			while(nodes[b].depth > nodes[a].depth) {
				b = nodes[b].ancestors[log2floor(nodes[b].depth - nodes[a].depth)];
			}
			//if they are the same, return (found LCA already)
			if(a == b) return a;
			
			//iterate down, which will make the final result the LCA's immediate children
			for(int i = log2floor(nodes[a].depth);i>=0;i--) {
				if(nodes[a].ancestors[i] != nodes[b].ancestors[i]) {
					a = nodes[a].ancestors[i];
					b = nodes[b].ancestors[i];
				}
			}
			return nodes[a].ancestors[0];
		}
		
		//customize
		private int f(int a, int b) {
			return a+b;
		}
		
		//util
		private int log2ceil(int x) {
			if((x & (x - 1)) == 0)
				return 31 - Integer.numberOfLeadingZeros(x);
			else
				return 32 - Integer.numberOfLeadingZeros(x);
		}
		private int log2floor(int x) {
			return 31 - Integer.numberOfLeadingZeros(x);
		}
	}
	//temporary graph to create a tree from
	//stores nodes as int indexes
	public static class TempGraph
	{
		Node[] nodes;
		class Node{
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

