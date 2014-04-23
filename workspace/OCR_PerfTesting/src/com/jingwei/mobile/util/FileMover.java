package com.jingwei.mobile.util;

import java.io.File;

public class FileMover {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File f = new File("../abc");
		Move(f, "/home/administrator");
	}
	
	public static boolean Move(File source, String targetPath){
		
		if(!source.exists()){
			System.err.println("File not exists: " + source.getAbsolutePath());
			return false;
		}
		
		boolean result;
		File target = new File(targetPath);
		File dest = null;
		
		if(!target.canWrite()){
			System.err.println("No access to file: " + target.getAbsolutePath());
			return false;
		}
		
		if(target.isDirectory()){
			// if is a directory, use origin name
			dest = new File(target.getAbsolutePath(), source.getName());
		}else{
			dest = new File(target.getAbsolutePath(), targetPath);
		}
		
//		if(!dest.canWrite()){
//			System.err.println("No access to file: " + dest.getAbsolutePath());
//			return false;
//		}
		
		result = source.renameTo(dest);
		result &= target.delete();
		
		return result;
	}

}
