package graphs.dijkstras;

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
	
	static class DirectedDijkstraGraph {
		Node[] nodes;
		class Node implements Comparable<Node> {
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
		class Edge {
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
		
		public void dijkstra() {		
			PriorityQueue<Node> pq = new PriorityQueue<>();
			pq.add(nodes[0]);
			HashSet<Integer> settled = new HashSet<Integer>();

			while(settled.size() < nodes.length)
			{
				Node node = pq.poll();
				if(settled.contains(node.id)) continue;
				
				for(Edge edge : node.outNeighbors) {
					int newVal = node.val + edge.weight;
					if(newVal < nodes[edge.node].val) {
						nodes[edge.node] = new Node(nodes[edge.node], newVal);
						pq.add(nodes[edge.node]);
					}
				}
				settled.add(node.id);
			}
		}
	}
}

