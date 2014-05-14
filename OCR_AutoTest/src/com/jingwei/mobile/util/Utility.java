package com.jingwei.mobile.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;

public class Utility {

	public static void main(String[] args){
		String str = "中文fd我是地方但是3434sdf国人吧喇叭啦as ";
		int count = Utility.ContainsChinese(str);
		System.out.println(count);
	}
	
	/**
	 * To verify if a number is a mobile number
	 * China MainLand format: +86 1xx xxxx xxxx
	 * TODO: use a regular expression to do validating.
	 * @param number
	 * @return
	 */
	public static boolean IsMobileNumber(String number){
		if(number.startsWith("+861")){
			return true;
		}
		
		if(number.startsWith("+8601")){
			return true;
		}
		
		return false;
	}
	
	public static String TrimNConvert(String s){
		s = s.replace("QQ:", "");
		
		s = s.replace(" ", "");
		s = s.replace("（", "(");
		s = s.replace("）", ")");
		
		s = Compl2Simpl.Compl2Simpl(s);
		s = s.toLowerCase();
		
		return s;
	}

	public static List<String> TrimNConvert(List<String> sList){
		ArrayList<String> resList = new ArrayList<String>();
		for(String s : sList){
			if(s != null){
				resList.add(TrimNConvert(s));
				
			}else{
				resList.add(s);
			}
		
		}
		
		return resList;
	}

	public static boolean isEmpty(String s){
		boolean res = false;
		if(s == null){
			return true;
		}
		
		if(s.toLowerCase().equals("null")){
			return true;
		}
		
		if(s.length() == 0){
			return true;
		}
		
		return res;
	}
	
	public static void PrintArray(List arr){
		for(Object o  : arr){
			System.out.println(o);
		}
	}
	
	public static int ContainsChinese(String str){
		int count = 0;
		String regEx = "[\\u4e00-\\u9fa5]";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				count += m.group(i).length();
			}
		}
		
		return count;
	}
	
	public static boolean IsChineseCard(CardBean card){
		
		StringBuilder sb = new StringBuilder();
		for(String fieldStr : card.header){
			try{
				Object field = card.getField(fieldStr);
				if(field!=null && (field instanceof String)){
					sb.append(field);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		if(ContainsChinese(sb.toString()) > 0){
			return  true;
		}else{
			return false;
		}
	}
	
	public static boolean IsChineseCard(Card card){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < card.count ; i++){
			sb.append(card.values[i]);
		}
		
		if(ContainsChinese(sb.toString()) > 0){
			return  true;
		}else{
			return false;
		}
	}
}
