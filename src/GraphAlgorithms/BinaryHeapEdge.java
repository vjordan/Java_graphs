package GraphAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Abstraction.Triple;
import AdjacencyList.DirectedNode;


public class BinaryHeapEdge<A extends Object> {

	private  List<Triple<A,A,Integer>> nodes;

    public BinaryHeapEdge() {
        this.nodes = new ArrayList<Triple<A,A,Integer>>();
    }

    public boolean isEmpty() {
       return nodes.size() == 0;
    }

    public void insert(A from, A to, int val) {
    	Triple triple = new Triple(from,to,val);
    	this.nodes.add(triple);
    	int i = nodes.size()-1;
    	while (i > 0 && val < (this.nodes.get((int)Math.floor((i-1)/2)).getThird())) {
    		Collections.swap(this.nodes, i, (int)Math.floor((i-1)/2));
    		i = (int)Math.floor((i-1)/2);
    	}
    }

    public Triple<A,A,Integer> remove() {
    	Collections.swap(this.nodes, 0, this.nodes.size()-1);
    	Triple triple = this.nodes.remove(this.nodes.size()-1);
    	int i=0;
    	while (!isLeaf(i) && (verifierFils1(i) || verifierFils2(i))) {
    		if (verifierFils2(i)) {
	    		if ((this.nodes.get(2*i+1).getThird()) < (this.nodes.get(2*i+2).getThird())) {
	    			Collections.swap(this.nodes, i, (2*i+1));
	        		i = 2*i+1;
	    		} else {
	    			Collections.swap(this.nodes, i, (2*i+2));
	        		i = 2*i+2;
	    		}
    		}
    		else {
    			Collections.swap(this.nodes, i, (2*i+1));
        		i = 2*i+1;
    		}
    	}
    	return triple;
    }

    private boolean isLeaf(int src) {
    	return (2*src+1) > this.nodes.size()-1;
    }
    
    private boolean verifierFils1(int src) {
    	return this.nodes.get(src).getThird() > (this.nodes.get(2*src+1).getThird());
    }

    private boolean verifierFils2(int src) {
    	if (2*src+2 <= this.nodes.size()-1) {
    		return this.nodes.get(src).getThird() > (this.nodes.get(2*src+2).getThird());
    	}
    	return false;
    }

    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	int compt = 0;
    	int nbMaxNodeAvantNouveauNiveau = 1;
    	int niveauCourant = 0;
    	int length = this.nodes.size();
    	
    	while (compt < length) {
    		sb.append(nodes.get(compt));
    		compt++;
    		if (compt >= nbMaxNodeAvantNouveauNiveau) {
    			sb.append("\n");
    			niveauCourant++;
    			nbMaxNodeAvantNouveauNiveau += Math.pow(2, niveauCourant);
    		}
    	}
    	return sb.toString();
    }

    // test recursif pour verifier que fils gauche et fils droit sont bien >= a la racine.
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
    	int lastIndex = nodes.size()-1; 
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= lastIndex) {
                return nodes.get(left).getThird() >= nodes.get(root).getThird() && testRec(left);
            } else {
                return nodes.get(left).getThird() >= nodes.get(root).getThird() && testRec(left) && 
                	nodes.get(right).getThird() >= nodes.get(root).getThird() && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeapEdge<DirectedNode> jarjarBin = new BinaryHeapEdge<DirectedNode>();
        System.out.println("Arbre binaire vide ? " + jarjarBin.isEmpty()+"\n");
        int k = 10;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));            
            System.out.print("insert triple des noeuds "+k+" et "+(k+30)+" de poids " + rand);
            jarjarBin.insert(new DirectedNode(k), new DirectedNode(k+30), rand);
            System.out.println("   ok");
            k--;
        }
        System.out.println("\n" + jarjarBin + "\n");
        System.out.println("Arbre binaire vide ? " + jarjarBin.isEmpty());
        System.out.println("Arbre binaire test ? " + jarjarBin.test() + "\n");
        while (m > 0) {
            System.out.print("remove " + jarjarBin.remove());
            System.out.println("   ok");
            m-=2;
        }
        System.out.println("\n" + jarjarBin + "\n");
        System.out.println("Arbre binaire vide ? " + jarjarBin.isEmpty());
        System.out.println("Arbre binaire test ? " + jarjarBin.test());
    }
}