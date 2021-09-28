package graphs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import test.MyReader;
import test.MyTimer;

public class MyDijkstra {
	public static void main(String[] args) throws NumberFormatException, IOException {
		File file = new File("graph2M.in"); 
		MyReader reader = new MyReader(file);
		int N = reader.nextInt();
		int M = reader.nextInt();
		int[] arr1 = new int[M];
		int[] arr2 = new int[M];
		int[] arr3 = new int[M];
		
		MyTimer timer = new MyTimer();
		for(int i=0; i<M; i++) {
			arr1[i] = reader.nextInt()-1;
			arr2[i] = reader.nextInt()-1;
			arr3[i] = reader.nextInt();
		}
		System.out.print("read from file: ");
		timer.lap();
		
		Graph dj = new Graph(N);
		for(int i=0; i<M; i++) {
			dj.addEdge(arr1[i], arr2[i], arr3[i]);
		}

		System.out.print("create graph: ");
		timer.lap();
		
		dj.dijkstra();

		System.out.print("dijkstra: ");
		timer.lap();
	}
	
	public static class Graph {
		Node[] nodes;
		class Node implements Comparable<Node> {
			int id;
			int val = Integer.MAX_VALUE;
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

