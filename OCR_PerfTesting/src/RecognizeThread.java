import java.io.File;
import java.io.FilenameFilter;
import java.util.Random;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardFactory;
import com.jingwei.mobile.log.Log;

public class RecognizeThread extends Thread{
		
		static int breakLengthInMillisecond = 700; 
		static int breakAfterScanCardsNumber = 1000;
		static int RandomBase = 1000;
		int size;
		static CardFactory cf;
		String picsetRoot;
		String ocrInitPath;
		
		/**
		 * The thread for running ocr service.
		 * 
		 * @param size how many files
		 * @param picSetPath
		 * @param ocrInitPath
		 * @throws Exception
		 */
		public RecognizeThread(int size, String picSetPath, String ocrInitPath) throws Exception{
			if (cf == null)
				cf = CardFactory.InitCardFactory(ocrInitPath);
			this.picsetRoot = picSetPath;
			this.size = size;
			this.ocrInitPath = ocrInitPath;
			
			// start the thread once as it's created.
			start();
		}
		
		public void run(){
			File picset = new File(picsetRoot);
			Log.Log("picsetRoot: " + picsetRoot);
			String[] files = picset.list(
				// Use anonymous class as a filter to get jpg, jpeg and webp files
				new FilenameFilter(){
					public boolean accept(File f, String fname){
						return fname.toLowerCase().endsWith(".jpg") || fname.toLowerCase().endsWith(".jpeg") || fname.toLowerCase().endsWith(".webp");
					}
				});
			int totalCount = files.length;
			int randomCardNo; 
			while(true){
				
				randomCardNo = new Random().nextInt(RandomBase);
						
				for(int i = 0; i < randomCardNo; i++){
					
					int index = i % totalCount;

					String filePath = picsetRoot + "/" + files[index];
					Log.Log("Reading: " + filePath);
					try {
						RecogonizeImageCard(filePath);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				try {
					Thread.sleep(breakLengthInMillisecond);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public void RecogonizeImageCard(String imageFilePath) throws Exception{
			
			Card card = cf.Make(imageFilePath);
			if(card == null){
				
				System.err.println("Recognizing failed!");
			}else{
				card.Print();
			}
		}
	}
