package com.jingwei.mobile.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.util.Utility;

public class QNWTestRun {
	
	public static String FieldsSeperator = "---";
	public static String ContentSeperator = "\t";
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException{
		
		int count = 300;
		String csvFile = "utf8.csv";
		String nameFile = "name.txt";
		String phoneFile = "phone.txt";
		String titleFile = "";
		String filenameFile = "";
		
		QNWTestRun testRun = new QNWTestRun();
		
		Map<String, CardBean> fileBeanMapping = testRun.GetFileBeanMapping(csvFile, count);
		for(String key : fileBeanMapping.keySet()){
			System.out.println("file: " + key);
		}
	}
	
	public Map<String, CardBean> GetFileBeanMapping(String csvFile, int count) throws UnsupportedEncodingException, FileNotFoundException{
		Map<String, CardBean> fileIdMap = new HashMap<String, CardBean>();
		CsvBeanReader reader =CardBean.InitReader(csvFile, "UTF-8", CsvPreference.EXCEL_PREFERENCE);
		
		CardBean cb = new CardBean();
		while(count > 0){
			try {
				cb = reader.read(cb.getClass(), CardBean.header);

				// if cb == null, means the csv file reach the end
				if(cb == null){
					break;
				}else{
					fileIdMap.put(cb.getImgname(), cb);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				return fileIdMap;
			}
		}
		
		return fileIdMap;
	}

	/**
	 * The map of filename <-> id, the id is the identity of contact_ export from QNW's log
	 * @param fileName, WITH .jpg as appendix name.
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> GetFileIdMapping(String fileName) throws IOException{
		
		File file = new File(fileName);
		if(!file.exists()){
			throw new java.io.FileNotFoundException();
		}
		
		HashMap<String, String> fileIdMapping = new HashMap<String, String>();
		
		InputStreamReader isReader = new InputStreamReader(new FileInputStream(file));
		BufferedReader br = new BufferedReader(isReader);
		try{
			// first line is the db table title, useless, skip
			String line = br.readLine();
			
			// the id index starts with 1st;
			int index = 1;
			
			// null means the EOF
			while(line != null){
				
				// here is the content 
				line = br.readLine();
				
				// trim to ignore the whitespaces
				line = line.trim();
				fileIdMapping.put(line + ".jpg", String.valueOf(index));
				index++;
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			
			// Release resource
			if(br != null){
				br.close();
			}
		}
		
		return fileIdMapping;
	}

	public Map<String, String> GetIdNameMapping(String fileName) throws IOException{
		return splitToString(fileName);
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public Map<String, String[]> GetIdPhonesMapping(String fileName) throws IOException{
		return splitToStringArray(fileName);
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public Map<String, String[]> GetIdTitleMapping(String fileName) throws IOException{
		return this.splitToStringArray(fileName);
	}

	
	public Map<String, String> splitToString(String fileName) throws IOException{
		File file = new File(fileName);
		if(!file.exists()){
			throw new java.io.FileNotFoundException();
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		InputStreamReader isReader = new InputStreamReader(new FileInputStream(file));
		BufferedReader br = new BufferedReader(isReader);
		try{
			// first line is the db table title, useless, skip
			String line = br.readLine();
			
			// null means the EOF
			while(line != null){
				
				// here is the content 
				line = br.readLine();
				
				String[] contents = line.split(QNWTestRun.ContentSeperator);
				if(contents.length == 2){
					map.put(contents[0], contents[1]);
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			
			// Release resource
			if(br != null){
				br.close();
			}
		}
		
		return map;
	}

	public Map<String, String[]> splitToStringArray(String fileName) throws IOException{
		// FieldsSeperator
		
		File file = new File(fileName);
		if(!file.exists()){
			throw new java.io.FileNotFoundException();
		}
		
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		
		InputStreamReader isReader = new InputStreamReader(new FileInputStream(file));
		BufferedReader br = new BufferedReader(isReader);
		try{
			// first line is the db table title, useless, skip
			String line = br.readLine();
			
			// null means the EOF
			while(line != null){
				
				// here is the content 
				line = br.readLine();
				
				String[] contents = line.split(QNWTestRun.ContentSeperator);
				if(contents.length == 2){
					String key = Utility.TrimNConvert(contents[0]);
					String values = Utility.TrimNConvert(contents[1]);
					map.put(key, values.split(QNWTestRun.FieldsSeperator));
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			
			// Release resource
			if(br != null){
				br.close();
			}
		}
		
		return map;
	}
}
