package test;

import java.io.File;
import java.io.IOException;

import trees.MyFastTree.Graph;
import trees.MyFastTree.Tree;

public class TestFastTree {
	public static void main(String[] args) throws IOException {
		File file = new File("tree5M.in"); 
		MyReader reader = new MyReader(file);
		
		int N = reader.nextInt();
		MyTimer timer = new MyTimer();
		
		
		int[] arr1 = new int[N-1], arr2  = new int[N-1];
		for(int i=0; i<N-1; i++) {
			arr1[i] = reader.nextInt()-1;
			arr2[i] = reader.nextInt()-1;
		}
		timer.lap("read");
		
		Graph graph = new Graph(N,N-1,arr1, arr2);
		timer.lap("create graph");
		
		/*
		TempGraph graph = new TempGraph(N);
		for(int i=0; i<N-1; i++) {
			graph.addEdge(reader.nextInt()-1, reader.nextInt()-1);
		}
		*/
		
		Tree tree = new Tree(graph);
		timer.lap("create tree");
		
		int breakpoint = 0;
	}
}
