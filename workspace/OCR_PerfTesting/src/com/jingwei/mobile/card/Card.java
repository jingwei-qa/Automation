package com.jingwei.mobile.card;

public class Card {
	
	int count;
	String[] values;
	int[] attrs;
	

	public Card(){
		// Empty constructor for Bean.
	}
	
	/*
	 * Initial method, create a card entity with fields count, matched values and the attribute of the value.
	 */
	public Card(int count, String[] values, int[] attrs){
		this.count = count;
		this.values = values;
		this.attrs = attrs;
	}
	
		
	// TODO: add compare method 
	public int Compare(Card card2){
				
		return 0;
	}
	
	// TODO: 
	public int Compare(Card card2, int[] attribToCp){
		return 0;
	}
	
	
	public int Compare(Card card2, int attr){
		return 0;
	}
	
	public void Print(){
		for(int i=0; i<this.count; i++)
		{
			System.out.println("attr["+i+"] = "+this.attrs[i]+"\t"+this.values[i]);
		}
		
	}
	
	
}
