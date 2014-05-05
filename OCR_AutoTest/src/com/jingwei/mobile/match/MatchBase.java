package com.jingwei.mobile.match;

import java.util.ArrayList;
import java.util.List;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.result.ResultBase;
import com.jingwei.mobile.util.Levenshtein;

public abstract class MatchBase {
	
	public static double TITLEMATCHRATE = 0.8;
	
	public static double NAMEMATCHRATE = 1.0;
	
	public static double ADDRESSMATCHRATE = 0.8;
	
	public static double COMPANYMATCHRATE = 0.8;
	
	public static double NAMEENMATCHRATE = 1.0;
	
	public static void main(String[] args){
		String exp = "abcd";
		
		ArrayList<String> actuals = new ArrayList<String>(){
			{
				add("adbc");
				add("aced");
				add("dsab");
				add("abdd");
			}
		};
		
		MobileMatcher mm = new MobileMatcher();
		String mostLike = mm.getMostLikeStr(exp, actuals);
		System.out.println(mostLike);
	}
	
	public abstract ResultBase Match(CardBean cardBean, Card card);
	
	public String getMostLikeStr(String expected, List<String> actuals){
		
		if(actuals.size() == 0){
			return null;
		}
		
		if(expected == null){
			return null;
		}
		
		int distance = expected.length();
		String result = "";
		for(String s : actuals){
			int tmp = Levenshtein.Compare(expected, s);
			if( tmp < distance){
				distance = tmp;
				result = s;
			}
		}
		
		return result;
	}

	public int getMostLikeInt(String expected, List<String> actuals){
		
		if(expected == null){
			return 0;
		}
		
		if(actuals.size() == 0){
			return expected.length();
		}
		
		int distance = expected.length();
		for(String s : actuals){
			int tmp = Levenshtein.Compare(expected, s);
			if( tmp < distance){
				distance = tmp;
			}
		}
		
		return distance;
	}
}
