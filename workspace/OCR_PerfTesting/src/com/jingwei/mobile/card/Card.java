package com.jingwei.mobile.card;

import com.jingwei.mobile.log.Log;

/**
 * The card entity, stands for the result of OCR
 * 
 * @author nan.lin3@renren-inc.com
 * @DateModifled: Apr 17, 2014
 */
public class Card {
	
	// Those fields come from the ocr result.
	
	// how many fields are there in the ocr result.
	public int count;
	
	// the values of the fields in the ocr result.
	String[] values;
	
	// the integers stands for the type of the String value above,
	// from 1 ~ 13, See the fields named as 'NAMECARD_XXXX' in ICardHeaders interface.
	int[] attrs;

	/**
	 * Empty constructor, for create an empty instance, to get the class of Card.
	 */
	public Card(){
		// 
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
	
	/**
	 * Format the String to print in a better view.
	 */
	public void Print(){
		for(int i=0; i<this.count; i++)
		{
			Log.Log("attr["+i+"] = "+this.attrs[i]+"\t"+this.values[i]);
		}
		
	}
	
	
}
