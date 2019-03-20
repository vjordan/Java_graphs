package AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Abstraction.IUndirectedGraph;


public class UndirectedGraph<A extends UndirectedNode> extends AbstractListGraph<A> implements IUndirectedGraph<A> {


    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	public UndirectedGraph() {
		 this.nodes = new ArrayList<>();
	}
	
	public UndirectedGraph(List<A> nodes) {
        super(nodes);
        for (UndirectedNode i : nodes) {
            this.m += i.getNbNeigh();
        }
    }

    public UndirectedGraph(int[][] matrix) { 	
    	super();
    	this.order = matrix.length;
    	for (int i=0; i<matrix.length; i++) {
    		this.nodes.add(makeNode(i));
    	}
    	for (int i=0; i<matrix.length; i++) {
    		for (int j=0; j<matrix.length; j++) {
    			if (matrix[i][j] > 0) {
    				this.m++;
    				this.nodes.get(i).addNeighbors(this.nodes.get(j));
    			}
    		}
    	}
    }

    public UndirectedGraph(UndirectedGraph<A> g) {
    	super();
        this.nodes = g.getNodes();
        for (int node = 0; node < g.getNbNodes(); node++) {
            for (int arc = 0; arc < g.getNbNodes(); arc++) {
                this.addEdge(this.nodes.get(node), g.getNeighbors(this.nodes.get(node)).get(arc));
            }
        }

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    @Override
    public int getNbEdges() {
        return this.m;
    }    

    @Override
    public List<A> getNeighbors(A x) {
        return this.getNodes().get(x.getLabel()).getNeighbors().stream().map(n -> (A)n).collect(Collectors.toList());
    }
    
    //--------------------------------------------------
    // 					Methods
    //--------------------------------------------------
    
    @Override
    public boolean isEdge(A x, A y) {
    	return nodes.get(x.getLabel()).getNeighbors().contains(y);
    }

    @Override
    public void removeEdge(A x, A y) {
    	nodes.get(x.getLabel()).getNeighbors().remove(y);
    	nodes.get(y.getLabel()).getNeighbors().remove(x);
    	this.m = this.m--;
    }

    @Override
    public void addEdge(A x, A y) {
    	nodes.get(x.getLabel()).getNeighbors().add(y);
    	nodes.get(y.getLabel()).getNeighbors().add(x);
    	this.m = this.m++;
    }
    
    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends UndirectedNode
     */
    @Override
    public A makeNode(int label) {
        return (A) new UndirectedNode(label);
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
    	int[][] mat = new int[this.order][this.order];
        for (int i = 0; i < this.order; i++) {
            A from = this.getNodes().get(i);
            for (int j = 0; j < this.order; j++) {
                A to = this.getNodes().get(j);
                if (isEdge(from, to)) {
                	mat[i][j] = 1;
                }
                else {
                	mat[i][j] = 0;
                }
            }
        }
        return mat;
    }

    
    @Override
    public String toString() {
        String s = "";
        for (UndirectedNode n : nodes) {
            s += "neighbors of " + n + " : ";
            for (UndirectedNode sn : n.getNeighbors()) {
                s += sn + " ";
            }
            s += "\n";
        }
        s += "\n";
        return s;
    }


    public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
        System.out.println("Affichage de la matrice\n");
        GraphTools.AfficherMatrix(mat);
        
        UndirectedGraph al = new UndirectedGraph(mat);
        System.out.println("\nAffichage des voisins\n");
        System.out.println(al);
        
        al.addEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println("\nAjout de l'arête 2-5\n");
        System.out.println(al);
        
        System.out.println("\nVérification présence arête\n");
        System.out.println(al.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
        
        al.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println("\nSuppression de l'arête 2-5\n");
        System.out.println(al);
        
        System.out.println("\nVérification présence arête\n");
        System.out.println(al.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
    }
}
