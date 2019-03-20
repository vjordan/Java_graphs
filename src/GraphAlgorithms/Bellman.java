package GraphAlgorithms;

import java.util.Arrays;

import AdjacencyList.DirectedNode;
import AdjacencyList.DirectedValuedGraph;

public class Bellman {
	
	public static void bellman (DirectedValuedGraph graph, int s) {
		int n = graph.getNbNodes();
		int[] d = new int[n];
		int[] d2 = new int[n];
		int[] p = new int[n];
		
		for (int i = 0; i < n; i++) {
			d[i] = Integer.MAX_VALUE;
			p[i] = -1;
		}
		d[s] = 0;
		p[s] = s;
		boolean fini = false;
		int k = 0;
		
		while(!fini && k < n-1) {
			fini = true;
			for (int i = 0; i < n; i++) {
				d2[i] = d[i];
				DirectedNode noeudI = graph.getNodes().get(i);
				int compteur = 0;
				for (DirectedNode j : noeudI.getPreds()) {
					if ((d[j.getLabel()] < Integer.MAX_VALUE) &&
							(d[j.getLabel()]+noeudI.getCostsInv().get(compteur) < d2[i])) {
						d2[i] = d[j.getLabel()]+noeudI.getCostsInv().get(compteur);
						p[i] = j.getLabel();
						fini = false;
					}
					compteur++;
				}
			}
			k++;
			d = Arrays.copyOf(d2, n);
		}
		
		if (!fini) {
			boolean cycle = false;
			for (int i = 0; i < n; i++) {
				DirectedNode noeudI = graph.getNodes().get(i);
				int compteur = 0;
				for (DirectedNode j : noeudI.getPreds()) {
					if (d[j.getLabel()]+noeudI.getCostsInv().get(compteur) < d[i]) {
						cycle = true;
						break;
					}
					compteur++;
				}
				if (cycle) {
					System.out.println("erreur");
					break;
				}
			}
		}
		System.out.println("Résultat Bellman : \n");
		for (int i = 0; i < n; i++) {
			System.out.println(i + "   " + d[i] + "   " + p[i]);
		}
	}

	public static void main(String[] args) {
		int[][] mat = GraphTools.generateValuedGraphData (10, false, false, true, true, 100007);
		System.out.println("Affichage de la matrice\n");
        GraphTools.AfficherMatrix(mat);
        
        DirectedValuedGraph graph = new DirectedValuedGraph(mat);
        System.out.println("\nAffichage des successeurs/prédecesseurs\n");
        System.out.println(graph);
        
        System.out.println("\nBellman\n");
        Bellman.bellman(graph, 0);
	}
}