package com.jingwei.mobile.util;

public class Utility {

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
		s = s.replace("QQ ", "");
		s = s.replace("QQ:", "");
		
		s = s.replace(" ", "");
		
		s = Compl2Simpl.Compl2Simpl(s);
		s = s.toLowerCase();
		
		return s;
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
}
