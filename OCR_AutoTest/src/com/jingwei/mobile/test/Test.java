package com.jingwei.mobile.test;

import java.util.ArrayList;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.result.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ResultBase imr = new IMResult();
		ResultBase nr = new NameResult();
		
		System.out.println(imr.getClass());
		System.out.println(nr.getClass());
	}
	
	CardBean bean;
	Card card;
	
	ArrayList<ResultBase> results = new ArrayList<ResultBase>();
	
	public Test(CardBean cardBean, Card card){
		this.bean = cardBean;
		this.card = card;
	}

	/**
	 * Do match to all the fields by default.
	 */
	public void doMatch(){
		
	}
	
	/**
	 * Only do match to the spicified fields
	 * @param fields, the fields need to do matching
	 */
	public void doMatch(int fields){
		
	}
	
}
