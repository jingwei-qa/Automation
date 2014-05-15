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

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.CardFactory;
import com.jingwei.mobile.log.Log;


public class TestRun extends Thread{
	
	public TestRun(){}

	String csvFile, rootPath, configFilePath;
	int fields;
	
	int bingo = 0;
	int amount = 0;
	
	int size = -1;
	double matchRate = 1.0;
	int startPos = 0;
	
	// sizep start from 1, since the do ... while will execute once before circle starts.
	
	public TestRun(String csvFile, String rootPath, String configFilePath, int fields) throws FileNotFoundException {
		this(csvFile, rootPath, configFilePath, fields, -1, 1.0, 0);
	}
	
	/**
	 * 
	 * @param csvFile
	 * @param rootPath
	 * @param configFilePath
	 * @param strict
	 * @param fields
	 * @throws FileNotFoundException 
	 * @throws Exception
	 * 
	 */
	public TestRun(String csvFile, String rootPath, String configFilePath, int fields, int size, double matchRate, int startPos) throws FileNotFoundException {
		
		File file = new File(rootPath);
		if(!file.exists()){
			throw new FileNotFoundException();
		}
		
		this.size = size;
		this.csvFile = csvFile;
		this.rootPath = rootPath;
		this.configFilePath = configFilePath;
		this.fields = fields;
		this.matchRate = matchRate;
		this.startPos = startPos;
		// start the thread when initialized.
//		start();
	}
	
	Log issuePicLog = Log.GetInstance("issuePic.log");
	
	public void run(){
		CardFactory cf = null;
		try {
			cf = CardFactory.InitCardFactory(configFilePath);
		} catch (Exception e) {
			Log.Log("Initial Card Factory failed");
			e.printStackTrace();
		}
		
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
		do{
			try {
				cb = beanReader.read(cb.getClass(), CardBean.header);
				
				// if the cb is null, break
				// maybe the beanReader reach the EOF.
				// initial failed, or any other special situation
				if (cb == null){
					break;
				}
				
				if(cb.getFolder() == null || cb.getImgname() == null || cb.getImgname() == ""){
					continue;
				}
				
//				if(cb.getFolder() != null & !cb.getFolder().isEmpty() & cb.getImgname()!=null & !cb.getImgname().isEmpty()){
				if( cb.getImgname()!=null & !cb.getImgname().isEmpty()){
					
//					String cardImgFilePath = this.rootPath + java.io.File.separator +  cb.getFolder() + java.io.File.separator + cb.getImgname();
					String cardImgFilePath = this.rootPath + java.io.File.separator + cb.getImgname();
					
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
					
					int diffEach = CardBean.matchCard(cb, card, fields, matchRate);
					
					// If the matchCard return -1. 
					// means the test data & ocr data met issues, ignore this record
					if(diffEach == -1){
						Log.Log(String.format("IGNORE! Fileds are empty!! [%s] -- [%s]", cb.getCard_id(), cardImgFilePath));
						continue;
					}
					
					if(diffEach == 0){
						Log.Log(String.format("SUCCESS! BINGO [%s] -- [%s] ", cb.getCard_id(), cardImgFilePath));
						bingo++;
					}else{
						Log.Log(String.format("FAILED! Field not match [%s] -- [%s]", cb.getCard_id(), cardImgFilePath));
					}

					amount++;
				}
				
			} catch (IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}finally{
				Log.Log(String.format("[%d] Bingo / [%d] All ", bingo, amount));
				
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
}
