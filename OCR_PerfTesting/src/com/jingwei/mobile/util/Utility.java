package com.jingwei.mobile.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	
	public static List<File> GetAllFiles(String path){
		ArrayList<File> res = new ArrayList<File>();

		File rootFolder = new File(path);
		if(rootFolder.exists()){
			if(rootFolder.isFile()){
				res.add(rootFolder);
			}else{
				for(String sub : rootFolder.list()){
					res.addAll(GetAllFiles(sub));
				}
			}
		}
		
		return res;
	}
}
