
package generatefile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

public class GenerateGraph
{
	public static void main(String[] args) throws IOException {
		final int N = 2_250_000;
		final int M = 9_000_000;
		
		BufferedWriter writer = new BufferedWriter(new PrintWriter("graph2M.in", "UTF-8"), 10000000);
		writer.write(N + " " + M + "\n");
		//track all edges so that there are no 2 edges that are the same (i.e. its a simple graph)
		HashSet<Edge> set = new HashSet<Edge>();
		//generate random tree
		for(int i=2; i<=N; i++) {
			int a = random(1, i-1);
			set.add(new Edge(i, a));
		}
		
		//add random edges
		for(int i=0; i<M-N+1; i++) {
			//generate random a, b such that a doesn't equal b
			int a, b;
			do {
				a = random(1, N);
				b = random(1, N);
			} while(a == b || set.contains(new Edge(a,b)));
			set.add(new Edge(a,b));
		}
		int m = 0;
		for(Edge e : set) {
			m++;
			if(e.a == e.b) System.out.println("ERROR");
			writer.write(e.a + " " + e.b + " " + random(1, 1000) + "\n");
		}
		System.out.println(m);
		writer.flush();
		writer.close();
	}
	public static int random(int lower, int upper) {
		return (int) (Math.floor(Math.random() * (upper - lower + 1)) + lower);
	}
	
	static class Edge {
		int a, b;
		public Edge(int a, int b) {
			this.a = a;
			this.b = b;
		}
		@Override
		public int hashCode() {
			return a+b;
		}
		@Override
		public boolean equals(Object o) {
			Edge e = (Edge) o;
			return (a == e.a && b == e.b) || (a == e.b && b == e.a);
		}
	}
}
