import java.io.*;
import java.util.List;

import com.jingwei.mobile.log.Log;
import com.jingwei.mobile.ocr.OCR;
import com.jingwei.mobile.util.Utility;
import com.jingwei.mobile.card.*;

public class demo2 {
	// This is where the root folder of pictures stored, in this case, the pic
	// were stored in path like:
	// "/home/testuser/ln/picset/20140221/*.jpg"
	// public static String picsetRoot = "/home/testuser/ln/picset/";

	// this is my own card pictures storage folder,
	// mounted on nan.lin3's ubuntu, change this as your own path.
	public static String picsetRoot = "/mnt/picset/";

	public static int size = 8000;
	public static String ocrInitPath = "ocr_data/";

	/*
	 * There are 14 folders in /mnt/picset/ folder(//10.2.54.9/card_record), To
	 * avoid thread lock, better use thread less then 14. If need more, please
	 * use subfolder of the picset not support multi-thread so far, but as told,
	 * ocr support multi-process invocation.
	 */
	public static int threadCount = 1;
	public static CardFactory cf;
	public static Log runLog;

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		runLog = Log.GetInstance("ocradhoc.log");
		cf = CardFactory.InitCardFactory(ocrInitPath);

		File rootFolder = new File(picsetRoot);
		int count = 0;
		// int total = allPictures.size();
		String[] subFolders = rootFolder.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.startsWith("2014");
			}
		});

		while (true) {
			try {
				for (String subFolderStr : subFolders) {
					File subF = new File(picsetRoot + File.separatorChar
							+ subFolderStr);
					if (subF.isDirectory()) {
						String[] pics = subF.list(new FilenameFilter() {

							@Override
							public boolean accept(File dir, String name) {
								// TODO Auto-generated method stub
								return name.endsWith("jpg");
							}
						});

						for (String picFile : pics) {

							Card card = cf.Make(subF.getAbsolutePath()
									+ File.separatorChar + picFile);
							System.out.println(card);

						}

					}
				}
			} catch (Exception ex) {
				runLog.Write(ex.getMessage());
			}
		}
	}

	/**
	 * This is the original demo for calling the ocr library.
	 * 
	 * @param filepath
	 */
	public static void Invoke(String filepath) {

		RandomAccessFile fis = null;
		try {
			fis = new RandomAccessFile(filepath, "r");
			System.out.println("len = " + fis.length());
			byte[] cbuf = new byte[(int) fis.length()];
			int clen = fis.read(cbuf);
			System.out.println("rlen = " + clen);
			OCR ocr = new OCR();
			if (ocr == null)
				return;

			if (!ocr.ocr_init("ocr_data/")) {
				System.out.println("init fail");
				return;
			}

			/*
			 * if(!ocr.ocr_readJpgBuffer(clen, cbuf)) {
			 * System.out.println("read buff fail"); return; }
			 */
			/*
			 * if(!ocr.ocr_read("/home/yunkai/Desktop/OCR_Server/java/000.webp"))
			 * { System.out.println("read file fail"); return; }
			 */
			if (!ocr.ocr_readWebpBuffer(clen, cbuf)) {
				System.out.println("read buff fail");
				return;
			}

			System.out.println("num = " + ocr.num);
			for (int i = 0; i < ocr.num; i++) {
				System.out.println("attr[" + i + "] = " + ocr.attr[i] + "\t"
						+ ocr.strs[i]);
			}

			ocr.ocr_free();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
