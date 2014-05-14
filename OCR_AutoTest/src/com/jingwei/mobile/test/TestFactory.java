package com.jingwei.mobile.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.CardFactory;
import com.jingwei.mobile.util.Utility;

public class TestFactory {

	CardFactory cf;
	String picsetPath;
	CsvBeanReader reader;
	public TestFactory(String picsetPath, String initPath, String csvFilePath, int startPos) throws Exception{
		
		this.picsetPath = picsetPath;
		this.cf = CardFactory.InitCardFactory(initPath);
		this.reader = this.initBeanReader(csvFilePath, startPos);
	}
	
	public Test Make() throws IOException{
		
		CardBean cb = new CardBean();
		Class<CardBean> cbClass = (Class<CardBean>) cb.getClass();
		Card card = null;
		
		do{
			cb = this.reader.read(cbClass, CardBean.header);
			
			if(cb == null){
				return null;
			}
		
			String imgPath = getImgPath(cb);
			System.out.println("img: " + imgPath);
			if(!new File(imgPath).exists()){
				continue;
			}
			
			card = this.cf.Make(imgPath);
			
		}while(card == null);
		
		Test test;
		if(Utility.IsChineseCard(cb)){
			test = new Test(cb, card);
		}else{
			test = new EnTest(cb, card);
		}
		return test;
	}
	
	public String getImgPath(CardBean cb){
		return this.picsetPath +  java.io.File.separatorChar + cb.getImgname();
//		return this.picsetPath +  java.io.File.separatorChar + cb.getFolder() + java.io.File.separatorChar + cb.getImgname();
	}
	

	public CsvBeanReader initBeanReader(String csvFile, int startPos) throws IOException{
		// Create a csv reader
		Reader streamReader = null;
		try {
			streamReader = new InputStreamReader(new FileInputStream(csvFile), "gb18030");
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		
		CsvBeanReader beanReader = new CsvBeanReader(streamReader, CsvPreference.EXCEL_PREFERENCE);
		CardBean cb = new CardBean();
		
		for(int p = 0; p < startPos; p++){
			try {
				cb = beanReader.read(cb.getClass(), CardBean.header);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return beanReader;
	}
}
