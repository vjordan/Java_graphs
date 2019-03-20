package GraphAlgorithms;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedNode;
import AdjacencyList.UndirectedGraph;
import AdjacencyList.UndirectedNode;

public class DFS {

	public static void explorerSommet(UndirectedNode s, Set<UndirectedNode> a) {
		a.add(s);
		System.out.println(s);
		for (UndirectedNode t : s.getNeighbors()) {
			if (!a.contains(t)) {
				explorerSommet(t,a);
			}
		}
	}
	
	public static void explorerSommet(DirectedNode s, Set<DirectedNode> a) {
		a.add(s);
		System.out.println(s);
		for (DirectedNode t : s.getSuccs()) {
			if (!a.contains(t)) {
				explorerSommet(t,a);
			}
		}
	}
	
	public static void explorerGraphe(UndirectedGraph graph) {
		Set<UndirectedNode> atteint = new HashSet<UndirectedNode>();
		for (Object s : graph.getNodes()) {
			UndirectedNode sommet = (UndirectedNode) s;
			if (!atteint.contains(sommet)) {
				explorerSommet(sommet, atteint);
			}
		}
	}
	
	public static void explorerGraphe(DirectedGraph graph) {
		Set<DirectedNode> atteint = new HashSet<DirectedNode>();
		for (Object s : graph.getNodes()) {
			DirectedNode sommet = (DirectedNode) s;
			if (!atteint.contains(sommet)) {
				explorerSommet(sommet, atteint);
			}
		}
	}
	
	public static void parcoursProfondeurIteratif(UndirectedGraph graph, int s) {
		boolean[] mark = new boolean[graph.getNbNodes()];
		Queue<Integer> toVisit = new PriorityQueue<Integer>();
		
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
	
	public static void parcoursProfondeurIteratif(DirectedGraph graph, int s) {
		boolean[] mark = new boolean[graph.getNbNodes()];
		Queue<Integer> toVisit = new PriorityQueue<Integer>();
		
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
        
        System.out.println("\nDFS UndirectedGraph Itératif\n");
        DFS.parcoursProfondeurIteratif(al, 0);
        
        System.out.println("\nDFS UndirectedGraph Récursif\n");
        DFS.explorerGraphe(al);
        
        
        
        System.out.println("\n\n");
        
        
        
        int[][] mat2 = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        System.out.println("Affichage de la matrice orientée\n");
        GraphTools.AfficherMatrix(mat2);
        
        DirectedGraph al2 = new DirectedGraph(mat2);
        System.out.println("\nAffichage des successeurs\n");
        System.out.println(al2);
        
        System.out.println("\nDFS DirectedGraph Itératif\n");
        DFS.parcoursProfondeurIteratif(al2, 0);
        
        System.out.println("\nDFS DirectedGraph Récursif\n");
        DFS.explorerGraphe(al2);
	}
}
