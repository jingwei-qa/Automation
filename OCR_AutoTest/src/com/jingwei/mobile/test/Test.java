package com.jingwei.mobile.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.ICardFields;
import com.jingwei.mobile.match.*;
import com.jingwei.mobile.result.*;

public class Test {
	
	public static Map<Integer, MatchBase> matchers;
	
	/**
	 * Init the matchers, set a key value, so that we can use a circle to do all the matching
	 */
	static{
		matchers = new HashMap<Integer, MatchBase>();
		matchers.put(new Integer(ICardFields.NAME), new NameMatcher());
		matchers.put(new Integer(ICardFields.NAME_EN), new NameEnMatcher());
		matchers.put(new Integer(ICardFields.TITLE), new TitleMatcher());
		matchers.put(new Integer(ICardFields.PHONE_COMPANY), new TelephoneMatcher());
		matchers.put(new Integer(ICardFields.PHONE_FAX), new FaxMatcher());
		matchers.put(new Integer(ICardFields.MOBILE), new MobileMatcher());
		matchers.put(new Integer(ICardFields.EMAIL), new EmailMatcher());
		matchers.put(new Integer(ICardFields.COMPANY), new CompanyMatcher());
		matchers.put(new Integer(ICardFields.ADDRESS), new AddressMatcher());
		matchers.put(new Integer(ICardFields.IM), new IMMatcher());
		matchers.put(new Integer(ICardFields.WEBSITE), new WebsiteMatcher());
	}

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
		
		results = null;
		results = new ArrayList<ResultBase>();
		
		for(Integer key : matchers.keySet()){
			ResultBase result = matchers.get(key).Match(bean, card);
			results.add(result);
		}
	}
	
	/**
	 * Only do match to the spicified fields
	 * @param fields, the fields need to do matching
	 */
	public void doMatch(int fields){
		results = null;
		results = new ArrayList<ResultBase>();
		
		for(Integer key : matchers.keySet()){
			if((key & fields) != 0){
				ResultBase result = matchers.get(key).Match(bean, card);
				results.add(result);
			}
		}
	}
	
}
