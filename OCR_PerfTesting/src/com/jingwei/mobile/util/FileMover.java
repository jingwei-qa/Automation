package com.jingwei.mobile.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

	// 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
}
