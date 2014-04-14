package com.jingwei.mobile.card;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Dictionary;
import java.util.Hashtable;
import java.io.FileNotFoundException;

import com.jingwei.mobile.ocr.OCR;

public class CardFactory implements java.io.Closeable {
	
	/**
	 * Use a dictionary to store the created factories, 
	 * string is the path of the initialization folder, 
	 */
	static Dictionary<String, CardFactory> factories = new Hashtable<String, CardFactory>();

	/**
	 * The header of the csv file content, use to create bean instance
	 */
	static String[] header = { 
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
		"card_pic_url  ",
		"imgname", 
		"folder" 
		};
	
	private OCR ocr;
	
	/*
	 * Create instance of the cards, need a initial file path as begin
	 */
	private CardFactory(String initFilePath) throws Exception{
		
		this.ocr = new OCR();
		if(ocr == null){
			throw new Exception("Create OCR failed");
		}
		
		ocr.ocr_init(initFilePath);
	}
	
	/*
	 * Use a static method to make sure not create the same CardFactory  
	 */
	public static CardFactory InitCardFactory(String configFilePath) throws Exception{
		if(configFilePath == null)
			throw new NullPointerException("The initial setting folder path is null");
		
		File temp = new File(configFilePath);
		if(!temp.exists()){
			throw new FileNotFoundException("The path for initializing ocr not found");
		}
		
		if(factories.get(temp.getAbsolutePath()) != null){
			return factories.get(configFilePath);
		}
		else{
			// Initialize a CardFactory instance and store in the factories dictionary.
			CardFactory cf = new CardFactory(configFilePath);
			factories.put(temp.getAbsolutePath(), cf);
			return cf;
		}
	}
	
	/*
	 * Create a Card entity from an image. 
	 * imageFilePath should have the appendix filename like .jpg, 
	 * or will process as webp format
	 */
	public Card Make(String imageFilePath) throws IOException{
		
		Boolean iswebp = true;
		String upperPath = imageFilePath.toUpperCase();
		if(upperPath.endsWith(".JPG") || upperPath.endsWith(".JPEG")){
			iswebp = false;
		}
		
		RandomAccessFile fis = null;
		
		try{
		fis = new RandomAccessFile(imageFilePath, "r");
		byte [] cbuf = new byte [(int)fis.length()];
		int clen = fis.read(cbuf);
		
//		System.out.println("rlen = " + clen);
		Boolean loadSuccess = false;
		if(iswebp){
			loadSuccess = ocr.ocr_readWebpBuffer(clen, cbuf);
		}
		else{
			loadSuccess = ocr.ocr_readJpgBuffer(clen, cbuf);
		}

		if(loadSuccess)
		{
			return new Card(ocr.num, ocr.strs, ocr.attr);
		}
		
		}catch(IOException ioe){
			// TODO: handle & log the exception
		}catch(Exception ex){
			// TODO: handle & log the exception
		}finally{
			if(fis != null){
				fis.close();
			}
		}
		
		return null;
	}
	
	/*
	 * Free the resource used by OCR engine.
	 */
	@Override
	public void close() throws IOException {
		this.ocr.ocr_free();
	}
	
}
