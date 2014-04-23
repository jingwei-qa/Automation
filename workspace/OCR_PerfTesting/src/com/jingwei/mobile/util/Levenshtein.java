package com.jingwei.mobile.util;

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
			matrix[0][i] = i;
		}
		
		for(int j =0; j <=tlength ; j ++){
			matrix[j][0] = j;
		}
		
		for(int sindex = 1; sindex <= slength; sindex++){
			for(int tindex = 1; tindex <= tlength; tindex++){
				int weight = source.charAt(sindex-1) == target.charAt(tindex-1) ? 0 : 1;
				matrix[sindex][tindex] = Minimium(matrix[sindex-1][tindex] + 1, matrix[sindex][tindex-1] + 1, matrix[sindex-1][tindex-1] + weight);
			}
		}
		
		return matrix[slength][tlength];
	}
	
	public static int Minimium(int i, int j, int k){
		int result;
		result = i < j ? i : j;
		result = result < k ? result : k;
		return result;
	}

}
