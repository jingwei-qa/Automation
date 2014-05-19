package com.jingwei.mobile.ocr;
import java.lang.Runtime;

//Return Status 
// -1. error
//	0. not accrate 
// 	1. accrate 
// 	2. has problem
//#define OCR_ERROR -1
//#define OCR_NOT_ACCURATE 0
//#define OCR_ACCURATE 1
//#define OCR_HAS_PROBLEM 2

public class OCR{

	static{
		// absolute file path
		//Runtime.getRuntime().load("/libocr.so");
		System.loadLibrary("ocr");
	}

	public OCR()
	{
		num = 0;
		strs = new String[128];
		attr = new int[128];
	}
	
	public native boolean ocr_init(String pathname);

	public native int ocr_read(String filename);

	public native int ocr_readJpgBuffer(int length, byte[] buffer);

	public native int ocr_readWebpBuffer(int length, byte[] buffer);
	
	public native void ocr_free();

	public int num;
	public String[] strs;
	public int[] attr;
}
