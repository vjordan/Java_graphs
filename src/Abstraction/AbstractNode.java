package Abstraction;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 
	public int label;
    public int weight;
	
	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 	
	public AbstractNode(int i) {
		this.label = i;
		this.weight = 0;
	}
	
	public AbstractNode(int i, int p) {
		this(i);
		this.weight = p;
	}
	
	// ------------------------------------------
	// 
	// 		Accessors
	//
	// ------------------------------------------
	/**
	 * @return an integer which is a unique identifier of a node
     */
	public int getLabel(){
		return this.label;
	}

	/**
	 * @return an integer which is a weight associated with a node
     */
	public int getWeight(){
			return this.weight;
	}

	/**
	 * setter for the weight variable
	 * @param p
     */
	public void setWeight(int p){
		this.weight = p;
	}

	@Override
	public boolean equals(Object n) {
		return n == this || ( n!=null &&
				  n instanceof AbstractNode && 
				  ((AbstractNode) n).getLabel() == this.getLabel());
	}

	
	public String toString() {
		String s = "node-"+label;
		if(weight>0)
			s+= "-"+weight;
		return s;
	}
	
	
}
