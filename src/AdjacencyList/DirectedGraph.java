package AdjacencyList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Abstraction.IDirectedGraph;


public class DirectedGraph<A extends DirectedNode> extends AbstractListGraph<A> implements IDirectedGraph<A> {

	//private static int _DEBBUG =1;
	private static int _DEBBUG =0;
		
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	public DirectedGraph(){
		super();
		this.nodes = new ArrayList<A>();
	}

    public DirectedGraph(int[][] matrix) {
    	this.order = matrix.length;
    	this.nodes = new ArrayList();
    	for(int i = 0; i<this.order; i++) {
    		this.nodes.add(i, this.makeNode(i));
    	}
    	for (A n : this.getNodes()) {
    		for(int j = 0; j<this.order; j++) {
    			A nn = this.getNodes().get(j);
    			if (matrix[n.getLabel()][j] != 0) {
    				n.getSuccs().add(nn);
    				n.getPreds().add(n);
    				this.m++;
    			}
    		}
    	}
    }

    public DirectedGraph(DirectedGraph<A> g) {
    	super();
        this.nodes = g.getNodes();
        for (int node = 0; node < g.getNbNodes(); node++) {
            List<A> successors = g.getSuccessors(g.getNodes().get(node));
            List<A> predecessors = g.getPredecessors(g.getNodes().get(node));
            for (int arc = 0; arc < successors.size(); arc++) {
                this.addArc(predecessors.get(arc), successors.get(arc));
            }
        }
    }

    // ------------------------------------------
    // 		Accessors
    // ------------------------------------------
    @Override
    public int getNbArcs() {
        return this.m;
    }

    @Override
    public List<A> getSuccessors(A x) {
        return this.getNodes().get(x.getLabel()).getSuccs().stream().map(n -> (A)n).collect(Collectors.toList());
    }

    @Override
    public List<A> getPredecessors(A x) {
        return this.getNodes().get(x.getLabel()).getPreds().stream().map(n -> (A)n).collect(Collectors.toList());
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------
    
    @Override
    public boolean isArc(A from, A to) {
    	return nodes.get(from.getLabel()).getSuccs().contains(to);
    }

    @Override
    public void removeArc(A from, A to) {
    	nodes.get(from.getLabel()).getSuccs().remove(to);
    	this.m--;
    }

    @Override
    public void addArc(A from, A to) {
    	nodes.get(from.getLabel()).getSuccs().add(to);
    	this.m++;
    }
    
    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends DirectedNode
     */
    @Override
    public A makeNode(int label) {
        return (A)new DirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public A getNodeOfList(A src) {
        return this.getNodes().get(src.getLabel());
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
                if (isArc(from, to)) {
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
    public IDirectedGraph computeInverse() {
    	int[][] matrix = this.toAdjacencyMatrix();
        int[][] matInv = new int[this.order][this.order];
        int tmp;
        for (int i = 0; i < this.order; i++) {
            for (int j = i; j < this.order; j++) {
                tmp = matrix[i][j];
                matInv[i][j] = matrix[j][i];
                matInv[j][i] = tmp;
            }
        }
        return new DirectedGraph(matInv);
    }
    
 	
    @Override
    public String toString(){
        String s = "";
        for(DirectedNode n : nodes){
            s+="successors of "+n+" : ";
            for(DirectedNode sn : n.getSuccs()){
                s+=sn+" ";
            }
            s+="\n";
        }
        s+="\n";
        return s;
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        System.out.println("Affichage de la matrice\n");
        GraphTools.AfficherMatrix(Matrix);
        
        DirectedGraph al = new DirectedGraph(Matrix);
        System.out.println("\nAffichage des successeurs\n");
        System.out.println(al);
        
        System.out.println("\nNombre d'arcs\n");
        System.out.println(al.getNbArcs());
        
        al.addArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println("\nAjout de l'arc 2->5\n");
        System.out.println(al);
        
        System.out.println("\nVérification présence arc\n");
        System.out.println(al.isArc(new DirectedNode(2), new DirectedNode(5)));
        
        al.removeArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println("\nSuppression de l'arc 2->5\n");
        System.out.println(al);
        
        System.out.println("\nVérification présence arc\n");
        System.out.println(al.isArc(new DirectedNode(2), new DirectedNode(5)));
        
        DirectedGraph alInv = (DirectedGraph)al.computeInverse();
        System.out.println("\nMatrice inversée\n");
        System.out.println(alInv);      
       
    }
}
