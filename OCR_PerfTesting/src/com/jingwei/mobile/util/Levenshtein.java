package com.jingwei.mobile.util;

import java.util.Date;

import com.jingwei.mobile.log.Log;

public class Levenshtein {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int diff = Compare("123", "321");
		System.out.println(diff);
			
	}
	
	public static int Compare(String source, String target){
		
		
		if(source == null && target == null){
			return 0;
		}
		
		if(source == null){
			source = "";
		}

		if(target == null){
			target = "";
		}
		
		int slength = source.length();
		int tlength = target.length();
		
		if(slength == 0){
			return tlength;
		}
		
		if(tlength ==0){
			return slength;
		}
		
		int[][] matrix = new int[slength + 1][tlength + 1];
		
		for(int i = 0; i <= slength; i++){
			matrix[i][0] = i;
		}
		
		for(int j =0; j <=tlength ; j ++){
			matrix[0][j] = j;
		}
		
		for(int sindex = 1; sindex <= slength; sindex++){
			for(int tindex = 1; tindex <= tlength; tindex++){
				int weight = source.charAt(sindex-1) == target.charAt(tindex-1) ? 0 : 1;
				matrix[sindex][tindex] = Minimium(matrix[sindex-1][tindex] + 1, matrix[sindex][tindex-1] + 1, matrix[sindex-1][tindex-1] + weight);
			}
		}
		
		int distance = matrix[slength][tlength];
		Log.Log(String.format("Comparing: [%s] <-> [%s] = [%d]", source, target, distance));
		
		return distance;
	}
	
	public static int Minimium(int i, int j, int k){
		int result;
		result = i < j ? i : j;
		result = result < k ? result : k;
		return result;
	}

}
