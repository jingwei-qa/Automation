package com.jingwei.mobile.card;

/**
 * Store all the fields in binary format.
 * 
 * 
 * @author nan.lin3@renren-inc.com
 * @DateModifled: Apr 17, 2014
 */
public interface ICardFields {
	
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
	
}
