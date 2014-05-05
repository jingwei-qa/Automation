package com.jingwei.mobile.card;

import java.util.ArrayList;
import java.util.List;

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
	public String[] values;
	
	// the integers stands for the type of the String value above,
	// from 1 ~ 13, See the fields named as 'NAMECARD_XXXX' in ICardHeaders interface.
	public int[] attrs;
	
	ArrayList<String> list;

	ArrayList<Integer> attribList;
	/**
	 * Empty constructor, for create an empty instance, to get the class of Card.
	 */
	public Card(){
		// 
	}
	
	public List<String> getValuesList(){
		if(list == null || list.size() == 0){
			list = new ArrayList<String>();
			for(String s : this.values){
				list.add(s);
			}
		}
		
		return list;
	}
	
	public List<Integer> getAttribList(){
		
		if(attribList == null || attribList.size() == 0){
			attribList = new ArrayList<Integer>();
			for(int a : this.attrs){
				attribList.add(a);
			}
		}
		
		return attribList;

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
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("==CardBean Object==" + System.lineSeparator());
		
		for(int i=0; i<this.count; i++)
		{
			int key = this.attrs[i];
			if(ICardHeaders.FieldNameMapping.containsKey(key)){
				String head = ICardHeaders.FieldNameMapping.get(key);
				sb.append(String.format("-%s: \t%s%s", head, this.values[i], System.lineSeparator()));
			}
		}
		sb.append("===CardBean END===");
		return sb.toString();
	}
	
	
}
