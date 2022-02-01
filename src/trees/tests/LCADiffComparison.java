package trees.tests;

import java.util.ArrayList;

import test.MyTimer;
import trees_diff.MyLCATreeDiff.LCATreeDiff;
import trees.MyLCATree.Tree;
import trees.MyLCATree.TempGraph;

public class LCADiffComparison {
	public static void main(String[] args) {
		int N = 1000000;
		TempGraph graph = new TempGraph(N);
		ArrayList<Integer>[] adjLists = new ArrayList[N];
		for(int i=0; i<N; i++) {
			adjLists[i] = new ArrayList<>();
		}
		for(int i=1; i<N; i++) {
			int j = (int) (Math.random() * i);
			adjLists[i].add(j);
			adjLists[j].add(i);
			graph.addEdge(i, j);
		}

		MyTimer timer = new MyTimer();
		timer.pause();
		MyTimer timerDiff = new MyTimer();
		timerDiff.pause();
		Tree tree = new Tree(graph, new int[N]);
		LCATreeDiff treeDiff = new LCATreeDiff(adjLists);
		for(int i=0; i<1000000; i++) {
			int a = (int) (Math.random() * N);
			int b = (int) (Math.random() * N);
			timerDiff.resume();
			int x = treeDiff.findLCA(a, b);
			timerDiff.pause();
			
			timer.resume();
			int y = tree.findLCA(a, b);
			timer.pause();
			
			if(x != y) {
				System.out.println(a + " " + b + " " + tree.findLCA(a, b) + " " + treeDiff.findLCA(a, b));
			}
		}
		
		timer.lap();
		timerDiff.lap();
	}
}
