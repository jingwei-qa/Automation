package com.jingwei.mobile.manual;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.jingwei.mobile.MobilePhoneMatchResult;
import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.CardFactory;
import com.jingwei.mobile.log.Log;
import com.jingwei.mobile.util.FileMover;

public class ManualData {
	
	static String csvFile = "card_tb.csv";
	static String rootPath = "/mnt/picset";
	static String configFilePath = "ocr_data/";
	static int StartIndex = 20000; 
	static int Count = 3000;
	
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		String basePath = "/media/boedriver/picset3000/";
		
		CardFactory cf = initCardFactory(configFilePath);
		
		CsvBeanReader beanReader = initBeanReader();
		CardBean cb = new CardBean();
		int count = 0;
		do{
			try {
//				int folderName = count / 20;
//				File subF = new File(basePath + File.separator + folderName);
//				if(!subF.exists()){
//					subF.mkdir();
//				}
//				Log log = Log.GetInstance(basePath + File.separator + folderName + ".log" );
				Log log = Log.GetInstance(basePath + "info.log" );
				log.Write(String.format("Index from %d , Count = ", StartIndex, Count));
				
				cb = beanReader.read(cb.getClass(), CardBean.header);
				String cardImgFilePath = rootPath + java.io.File.separator +  cb.getFolder() + java.io.File.separator + cb.getImgname();
				
				File imgFile = new File(cardImgFilePath);
				if(imgFile.exists()){
					
					File targetFile = new File(basePath + File.separator + cb.getImgname());
					Card card = cf.Make(cardImgFilePath);
					
//					if(card.count > 5){
						// valid picture
						FileMover.copyFile(imgFile, targetFile);
						count++;
						log.Write(System.lineSeparator() + cb.toString());
//					}
				}
			}catch(Exception e){
			}
		}while(cb != null && count < Count);

	}
	
	public static CardFactory initCardFactory(String configFilePath){
		CardFactory cf = null;
		
		try {
			cf = CardFactory.InitCardFactory(configFilePath);
		} catch (Exception e) {
			Log.Log("Initial Card Factory failed");
			e.printStackTrace();
		}
			
		return cf;
	}
	

	public static CsvBeanReader initBeanReader(){
		// Create a csv reader
		Reader streamReader = null;
		try {
			streamReader = new InputStreamReader(new FileInputStream(csvFile), "gb18030");
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
		CsvBeanReader beanReader = new CsvBeanReader(streamReader, CsvPreference.EXCEL_PREFERENCE);
		
		CardBean cb = new CardBean();
		// move 10000 lines to test new data.
		for(int p = 0; p < StartIndex; p++){
			try {
				cb = beanReader.read(cb.getClass(), CardBean.header);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return beanReader;
	}
	

}
