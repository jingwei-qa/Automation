package com.jingwei.mobile.ocr;
import java.lang.Runtime;

public class OCR{

	static{
//		Runtime.getRuntime().load("/home/administrator/workspace/OCR_PerfTesting/bin/libocr-0.1.so");

		Runtime.getRuntime().load("/media/boedriver/automation_workspace/OCR_PerfTesting/libocr.so");

//		System.loadLibrary("ocr");
	}

	public OCR()
	{
		num = 0;
		strs = new String[128];
		attr = new int[128];
	}

	
	public native boolean ocr_init(String pathname);

	public native boolean ocr_read(String filename);

	public native boolean ocr_readJpgBuffer(int length, byte[] buffer);

	public native boolean ocr_readWebpBuffer(int length, byte[] buffer);
	
	public native void ocr_free();

	public int num;
	public String[] strs;
	public int[] attr;
}
