package GraphAlgorithms;

import java.util.Stack;

import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedNode;

public class CFC {

	static DirectedGraph graph;

	public CFC(DirectedGraph al) {
		graph = al;
	}

	static void DFS(int v,boolean visited[],DirectedGraph graph)
	{
		visited[v] = true;
		System.out.print(graph.getNodes().get(v) + ", ");
		for (DirectedNode s : ((DirectedNode) graph.getNodes().get(v)).getSuccs()){
			if (!visited[s.getLabel()])
				DFS(s.getLabel(),visited,graph);
		}
	}

	void chercherFin(int v, boolean visited[], Stack stack)
	{
		visited[v] = true;
		for (DirectedNode s : ((DirectedNode) graph.getNodes().get(v)).getSuccs()){
			if(!visited[s.getLabel()])
				chercherFin(s.getLabel(), visited, stack);
		}
		stack.push(new Integer(v));
	}

	void ComposanteFortementConnexe()
	{
		Stack stack = new Stack();

		boolean visited[] = new boolean[graph.getNbNodes()];
		for(int i = 0; i < graph.getNbNodes(); i++)
			visited[i] = false;

		for (int i = 0; i < graph.getNbNodes(); i++)
			if (!visited[i])
				chercherFin(i, visited, stack);

		DirectedGraph gr = (DirectedGraph) graph.computeInverse();
		
		for (int i = 0; i < graph.getNbNodes(); i++)
			visited[i] = false;

		while (!stack.empty())
		{
			int v = (int)stack.pop();
			if (!visited[v])
			{
				System.out.print("Composante Fortement Connexe composée de : ");
				DFS(v, visited, gr);
				System.out.println();
			}
		}
	}

	public static void main(String args[])
	{			
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
        System.out.println("Affichage de la matrice :\n");
        GraphTools.AfficherMatrix(Matrix);
        
        DirectedGraph al = new DirectedGraph(Matrix);
        System.out.println("Affichage des successeurs :\n");
        System.out.println(al);
        
        System.out.println("Parcours en profondeur :\n");
        DFS.explorerGraphe(al);
        
        DirectedGraph alInv = (DirectedGraph)al.computeInverse();
        System.out.println("\nMatrice inversée :\n");
        System.out.println(alInv);
        
        System.out.println("COMPOSANTES FORTEMENT CONNEXES :\n");
        CFC cfc = new CFC(al);
		cfc.ComposanteFortementConnexe();
	}

}
