package com.jingwei.mobile.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.util.Compl2Simpl;
import com.jingwei.mobile.util.Utility;

public class QNWTestRun {
	
	public static String FieldsSeperator = "---";
	public static String ContentSeperator = "\t";
	
	public static String ENCODING = "Unicode";
	public static String[] phonePrefix = {"+86", "+852", "+1"};
	public static String[] websitePrefix = {"http://", "https://"};
	public static String QNWResRoot = "/mnt/samba/测试组专用/automation/qnw_auto_res/data/7";
	public static int Index = 20000;
	
	public static void main(String[] args) throws IOException{
		
		int count = 3500;
		String csvFile = "utf8.csv";
		String nameFile = QNWResRoot + java.io.File.separatorChar + "1-name.txt";
//		String enNameFile = QNWResRoot + java.io.File.separatorChar + "9.txt";
		String phoneFile = QNWResRoot + java.io.File.separatorChar + "2-phone.txt";
		String addressFile = QNWResRoot + java.io.File.separatorChar + "3-adress.txt";
		String titleFile = QNWResRoot + java.io.File.separatorChar + "4-title.txt";
		String emailFile = QNWResRoot + java.io.File.separatorChar + "5-mail.txt";
		String websiteFile = QNWResRoot + java.io.File.separatorChar + "7-website.txt";

		String filenameFile = QNWResRoot + java.io.File.separatorChar + "info.log";

		String imFile = QNWResRoot + java.io.File.separatorChar + "im.txt";
		
		int matchedCount = 0;
		int totalCount = 0;
		
		
		CompareName(count, csvFile, nameFile, filenameFile, matchedCount,
				totalCount);
		
		ComparePhone(count, csvFile, phoneFile, filenameFile, matchedCount,
				totalCount);
		
		CompareEmail(count, csvFile, emailFile, filenameFile, matchedCount,
				totalCount);
		
//		CompareWebsite(count, csvFile, websiteFile, filenameFile, matchedCount,
//				totalCount);
		
//		CompareEnName(count, csvFile, enNameFile, filenameFile, matchedCount,
//				totalCount);
		
//		CompareIM(count, csvFile, imFile, filenameFile, matchedCount,
//				totalCount);
		
//		WriteAddress(count, csvFile, addressFile, filenameFile, matchedCount,
//				totalCount);
//		
//		WriteTitle(count, csvFile, titleFile, filenameFile, matchedCount,
//				totalCount);
	}
	

	private static void WriteAddress(int count, String csvFile, String addressFile,
			String filenameFile, int matchedCount, int totalCount) throws IOException {
		// TODO Auto-generated method stub
		QNWTestRun testRun = new QNWTestRun();
		
		Map<String, CardBean> fileBeanMapping = testRun.GetFileBeanMapping(csvFile, count);
		Map<String, String> fileIdMapping = testRun.GetFileIdMapping(filenameFile);
		Map<String, String> idAddressMapping =testRun.GetIdAddressMapping(addressFile);
		
		FileWriter writer = new FileWriter("address2C.txt");
		writer.write(String.format("%s\t %s ***** %s %s" , "id", "expected", "actual", System.lineSeparator()));
		int sepCount = 0;
		for(String key : fileIdMapping.keySet()){
			if((sepCount % 80) == 0 ){
				writer.write("\n\n\n\n*****\n\n\n\n");
			}
			
			String id = fileIdMapping.get(key);
			CardBean cb = fileBeanMapping.get(key);
			
			String expectedAddress = cb.getAddress();
			String actualAddress = idAddressMapping.get(id);
			
			writer.write(String.format("%s\t %s ***** %s %s" , id, expectedAddress, actualAddress, System.lineSeparator()));
			sepCount++;
		}
		
		writer.close();
		System.out.println(String.format("Write Complete!"));
	}

	private static void WriteTitle(int count, String csvFile, String titleFile,
			String filenameFile, int matchedCount, int totalCount) throws IOException {
		// TODO Auto-generated method stub
		QNWTestRun testRun = new QNWTestRun();
		
		Map<String, CardBean> fileBeanMapping = testRun.GetFileBeanMapping(csvFile, count);
		Map<String, String> fileIdMapping = testRun.GetFileIdMapping(filenameFile);
		Map<String, String> idTitleMapping =testRun.GetIdAddressMapping(titleFile);
		
		FileWriter writer = new FileWriter("title2C.txt");
		writer.write(String.format("%s\t %s ***** %s %s" , "id", "expected", "actual", System.lineSeparator()));
		int sepCount = 0;
		for(String key : fileIdMapping.keySet()){
			if((sepCount % 80) == 0 ){
				writer.write("\n\n\n\n*****\n\n\n\n");
			}
			String id = fileIdMapping.get(key);
			CardBean cb = fileBeanMapping.get(key);
			
			String expectedTitle = cb.getCompany() + cb.getTitle();
			String actualTitle = idTitleMapping.get(id);
			
			writer.write(String.format("%s\t %s ***** %s %s" , id, expectedTitle, actualTitle, System.lineSeparator()));
			sepCount++;
		}
		
		writer.close();
		System.out.println(String.format("Write Complete!"));
	}


	private static void ComparePhone(int count, String csvFile, String idPhoneFile,
			String filenameFile, int matchedCount, int totalCount)
			throws IOException {
		
		int totalNum = 0;
		int bingoNum = 0;
		
		QNWTestRun testRun = new QNWTestRun();
		
		Map<String, CardBean> fileBeanMapping = testRun.GetFileBeanMapping(csvFile, count);
		Map<String, String> fileIdMapping = testRun.GetFileIdMapping(filenameFile);
		Map<String, String[]> idPhoneMapping =testRun.GetIdPhonesMapping(idPhoneFile);
		for(String key : fileIdMapping.keySet()){
			CardBean cb = fileBeanMapping.get(key);
			
			List<String> expectedPhones = new ArrayList<String>();
			JSONObject obj = new JSONObject();
			
			try{
				JSONArray phoneJsonArr = JSONArray.fromObject(cb.getPhone_company());
				for(Object phone : phoneJsonArr){
					if(phone.getClass() == obj.getClass()){
						obj = (JSONObject) phone;
						if( obj.containsKey("v")){
							expectedPhones.add(obj.getString("v"));
						}
					}
				}
			}catch(Exception ex){
				
			}

			try{
				JSONArray mobileJsonArr = JSONArray.fromObject(cb.getMobile());
				for(Object mob : mobileJsonArr){
					if(mob.getClass() == obj.getClass()){
						obj = (JSONObject) mob;
						if( obj.containsKey("v")){
							expectedPhones.add(obj.getString("v"));
						}
					}
				}
			}catch(Exception ex){
				
			}
			
			String id = fileIdMapping.get(key);
			String[] actualPhones = idPhoneMapping.get(id);

//			if(cb.getName() == null || cb.getName() == "" || cb.getName().toLowerCase() == "null"){
//				continue;
//			}
			
			if(expectedPhones.size() > 0){
				totalNum ++;
			}
			
			int tmpMatchCount = 0;
			if(actualPhones == null || actualPhones.length == 0){
				tmpMatchCount += 0;
			}else{
//				System.out.println(String.format("Comparing: [%s] :: [%s]", cb.getName(), cb.getImgname()));
				
				for(String act : actualPhones){
					for(String exp : expectedPhones){
//						System.out.println(String.format("Matching: [%s] -> [%s]", exp, act));
						for(String s : QNWTestRun.phonePrefix){
							if(exp.startsWith(s) && !act.startsWith(s)){
								exp = exp.replace(s, "");
								
								while(act.startsWith("0")){
									act = act.substring(1);
								}
							}
						}
						
						if(act.equals(exp)){
							tmpMatchCount++;
						}
					}
				}
			}
			
			if(tmpMatchCount > 0){
				bingoNum++;
			}
			
//			System.out.println(String.format("Single Match: [%d] / [%d]", tmpMatchCount,  expectedPhones.size()));
			matchedCount += tmpMatchCount;
			totalCount += expectedPhones.size();
			
		}
		
		System.out.println(String.format("Phone Matched / Total : [%d] / [%d]", matchedCount, totalCount));
		System.out.println(String.format("Phone Bingo / Count : [%d] / [%d]", bingoNum, totalNum));
	}

	private static void CompareIM(int count, String csvFile, String idImFile,
			String filenameFile, int matchedCount, int totalCount)
			throws IOException {
		
		QNWTestRun testRun = new QNWTestRun();
		
		Map<String, CardBean> fileBeanMapping = testRun.GetFileBeanMapping(csvFile, count);
		Map<String, String> fileIdMapping = testRun.GetFileIdMapping(filenameFile);
		Map<String, String[]> idImsMapping =testRun.GetIdIMsMapping(idImFile);
		for(String key : fileIdMapping.keySet()){
			CardBean cb = fileBeanMapping.get(key);
			String imsStr = cb.getIm();
			if(imsStr == null || imsStr.length() == 0 || imsStr.toLowerCase() == "null"){
				continue;
			}
			
			List<String> expectedIms = new ArrayList<String>();
			for(String im : imsStr.split("@@@")){
				expectedIms.add(im);
			}
			
			String id = fileIdMapping.get(key);
			String[] actualIMs = idImsMapping.get(id);

			int tmpMatchCount = 0;
			if(actualIMs == null || actualIMs.length == 0){
				tmpMatchCount = 0;
			}else{
				System.out.println(String.format("Comparing: [%s] :: [%s]", cb.getName(), cb.getImgname()));
				
				for(String act : actualIMs){
					for(String exp : expectedIms){
						act = Utility.TrimNConvert(act);
						exp = Utility.TrimNConvert(exp);
						
						System.out.println(String.format("Matching: [%s] -> [%s]", exp, act));
						if(act.equals(exp)){
							tmpMatchCount++;
						}
					}
				}
			}
			
			System.out.println(String.format("Single Match: [%d] / [%d]", tmpMatchCount,  expectedIms.size()));
			matchedCount += tmpMatchCount;
			totalCount += expectedIms.size();
			
		}
		
		System.out.println(String.format("Matched / Total : [%d] / [%d]", matchedCount, totalCount));
	}

	private static void CompareName(int count, String csvFile, String nameFile,
			String filenameFile, int matchedCount, int totalCount)
			throws IOException {
		QNWTestRun testRun = new QNWTestRun();
		
		Map<String, CardBean> fileBeanMapping = testRun.GetFileBeanMapping(csvFile, count);
		Map<String, String> fileIdMapping = testRun.GetFileIdMapping(filenameFile);
		Map<String, String> idNameMapping =testRun.GetIdNameMapping(nameFile);
		for(String key : fileIdMapping.keySet()){
			String imgName = key;
			CardBean cb = fileBeanMapping.get(imgName);
			
			String id = fileIdMapping.get(key);
			String actualName = idNameMapping.get(id);
			
			System.out.println("BeanName: " + cb.getName());
			System.out.println("BeanenName: " + cb.getName_en());
			System.out.println("actualName: " + actualName);
			
			String expName = cb.getName();
			if( expName == null || expName == "" || expName.toLowerCase() == "null"){
				expName = cb.getName_en();
			}
			
			if( expName == null || expName == "" || expName.toLowerCase() == "null"){
				continue;
			}
			
			expName = Utility.TrimNConvert(expName);
			
			if( actualName == null || actualName == "" || actualName.toLowerCase() == "null"){
				continue;
			}
			
			actualName = Utility.TrimNConvert(actualName);
			if(expName.equals(actualName)){
				matchedCount++;
			}
			
			totalCount++;
			
		}
		
		System.out.println(String.format("Name field: Matched / Total : [%d] / [%d]", matchedCount, totalCount));
	}

	private static void CompareEnName(int count, String csvFile, String enNameFile,
			String filenameFile, int matchedCount, int totalCount)
			throws IOException {
		QNWTestRun testRun = new QNWTestRun();
		
		Map<String, CardBean> fileBeanMapping = testRun.GetFileBeanMapping(csvFile, count);
		Map<String, String> fileIdMapping = testRun.GetFileIdMapping(filenameFile);
		Map<String, String> idEnNameMapping =testRun.GetIdNameMapping(enNameFile);
		for(String key : fileIdMapping.keySet()){
			String imgName = key;
			CardBean cb = fileBeanMapping.get(imgName);
			
			String id = fileIdMapping.get(key);
			String expectedName = cb.getName_en();
			if(expectedName == null || expectedName == "" || expectedName.toLowerCase() == "null"){
				continue;
			}

			String actualName = idEnNameMapping.get(id);
			if(actualName == null || actualName == "" || actualName.toLowerCase() == "null"){
				matchedCount += 0;
			}else{
				expectedName = Utility.TrimNConvert(expectedName);
				actualName = Utility.TrimNConvert(actualName);
				
				System.out.println("BeanName: [" + expectedName + "]");
				System.out.println("actualName: [" + actualName + "]");
				
				if(expectedName.equals(actualName)){
					matchedCount++;
				}
			}
			totalCount++;
			
		}
		
		System.out.println(String.format("Matched / Total : [%d] / [%d]", matchedCount, totalCount));
	}

	private static void CompareEmail(int count, String csvFile, String emailFile,
			String filenameFile, int matchedCount, int totalCount)
			throws IOException {
	QNWTestRun testRun = new QNWTestRun();
		
		Map<String, CardBean> fileBeanMapping = testRun.GetFileBeanMapping(csvFile, count);
		Map<String, String> fileIdMapping = testRun.GetFileIdMapping(filenameFile);
		Map<String, String[]> emails =testRun.GetIdPhonesMapping(emailFile);
		
		for(String key : fileIdMapping.keySet()){
			CardBean cb = fileBeanMapping.get(key);
			String expectedEmail = cb.getEmail();
			
			if(expectedEmail == null || expectedEmail== "" || expectedEmail.toLowerCase() == "null" ){
				continue;
			}
			
			String[] expectedEmails =  expectedEmail.split("@@@");
			
			String id = fileIdMapping.get(key);
			String[] actualEmails = emails.get(id);

			int tmpMatchCount = 0;
			if(actualEmails == null || actualEmails.length == 0){
				tmpMatchCount += 0;
			}else{
//				System.out.println(String.format("Comparing: [%s] :: [%s]", cb.getName(), cb.getImgname()));
				
				for(String act : actualEmails){
					for(String s : expectedEmails){
						
//						System.out.println(String.format("Matching: [%s] -> [%s]", s, act));
						act=Utility.TrimNConvert(act);
						String exp = Utility.TrimNConvert(s);
						
						if(act.equals(exp)){
							tmpMatchCount++;
						}
					}
				}
				
//				System.out.println(String.format("Matched / Total: [%d] / [%d]", tmpMatchCount, expectedEmails.length));
				
			}
			
			matchedCount += tmpMatchCount;
			totalCount += expectedEmails.length;
			
		}
		
		System.out.println(String.format("Mail Matched / Total : [%d] / [%d]", matchedCount, totalCount));
	}

	private static void CompareWebsite(int count, String csvFile, String websiteFile,
			String filenameFile, int matchedCount, int totalCount)
			throws IOException {
		QNWTestRun testRun = new QNWTestRun();
		
		Map<String, CardBean> fileBeanMapping = testRun.GetFileBeanMapping(csvFile, count);
		Map<String, String> fileIdMapping = testRun.GetFileIdMapping(filenameFile);
		Map<String, String[]> websites =testRun.GetIdPhonesMapping(websiteFile);
		
		for(String key : fileIdMapping.keySet()){
			CardBean cb = fileBeanMapping.get(key);
			String expectedWebsite = cb.getWebsite();
			
			if(expectedWebsite == null || expectedWebsite== "" || expectedWebsite.toLowerCase() == "null" ){
				continue;
			}
			
			String[] expectedWebsites =  expectedWebsite.split("@@@");
			
			String id = fileIdMapping.get(key);
			String[] actualWebsites = websites.get(id);

			int tmpMatchCount = 0;
			if(actualWebsites == null || actualWebsites.length == 0){
				tmpMatchCount += 0;
			}else{
				System.out.println(String.format("Comparing: [%s] :: [%s]", cb.getName(), cb.getImgname()));
				
				for(String act : actualWebsites){
					for(String s : expectedWebsites){
						
						act=Utility.TrimNConvert(act);
						String exp = Utility.TrimNConvert(s);
						
						for(String pr : QNWTestRun.websitePrefix){
							if(exp.startsWith(pr) && !act.startsWith(pr)){
								exp = exp.replace(pr, "");
							}
							
							if(act.startsWith(pr) && !exp.startsWith(pr)){
								act = act.replace(pr, "");
							}
						}
						
						System.out.println(String.format("Matching: [%s] -> [%s]", exp, act));
						if(act.equals(exp)){
							tmpMatchCount++;
						}
					}
				}
				
				System.out.println(String.format("Matched / Total: [%d] / [%d]", tmpMatchCount, expectedWebsites.length));
				
			}
			
			matchedCount += tmpMatchCount;
			totalCount += expectedWebsites.length;
			
		}
		
		System.out.println(String.format("Matched / Total : [%d] / [%d]", matchedCount, totalCount));
	}
	
	public Map<String, CardBean> GetFileBeanMapping(String csvFile, int count) throws IOException{
		Map<String, CardBean> fileBeanMap = new HashMap<String, CardBean>();
		CsvBeanReader reader =CardBean.InitReader(csvFile, "UTF-8", CsvPreference.EXCEL_PREFERENCE);
		CardBean cb = new CardBean();
		while(reader.getLineNumber() < Index ){
			reader.read(cb.getClass(), CardBean.header);

		}
		while(count-- > 0){
			try {
				cb = reader.read(cb.getClass(), CardBean.header);

				// if cb == null, means the csv file reach the end
				if(cb == null){
					break;
				}else{
					if(cb.getImgname() != null && cb.getImgname() != ""){
						fileBeanMap.put(cb.getImgname(), cb);
						
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				reader.close();
				return fileBeanMap;
			}
		}
		
		return fileBeanMap;
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
		
		InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), ENCODING);
		BufferedReader br = new BufferedReader(isReader);
		try{
			// first line is the db table title, useless, skip
			String line = "";
			
			// the id index starts with 1st;
			int index = 1;
			
			// null means the EOF
			while(line != null){
				
				// here is the content 
				line = br.readLine();
				if(line == null || line == "" ){
					break;
				}
				// trim to ignore the whitespaces
				line = line.trim();
				
				if(line != null && line != "" )
				{
					line = line + ".jpg";
					fileIdMapping.put(line, String.valueOf(index));
					index++;
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
		
		return fileIdMapping;
	}

	/**
	 * The name of contact, field id is '1'
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> GetIdNameMapping(String fileName) throws IOException{
		return splitToString(fileName);
	}
	
	public Map<String, String> GetIdAddressMapping(String fileName) throws IOException{
		return splitToString(fileName);
	}
	
	/**
	 * QNW does not seperate the phone & mobile, the field id is '2'
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public Map<String, String[]> GetIdPhonesMapping(String fileName) throws IOException{
		return splitToStringArray(fileName);
	}

	public Map<String, String[]> GetIdIMsMapping(String fileName) throws IOException{
		return splitToStringArray(fileName);
	}

	/**
	 * QNW seems like put company & title together, the field id is '4'
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public Map<String, String[]> GetIdTitleMapping(String fileName) throws IOException{
		return this.splitToStringArray(fileName);
	}

	/**
	 * The value of email field, field id is '5' 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> GetIdEmailMapping(String fileName) throws IOException{
		return this.splitToString(fileName);
	}
	
	/**
	 * The field id is '7', website of the contact
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> GetIdWebsiteMapping(String fileName) throws IOException{
		return this.splitToString(fileName);
	}
	
	protected Map<String, String> splitToString(String fileName) throws IOException{
		File file = new File(fileName);
		if(!file.exists()){
			throw new java.io.FileNotFoundException();
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), ENCODING);
		BufferedReader br = new BufferedReader(isReader);
		try{
			// first line is the db table title, useless, skip
			String line = br.readLine();
			
			// null means the EOF
			while(line != null){
				
				// here is the content 
				line = br.readLine();
				if(line == null){
					break;
				}
				
				String[] contents = line.split(QNWTestRun.ContentSeperator);
				if(contents.length == 2){
					String key = Compl2Simpl.Compl2Simpl(contents[0]);
					String value = Compl2Simpl.Compl2Simpl(contents[1]);
					
					map.put(key, value);
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

	protected Map<String, String[]> splitToStringArray(String fileName) throws IOException{
		// FieldsSeperator
		
		File file = new File(fileName);
		if(!file.exists()){
			throw new java.io.FileNotFoundException();
		}
		
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		
		InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), ENCODING);
		BufferedReader br = new BufferedReader(isReader);
		try{
			// first line is the db table title, useless, skip
			String line = br.readLine();
			
			// null means the EOF
			while(line != null){
				
				// here is the content 
				line = br.readLine();
				if(line == null){
					break;
				}
				
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
