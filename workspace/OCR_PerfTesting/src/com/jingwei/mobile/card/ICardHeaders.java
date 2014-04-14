package com.jingwei.mobile.card;

import java.util.HashMap;
import java.util.Map;

/**
 * All the fileds' header in the excel.
 * Supported to use enum for this, final int
 * but seems like java's enum type does not support '^' operator
 * Have to use int in an interface instread.  
 * @author nan.lin3
 *
 */
public interface ICardHeaders {
	
	// The headers list of .csv file
	public int CARD_ID = 1;
	public int NAME = 2;
	public int NAME_EN = 4;
	public int TITLE = 8;
	public int TITLE_EN= 16;
	public int PHONE_COMPANY= 32;
	public int PHONE_FAX = 64;
	public int MOBILE = 128;
	public int EMAIL = 256;
	public int COMPANY = 512;
	public int ADDRESS = 1024;
	public int IM = 2048;
	public int WEBSITE = 4096;
	public int CREATE_TIME = 8192;
	public int CARD_PIC_URL = 16384;
	public int IMGNAME = 32768;
	public int FOLDER = 65536;
	
	// The fields of OCR result.
	public static int NAMECARD_NAME_EN = 0;    //英文姓名
	public static int NAMECARD_NAME_CN = 1;     //中文姓名
	public static int NAMECARD_TITLE = 2;       //职称
	public static int NAMECARD_TELPHONE = 3;    //电话
    public static int NAMECARD_FAX = 4;         //传真
    public static int NAMECARD_CELLPHONE = 5;   //手机
    public static int NAMECARD_EMAIL = 6;       //邮箱
    public static int NAMECARD_WEBURL = 7;      //主页
    public static int NAMECARD_IM = 8;          //如QQ
    public static int NAMECARD_ADDRESS = 9;     //地址
    public static int NAMECARD_POSTCODE = 10;    //邮编
    public static int NAMECARD_COMPANY = 11;     //公司
    public static int NAMECARD_DEPARTMENT = 12;  //部门
    public static int NAMECARD_UNKNOWN = 13;     //未知
    
    //OCR组使用信息, 
    public static int NAMECARD_BBCALL = 14;      //传呼, 以电话输出
    public static int NAMECARD_OTHER = 15;     //如统一编号，以未知输出
    
    /**
     * The key is the fields id from OCR result, the value is the fields id from .csv file.
     */
	public static HashMap<Integer, Integer> FieldHeaderMapping = new HashMap<Integer, Integer>(){
		{
			put(NAMECARD_NAME_EN, NAME_EN); // NAMECARD_NAME_EN -> NAME_EN
			put(NAMECARD_NAME_CN,NAME); // NAMECARD_NAME_CN -> NAME
			put(NAMECARD_TITLE,TITLE); // NAMECARD_TITLE	 -> TITLE
			put(NAMECARD_TELPHONE,PHONE_COMPANY); // NAMECARD_TELPHONE -> PHONE_COMPANY
			put(NAMECARD_FAX,PHONE_FAX); // NAMECARD_FAX -> PHONE_FAX
			put(NAMECARD_CELLPHONE,MOBILE); // NAMECARD_CELLPHONE -> MOBILE
			put(NAMECARD_EMAIL,EMAIL); // NAMECARD_EMAIL -> EMAIL
			put(NAMECARD_WEBURL,WEBSITE); // NAMECARD_WEBURL -> WEBSITE
			put(NAMECARD_IM,IM); // NAMECARD_IM -> IM
			put(NAMECARD_ADDRESS,ADDRESS); // NAMECARD_ADDRESS -> ADDRESS
			put(NAMECARD_COMPANY,COMPANY); //  NAMECARD_COMPANY -> COMPANY
			
			// Not in use in .csv file
//			put(10,1); // NAMECARD_POSTCODE
//			put(12,1); // NAMECARD_DEPARTMENT
//			put(13,1); // NAMECARD_UNKNOWN
//			put(14,1); // NAMECARD_BBCALL
//			put(15,1); // NAMECARD_OTHER
		}
	};
	
	/**
     * The key is the fields id from OCR result, the value is the fields id from .csv file.
     */
	public static HashMap<Integer, Integer> HeaderFieldMapping = new HashMap<Integer, Integer>(){
		{
			put(NAME_EN,NAMECARD_NAME_EN); // NAMECARD_NAME_EN -> NAME_EN
			put(NAME,NAMECARD_NAME_CN); // NAMECARD_NAME_CN -> NAME
			put(TITLE,NAMECARD_TITLE); // NAMECARD_TITLE	 -> TITLE
			put(PHONE_COMPANY,NAMECARD_TELPHONE); // NAMECARD_TELPHONE -> PHONE_COMPANY
			put(PHONE_FAX, NAMECARD_FAX); // NAMECARD_FAX -> PHONE_FAX
			put(MOBILE,NAMECARD_CELLPHONE); // NAMECARD_CELLPHONE -> MOBILE
			put(EMAIL, NAMECARD_EMAIL); // NAMECARD_EMAIL -> EMAIL
			put(WEBSITE, NAMECARD_WEBURL); // NAMECARD_WEBURL -> WEBSITE
			put(IM, NAMECARD_IM); // NAMECARD_IM -> IM
			put(ADDRESS, NAMECARD_ADDRESS); // NAMECARD_ADDRESS -> ADDRESS
			put(COMPANY,NAMECARD_COMPANY); //  NAMECARD_COMPANY -> COMPANY
			
		}
	};
}

