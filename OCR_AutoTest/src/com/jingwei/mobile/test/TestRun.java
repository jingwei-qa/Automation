package com.jingwei.mobile.test;

import java.util.ArrayList;
import java.util.List;

import com.jingwei.mobile.result.ResultBase;

public class TestRun {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String picsetPath = "/mnt/samba/测试组专用/automation/picset2000"; 
		String initPath = "ocr_data/";
		String csvFilePath = "card_tb.csv";
		int startPos = 20000;
		int count  = 2000;
		int processedCount = 0;
//		List<Test> testResultList = new ArrayList<Test>();
		Test testResult = Test.GetEmptyTest();
		Test enTestResult = Test.GetEmptyTest();
		try{
			TestFactory tf = new TestFactory(picsetPath, initPath, csvFilePath, startPos);
			while(processedCount++ < count ){
				Test t = tf.Make();
				
				/**
				 * t == null means the csv reach the end, stop testing
				 */
				if(t == null){
					break;
				}
				
				t.doMatch();
				
				if(t instanceof EnTest){
					enTestResult.add(t);
				}else{
					testResult.add(t);
				}
				
				System.out.println("Processed [" + (processedCount) + "] / [" + count + "]");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			System.out.println("Chinese card result: ");
			for(ResultBase rb : testResult.results){
				System.out.println(rb);
			}
			
			System.out.println("Chinese card result End");
			System.out.println("-----------------------");
			System.out.println("English card result: ");
			for(ResultBase rb : enTestResult.results){
				System.out.println(rb);
			}
			
			System.out.println("English card result End");
			
		}
	
	}
	
	

}
