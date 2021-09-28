package graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class MyDirectedDijkstra
{
	public static void main(String[] args) {
		DirectedDijkstraGraph dj = new DirectedDijkstraGraph(3);
		dj.addEdge(0,1,1);
		dj.addEdge(0,2,4);
		dj.addEdge(2,1,1);
		dj.dijkstra();
		//breakpoint
		int a = 0;
	}
}

//root is 0
class DirectedDijkstraGraph
{
	Node[] nodes;
	class Node implements Comparable<Node>
	{
		int id;
		int val = Integer.MAX_VALUE;
		ArrayList<Edge> outNeighbors;
		public Node(int id) {
			this.id = id;
			this.outNeighbors = new ArrayList<>();
		}
		public Node(Node node, int val) {
			this.id = node.id;
			this.val = val;
			this.outNeighbors = node.outNeighbors; //reference is fine, neighbors won't change
		}
		
		@Override
		public int compareTo(Node o) {
			return this.val - o.val;
		}
	}
	//not a full edge, meant to be used in neighbors in Node
	class Edge
	{
		int node;
		int weight;
		public Edge(int node, int weight) {
			this.node = node;
			this.weight = weight;
		}
	}
	
	public DirectedDijkstraGraph(int size) {
		nodes = new Node[size];
		for(int i = 0;i<nodes.length;i++) {
			nodes[i] = new Node(i);
		}
		nodes[0].val = 0;
	}
	public void addEdge(int a, int b, int weight) {
		nodes[a].outNeighbors.add(new Edge(b,weight));
	}
	
	//updates all nodes' val
	public void dijkstra() {
		/*
		Create PQ pq and HashSet settled
		Pull out lowest val node (and remove it)
			if this node is in HashSet, do nothing
				this will maintain correctness becuase the node is already settled, i.e.
				it was once the lowest val node, so this current path cannot make it
				lower value
				there are at most E extra nodes, O(E*log(V+E)) extra time
			else
				for each neighbor i of this node, edge weight w
					if val + w < i.val, update i's val and add it to PQ
				Add this node to settled
		
		*/
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(nodes[0]);
		HashSet<Integer> settled = new HashSet<Integer>();

		//alternatively, while pq size > 0, but this is tiny bit faster
		while(settled.size() < nodes.length)
		{
			Node node = pq.poll();
			if(settled.contains(node.id)) continue;
			
			//here, the same edge will be traversed twice, but it is harmless
			for(Edge edge : node.outNeighbors) {
				int newVal = node.val + edge.weight;
				if(newVal < nodes[edge.node].val) {
					//make a new node, can't change old node since its in pq
					nodes[edge.node] = new Node(nodes[edge.node], newVal);
					pq.add(nodes[edge.node]);
				}
			}
			settled.add(node.id);
		}
	}
}
