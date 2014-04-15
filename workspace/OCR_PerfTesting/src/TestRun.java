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


public class TestRun extends Thread{

	String csvFile, rootPath, configFilePath;
	boolean strict;
	int fields;
	
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
	 * TODO: Add log method to log the details of OCR running & comparing
	 */
	public TestRun(String csvFile, String rootPath, String configFilePath, boolean strict, int fields) throws FileNotFoundException {
		
		File file = new File(rootPath);
		if(!file.exists()){
			throw new FileNotFoundException();
		}
		
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
			// TODO Auto-generated catch block
			// TODO Log the failure
			System.out.println("Initial Card Factory failed");
			e.printStackTrace();
		}
		
		// Create a csv reader
		Reader streamReader = null;
		try {
			streamReader = new InputStreamReader(new FileInputStream(csvFile), "gb18030");
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
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
				
				if(cb.getFolder() != null & !cb.getFolder().isEmpty() & cb.getImgname()!=null & !cb.getImgname().isEmpty()){
					String cardImgFilePath = this.rootPath + java.io.File.separator +  cb.getFolder() + java.io.File.separator + cb.getImgname();
					Card card = cf.Make(cardImgFilePath);
					
					int diffEach = CardBean.matchCard(cb, card, strict, fields);
					diffs += diffEach;
					
					// TODO: Log the details, use out.println for now
					if(diffEach != 0){
						System.out.println(String.format("Failed! Card [%s] does not match the OCR result of [%s]", cb.getCard_id(), cardImgFilePath));
					}else{
						System.out.println(String.format("successfully! Card [%s] match the OCR result of [%s] ", cb.getCard_id(), cardImgFilePath));
					}
				}
				
			} catch (IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
			}
		}while(cb!= null);
			
		// close the bean reader
		try {
			beanReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
