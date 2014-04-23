import java.io.*;

import com.jingwei.mobile.ocr.OCR;
import com.jingwei.mobile.card.*;


public class demo
{
	// This is where the root folder of pictures stored, in this case, the pic were stored in path like:
	// "/home/testuser/ln/picset/20140221/*.jpg"
	// public static String picsetRoot = "/home/testuser/ln/picset/";
	
	// this is my own card pictures storage folder, 
	// mounted on nan.lin3's ubuntu, change this as your own path.
	public static String picsetRoot = "/mnt/picset/";
	
	public static int size = 8000;
	public static String ocrInitPath = "ocr_data/";
	
	/* 
	 * There are 14 folders in /mnt/picset/ folder(//10.2.54.9/card_record), 
	 * To avoid thread lock, better use thread less then 14. 
	 * If need more, please use subfolder of the picset
	 * not support multi-thread so far, but as told, ocr support multi-process invocation.
	 */
	public static int threadCount = 1; 
	
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String [] args) throws Exception
	{
		File picRoot = new File(picsetRoot);
		
		// Get all the folders start with '2014', the parent folder for *.jpg files
		File[] picFolders = picRoot.listFiles(new FilenameFilter(){
					public boolean accept(File f, String fname){
						return fname.toLowerCase().startsWith("2014");
					}
				});
		
		System.out.println("picFolders: " + picFolders.length);
		
		// The OCR support multi-process but not multi-thread, so keep the threadCount =1.
		for(int i = 0; i < threadCount && i < picFolders.length; i++){
			new RecognizeThread(size, picFolders[i % picFolders.length].getAbsolutePath(), ocrInitPath);
		}
		
	}
	
	static CardFactory cf;
	public static void RecogonizeImageCard(String imageFilePath) throws Exception{
		if (cf == null)
		{
			cf = CardFactory.InitCardFactory("ocr_data/");
		}
		
		Card card = cf.Make(imageFilePath);
		if(card == null){
			
			System.err.println("Recognizing failed!");
		}else{
			card.Print();
		}
	}
	
	/**
	 * This is the original demo for calling the ocr library.
	 * @param filepath
	 */
	public static void Invoke(String filepath){
		
		RandomAccessFile fis = null;
		try
		{
			fis = new RandomAccessFile(filepath,"r");
			System.out.println("len = " + fis.length());
			byte [] cbuf = new byte [(int)fis.length()];
			int clen = fis.read(cbuf);
			System.out.println("rlen = " + clen);
			OCR ocr = new OCR();
			if(ocr == null)
				return;

			if(!ocr.ocr_init("ocr_data/"))
			{
				System.out.println("init fail");
				return;
			}

/*			if(!ocr.ocr_readJpgBuffer(clen, cbuf))
			{
				System.out.println("read buff fail");
				return;
			}
*/			
/*			if(!ocr.ocr_read("/home/yunkai/Desktop/OCR_Server/java/000.webp"))
			{
				System.out.println("read file fail");
				return;
			}
*/
			if(!ocr.ocr_readWebpBuffer(clen, cbuf))
			{
				System.out.println("read buff fail");
				return;
			}

			System.out.println("num = "+ocr.num);
			for(int i=0; i<ocr.num; i++)
			{
				System.out.println("attr["+i+"] = "+ocr.attr[i]+"\t"+ocr.strs[i]);
			}
			 
			ocr.ocr_free();
			fis.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	
}
