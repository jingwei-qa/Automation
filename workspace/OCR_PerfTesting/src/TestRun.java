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

	String csvFile, rootPath, configFilePath;
	boolean strict;
	int fields;
	
	int bingo = 0;
	int amount = 0;
	
	int size = -1;
	// sizep start from 1, since the do ... while will execute once before circle starts.
	
	public TestRun(String csvFile, String rootPath, String configFilePath, boolean strict, int fields) throws FileNotFoundException {
		this(csvFile, rootPath, configFilePath, strict, fields, -1);
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
	public TestRun(String csvFile, String rootPath, String configFilePath, boolean strict, int fields, int size) throws FileNotFoundException {
		
		File file = new File(rootPath);
		if(!file.exists()){
			throw new FileNotFoundException();
		}
		
		this.size = size;
		this.csvFile = csvFile;
		this.rootPath = rootPath;
		this.configFilePath = configFilePath;
		this.strict = strict;
		this.fields = fields;
		
		// start the thread when initialized.
//		start();
	}
	
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
		int diffs = 0;
		
		do{
			try {
				cb = beanReader.read(cb.getClass(), CardBean.header);
				
				// if the cb is null, break
				// maybe the beanReader reach the EOF.
				// initial failed, or any other special situation
				if (cb == null){
					break;
				}
				
				if(cb.getFolder() == null || cb.getImgname() == null){
					continue;
				}
				
				if(cb.getFolder() != null & !cb.getFolder().isEmpty() & cb.getImgname()!=null & !cb.getImgname().isEmpty()){
					String cardImgFilePath = this.rootPath + java.io.File.separator +  cb.getFolder() + java.io.File.separator + cb.getImgname();
					Card card = cf.Make(cardImgFilePath);
					
					int diffEach = CardBean.matchCard(cb, card, strict, fields);
					diffs += diffEach;
					
					if(diffEach != 0){
						Log.Log(String.format("Failed! Card [%s] does not match the OCR result of [%s]", cb.getCard_id(), cardImgFilePath));
					}else{
						Log.Log(String.format("successfully! Card [%s] match the OCR result of [%s] ", cb.getCard_id(), cardImgFilePath));
						bingo++;
					}
				}
				
			} catch (IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}finally{
				amount++;
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
