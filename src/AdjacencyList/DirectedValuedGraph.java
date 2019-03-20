package AdjacencyList;

import Abstraction.AbstractNode;
import GraphAlgorithms.GraphTools;

public class DirectedValuedGraph extends DirectedGraph<DirectedNode> {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	public DirectedValuedGraph(int[][] matrixVal) {
		super();
    	
    	for(int i=0;i<matrixVal.length;i++)
    	{
    		DirectedNode vDirectedNode = makeNode(i);
    		nodes.add(vDirectedNode);
    	}
    	
    	this.order = nodes.size();
    	
    	for(int i=0;i<nodes.size();i++)
    	{
    		for(int j=0;j<nodes.size();j++)
    		{
        		if(matrixVal[i][j]!=0)
        		{
        			m++;
        			nodes.get(i).addSuccs(nodes.get(j));
        			nodes.get(i).addCosts(matrixVal[i][j]);
        			nodes.get(j).addPreds(nodes.get(i));
        			nodes.get(j).addCostsInv(matrixVal[i][j]);
        		}
    		}
    	}         	
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    
	/**
     * Removes the arc (from,to) with cost if it is present in the graph
     */
    @Override
    public void removeArc(DirectedNode from, DirectedNode to) {
    	int vSuccToSupress = -1;
    	int vPredToSupress = -1;
    	int vCp=0;
    	if(getNodeOfList(from).getSuccs().contains(to))
    	{
    		m--;
        	for(AbstractNode vNode : getNodeOfList(from).getSuccs())
        	{
        		if(vNode.getLabel()==to.getLabel())
        		{
        			vSuccToSupress = vCp;  			
        		}
        		vCp++;
        	}
        	if(vSuccToSupress>=0)
        	{
        		getNodeOfList(from).getSuccs().remove(vSuccToSupress);
        		getNodeOfList(from).getCosts().remove(vSuccToSupress);
        	}
        	
        	vCp = 0;
        	for(AbstractNode vNode : getNodeOfList(to).getPreds())
        	{
        		if(vNode.getLabel()==from.getLabel())
        		{
        			vPredToSupress = vCp;
        		}
        		vCp++;
        	}
        	if(vPredToSupress>=0)
        	{
        		getNodeOfList(to).getPreds().remove(vPredToSupress);
        		getNodeOfList(to).getCostsInv().remove(vPredToSupress);
        	}
    	}
    }

    /**
     * Adds the arc (from,to) with cost  if it is not already present in the graph
     */
    public void addArc(DirectedNode from, DirectedNode to, int cost) {
    	if(!getNodeOfList(from).getSuccs().contains(to))
    	{
    		m++;
    		getNodeOfList(from).addSuccs(to);
    		getNodeOfList(from).addCosts(cost);
    		getNodeOfList(to).addPreds(from);
    		getNodeOfList(to).addCostsInv(cost);
    	}
    }
    
    @Override
    public String toString(){
        String s = "";
        for(DirectedNode n : nodes){
        	int vCp =0;
            s+="successors of "+n+" : ";
            for(DirectedNode sn : n.getSuccs()){
                s+=sn+"|="+n.getCosts().get(vCp)+" ";
                vCp++;
            }
            s+="\n";
            
            s+="predecessor of "+n+" : ";
            for(DirectedNode sn : n.getPreds()){
                s+=sn+" ";
            }
            s+="\n\n";
            
        }
        s+="\n";
        return s;
    }
    
    
    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        GraphTools.AfficherMatrix(matrix);
        GraphTools.AfficherMatrix(matrixValued);
        DirectedValuedGraph al = new DirectedValuedGraph(matrixValued);
        System.out.println(al);
        DirectedGraph alInv = (DirectedGraph)al.computeInverse();
        System.out.println(alInv);
        System.out.println(al.getNodes().get(2).getCosts());
        al.addArc(new DirectedNode(2), new DirectedNode(5), 13);
        al.addArc(new DirectedNode(2), new DirectedNode(7), 4);
        System.out.println(al);
        System.out.println(al.getNodes().get(2).getCosts());
        System.out.println(al.isArc(new DirectedNode(2), new DirectedNode(5)));
        al.removeArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println(al.getNodes().get(2).getCosts());
    }
	
}
