package graphs.dijkstras;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class MyDijkstra {
	public static void main(String[] args) {
		Graph graph = new Graph(4);
		graph.addEdge(0,1,10);
		graph.addEdge(1,2,10);
		graph.addEdge(2,3,10);
		graph.addEdge(0,3,20);
		graph.dijkstra();
	}
	
	public static class Graph {
		public Node[] nodes;
		public class Node implements Comparable<Node> {
			int id;
			public int val = Integer.MAX_VALUE;
			ArrayList<Edge> neighbors;
			public Node(int id) {
				this.id = id;
				this.neighbors = new ArrayList<>();
			}
			public Node(Node node, int val) {
				this.id = node.id;
				this.val = val;
				this.neighbors = node.neighbors; //reference is fine, neighbors won't change
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
		
		public Graph(int size) {
			nodes = new Node[size];
			for(int i = 0;i<nodes.length;i++) {
				nodes[i] = new Node(i);
			}
			nodes[0].val = 0;
		}
		public void addEdge(int a, int b, int weight) {
			nodes[a].neighbors.add(new Edge(b,weight));
			nodes[b].neighbors.add(new Edge(a,weight));
		}
		
		public void dijkstra() {
			PriorityQueue<Node> pq = new PriorityQueue<>();
			pq.add(nodes[0]);
			HashSet<Integer> settled = new HashSet<Integer>();

			while(pq.size() > 0)
			{
				Node node = pq.poll();
				if(settled.contains(node.id)) continue;
				
				for(Edge edge : node.neighbors) {
					int newVal = node.val + edge.weight;
					if(newVal < nodes[edge.node].val) {
						nodes[edge.node] = new Node(nodes[edge.node], newVal);
						pq.add(nodes[edge.node]);
					}
				}
				settled.add(node.id);
			}
		}
		
		public void reset() {
			for(int i = 0;i<nodes.length;i++) {
				nodes[i].val = Integer.MAX_VALUE;
			}
			nodes[0].val = 0;
		}
	}
}

