package GraphAlgorithms;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedNode;
import AdjacencyList.UndirectedGraph;
import AdjacencyList.UndirectedNode;

public class BFS {

	public static void parcoursLargeur(UndirectedGraph graph, int s) {
		boolean[] mark = new boolean[graph.getNbNodes()];
		Queue<Integer> toVisit = new ConcurrentLinkedQueue<Integer>();
		
		for (int i = 0; i<mark.length; i++) {
			mark[i] = false;
		}
		mark[s] = true;
		toVisit.add(s);
		while (!toVisit.isEmpty()){
			int v = toVisit.remove();
			System.out.println(graph.getNodes().get(v));
			for (UndirectedNode w : ((UndirectedNode)graph.getNodes().get(v)).getNeighbors()) {
				if (!mark[w.getLabel()]) {
					mark[w.getLabel()] = true;
					toVisit.add(w.getLabel());
				}
			}
			if(toVisit.isEmpty()){
				for(int i=0; i<graph.getNbNodes();i++){
					if(!mark[i]){
						mark[i] = true;
						toVisit.add(i);
						break;
					}
				}
			}
		}		
	}
	
	public static void parcoursLargeur(DirectedGraph graph, int s) {
		boolean[] mark = new boolean[graph.getNbNodes()];
		Queue<Integer> toVisit = new ConcurrentLinkedQueue<Integer>();
		
		for (int i = 0; i<mark.length; i++) {
			mark[i] = false;
		}
		mark[s] = true;
		toVisit.add(s);
		while (!toVisit.isEmpty()){
			int v = toVisit.remove();
			System.out.println(graph.getNodes().get(v));
			for (DirectedNode w : ((DirectedNode)graph.getNodes().get(v)).getSuccs()) {
				if (!mark[w.getLabel()]) {
					mark[w.getLabel()] = true;
					toVisit.add(w.getLabel());
				}
			}
			if(toVisit.isEmpty()){
				for(int i=0; i<mark.length;i++){
					if(!mark[i]){
						mark[i] = true;
						toVisit.add(i);
						break;
					}
				}
			}
		}		
	}
	
	public static void main(String[] args) {
		int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);        
        System.out.println("Affichage de la matrice non-orientée\n");
        GraphTools.AfficherMatrix(mat);
        
        UndirectedGraph al = new UndirectedGraph(mat);
        System.out.println("\nAffichage des voisins\n");
        System.out.println(al);
        
        System.out.println("\nBFS UndirectedGraph\n");
        BFS.parcoursLargeur(al, 0);
        
        
        
        System.out.println("\n\n");
        
        
        
        int[][] mat2 = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        System.out.println("Affichage de la matrice orientée\n");
        GraphTools.AfficherMatrix(mat2);
        
        DirectedGraph al2 = new DirectedGraph(mat2);
        System.out.println("\nAffichage des successeurs\n");
        System.out.println(al2);
        
        System.out.println("\nBFS DirectedGraph\n");
        BFS.parcoursLargeur(al2, 0);
	}
}
