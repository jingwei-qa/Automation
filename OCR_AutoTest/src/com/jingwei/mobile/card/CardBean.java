package com.jingwei.mobile.card;

import java.beans.Beans;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.jingwei.mobile.log.Log;
import com.jingwei.mobile.util.Compl2Simpl;
import com.jingwei.mobile.util.Levenshtein;
import com.jingwei.mobile.util.Utility;

/**
 * 
 * @author nan.lin3@renren-inc.com
 * @DateModifled Apr 10, 2014
 */
public class CardBean extends Beans {

	/**
	 * Empty initial constructor, 
	 * added for CSV bean reader.
	 */
	public CardBean() {
		// I am a card bean
	}

	// All the headers of the stored data 
	// Use to init the CardBean instance as headers' set.
	public static String[] header = { 
			"card_id", 
			"name", 
			"name_en", 
			"title", 
			"title_en", 
			"phone_company", 
			"phone_fax", 
			"mobile", 
			"email", 
			"company", 
			"address", 
			"im",  
			"website", 
			"create_time", 
			"card_pic_url",
			"imgname", 
			"folder" 
			};
	
	public String card_id;
	public String name;
	public String name_en;
	public String title;
	public String title_en;
	public String phone_company;
	public String phone_fax;
	public String mobile;
	public String email;
	public String company;
	public String address;
	public String im;
	public String website;
	public String create_time;
	
	// this field is not correct.
	public String card_pic_url;
	
	public String imgname;
	public String folder;
	
	// Getter & setters for the fields.
	
	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle_en() {
		return title_en;
	}

	public void setTitle_en(String title_en) {
		this.title_en = title_en;
	}

	public String getPhone_company() {
		return phone_company;
	}

	public void setPhone_company(String phone_company) {
		this.phone_company = phone_company;
	}

	public String getPhone_fax() {
		return phone_fax;
	}

	public void setPhone_fax(String phone_fax) {
		this.phone_fax = phone_fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCard_pic_url() {
		return card_pic_url;
	}

	public void setCard_pic_url(String card_pic_url) {
		this.card_pic_url = card_pic_url;
	}

	public String getImgname() {
		return imgname;
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	/**
	 * Override the toString() method derived from object.
	 * Give a pretty formatted view
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		int width = 20;
		sb.append(String.format("%-"+width+"s:%s%n", "Name", this.name));
		sb.append(String.format("%-"+width+"s:%s%n", "EN Name", this.name_en));
		sb.append(String.format("%-"+width+"s:%s%n","Title", this.title));
		sb.append(String.format("%-"+width+"s:%s%n", "EN Title", this.title_en));
		sb.append(String.format("%-"+width+"s:%s%n", "Phone_Company", this.phone_company));
		sb.append(String.format("%-"+width+"s:%s%n", "Phone_Fax", this.phone_fax));
		sb.append(String.format("%-"+width+"s:%s%n", "Mobile", this.mobile));
		sb.append(String.format("%-"+width+"s:%s%n", "Company", this.company));
		sb.append(String.format("%-"+width+"s:%s%n", "Address", this.address));
		sb.append(String.format("%-"+width+"s:%s%n", "IM", this.im));
		sb.append(String.format("%-"+width+"s:%s%n", "Website", this.website));
		sb.append(String.format("%-"+width+"s:%s%n", "CreateTime", this.create_time));
		sb.append(String.format("%-"+width+"s:%s%n", "Card_pic_url", this.card_pic_url));
		sb.append(String.format("%-"+width+"s:%s%n", "ImgName", this.imgname));
		sb.append(String.format("%-"+width+"s:%s%n", "Folder", this.folder));
				
		return sb.toString();
	}
	
	public static int NOTMATCH = 1;
	public static int MATCHED = 0;
	/**
	 * if strict is true, each field only can have one value, 
	 * if the card contains more than 1 field, like card with two Address, 
	 * the function will think the address could not match.
	 * Otherwise, only check if the address fields CONTAINS in the card ocr result.
	 * 
	 * @param card, the card to compare with
	 * @param cf, card fields, DO NOT use values beyond the 'ICardFields' interface.
	 * @return if the fields match perfectly, return 0, one difference, return value +1
	 * will return -1 if one of the expected value is null, unexpected or any invalid value/type.   			
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * 
	 */
	public static int matchCard(CardBean cardBean, Card card, int cf, double matchRate) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		// TODO: complete this method to give a result,
		// TODO: Need to calculate the value according to the difference.
		
		for(int i = 0; i < CardFactory.header.length; i++){
			int p_csv = (int) Math.pow(2, i);
			
			// if the cf containts this fields, do matching.
//			if( (p_csv & cf) != 0 ){
			if(p_csv == cf){
				// some of the fields in the ocr result, does not stored in the csv file. 
				// Ignore those fields for now. 
				if(ICardHeaders.HeaderFieldMapping.containsKey(p_csv))
				{
					String fieldStr = CardFactory.header[i];
					
					// find all the fields, which attribute value equals to fieldInOCR.
					// record the count, and if any of the matched values equal to the expected value.
					String expectedValue = String.valueOf(cardBean.getField(fieldStr)).replace(" ", "");
					if(expectedValue == null || expectedValue == "" || expectedValue.toLowerCase() == "null"){
						return -1;
					}
					

					int fieldInOCR = ICardHeaders.HeaderFieldMapping.get(p_csv);
					String actualValue = null;
					for(int k=0; k<card.count ; k++){
						
						if(fieldInOCR != card.attrs[k]){
							continue;
						}
						
						actualValue = Compl2Simpl.Compl2Simpl(card.values[k].replace(" ", ""));
						int distance = Levenshtein.Compare(expectedValue.toLowerCase(), actualValue.toLowerCase());
						
						double matchPercentage = new Double(expectedValue.length() - distance) / expectedValue.length();
						
						// if the real matchPercentange less than the given value, match failed
						if(matchPercentage >= matchRate){
							return 0;
						}else{
							return 1;
						}
						
					}
				}
				
			}
		}
		
		// recursively validating all the fields, if not return ,return -1 to mark the record as IGNORE
		return -1;
	}
	
	/**
	 * Add a reflection method to read field easier.
	 * For this method could not read the private fields, so change the modifier to PUBLIC. 
	 * @param field - field's name
	 * @return The field object,
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Object getField(String field) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Class objClass = this.getClass();
		Field targetField = objClass.getField(field);
		return targetField.get(this);
	}
	
	/**
	 * Lighter version of InitReader, only need to pass the csv file path
	 * The encoding use gb18030 as default, which works file currently.
	 * And the CsvPreference is EXCEL_PREFERENCE, also works file so far.
	 * 
	 * @param csvFilePath
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public static CsvBeanReader InitReader(String csvFilePath) throws UnsupportedEncodingException, FileNotFoundException{
		return InitReader(csvFilePath, "gb18030", CsvPreference.EXCEL_PREFERENCE);
	}
	
	/**
	 * Initialize a CsvBeanReader with the csvFilePath & encoding.
	 * Remember to close the reader after use.
	 * 
	 * @param csvFilePath 
	 * @param encoding, in our case is gb18030
	 * @param preference, you may use CsvPreference.EXCEL_PREFERENCE
	 * @return
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public static CsvBeanReader InitReader(String csvFilePath, String encoding, CsvPreference preference) throws UnsupportedEncodingException, FileNotFoundException{		
		Reader streamReader = new InputStreamReader(new FileInputStream(csvFilePath), encoding);
		return new CsvBeanReader(streamReader, preference);
	}


}
