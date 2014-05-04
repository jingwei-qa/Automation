package com.jingwei.mobile.testrun;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.jingwei.mobile.MatchCount;
import com.jingwei.mobile.MobilePhoneMatchResult;
import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.CardFactory;
import com.jingwei.mobile.log.Log;


public class TestRunMobileNPhone extends TestRun{

	String csvFile, rootPath, configFilePath;
	boolean strict;
	int fields;
	
	int bingo = 0;
	int amount = 0;
	int startPos = 0;
	int size = -1;
	double matchRate = 1.0;
	// sizep start from 1, since the do ... while will execute once before circle starts.
//	
//	public TestRunMobilePhone(String csvFile, String rootPath, String configFilePath) throws FileNotFoundException {
//		this(csvFile, rootPath, configFilePath, -1);
//	}
	
	/**
	 * 
	 * @param csvFile
	 * @param rootPath
	 * @param configFilePath
	 * @param size
	 * @param startPos
	 * @throws FileNotFoundException
	 */
	public TestRunMobileNPhone(String csvFile, String rootPath, String configFilePath, int size, int startPos, double matchRate) throws FileNotFoundException {
		
		File file = new File(rootPath);
		if(!file.exists()){
			throw new FileNotFoundException();
		}
		
		this.size = size;
		this.csvFile = csvFile;
		this.rootPath = rootPath;
		this.configFilePath = configFilePath;
		this.startPos = startPos;
		this.matchRate = matchRate;
	}
	
	Log issuePicLog = Log.GetInstance("issuePic.log");
	
	public void run(){
		CardFactory cf = null;
		
		try {
			cf = CardFactory.InitCardFactory(configFilePath);
		} catch (Exception e) {
			Log.Log("Initial Card Factory failed");
			e.printStackTrace();
			return;
		}
			
		int phoneMatchCount = 0;
		int phoneTotalCount = 0;
		CsvBeanReader beanReader = initBeanReader();
		CardBean cb = new CardBean();
		MobilePhoneMatchResult resultTotal = new MobilePhoneMatchResult();
		do{
			try {
				cb = beanReader.read(cb.getClass(), CardBean.header);
			
				if (cb == null){
					break;
				}
				
				if(cb.getFolder() == null || cb.getImgname() == null || cb.getImgname() == ""){
					continue;
				}
				
				if(cb.getFolder() != null & !cb.getFolder().isEmpty() & cb.getImgname()!=null & !cb.getImgname().isEmpty()){
					String cardImgFilePath = this.rootPath + java.io.File.separator +  cb.getFolder() + java.io.File.separator + cb.getImgname();
					
					// if the file does not exists, ignore & continue
					if( !(new File(cardImgFilePath).exists()) ){
						continue;
					}
					
					Card card = cf.Make(cardImgFilePath);
					
					// if card is nul, means the ocr load picture failed, continue.
					if(card == null || card.count == 0){
						// TODO, handle this
						issuePicLog.Write(String.format("OCR read PIC failed: [%s]", cardImgFilePath));
						continue;
					}
					
					MobilePhoneMatchResult diffEach = CardBean.matchCardPhoneNMobile2(cb, card, matchRate);
//					Log.Log(diffEach.toString());
										resultTotal.add(diffEach);
					
					Log.Log("=================Start==================");
					Log.Log(resultTotal.toString());
					
					Log.Log("===================end===================");
					
					
					
					amount ++;
//					if(diffEach.bingo == 0){
//						Log.Log(String.format("FAILED! Field not match [%s] -- [%s]", cb.getCard_id(), cardImgFilePath));
//					}else{
//						Log.Log(String.format("SUCCESS! BINGO [%s] -- [%s] ", cb.getCard_id(), cardImgFilePath));
//					}
				}
				
			} catch (IOException | SecurityException | IllegalArgumentException e) {
				e.printStackTrace();
			}finally{
//				Log.Log(String.format("[%d] Bingo / [%d] All ", bingo, amount));
				
				if(size > 0 && amount >= size){
					break;
				}
			}
			
		}while(cb!= null);
			
		// close the bean reader
		try {
			beanReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public CsvBeanReader initBeanReader(){
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
