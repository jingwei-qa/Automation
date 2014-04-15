
import java.io.*;
import java.util.List;

import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

import com.jingwei.mobile.card.*;

public class POC {
	
	public static void main(String[] args) throws Exception{
		
		String a = "aa";
		String b ="aa";
		System.out.println(a == b);
		
		String csvFile = "ocr_info_utf8_10.csv";
		String rootPath = "/mnt/picset";
		String configFilePath = "ocr_data/";
		boolean strict = false;

		//int fields = ICardFields.NAME | ICardFields.MOBILE; // can add more | to match more fields
		int fields = ICardFields.NAME;

		TestRun tr = new TestRun(csvFile, rootPath, configFilePath, strict, fields);
		
		tr.start();
		
		String[] values = {"linnan", "15210282499"};
		int[] attrs = {ICardHeaders.NAMECARD_NAME_CN, ICardHeaders.NAMECARD_CELLPHONE};
		
		Card card = new Card(2, values, attrs);
		CardBean cb = new CardBean();
		cb.setName(values[0]+'a');
		cb.setMobile(values[1]);
//		int matches = CardBean.matchCard(cb, card, true, ICardFields.NAME | ICardFields.MOBILE);
		int matches = CardBean.matchCard(cb, card, true,  ICardFields.NAME | ICardFields.MOBILE);
		
		System.out.println("matches: \t" + matches);
		
//		int ii = 1;
//		int iii =2;
//		int iiii = 4;
//		int k = ii | iii | iiii;
		System.out.println(" operation: " + (128));
		System.out.println( 64 & (2 | 128));
		
//		if( (24 & 32) != 0){
//			System.out.println("Failed");	
//		}
//		System.out.println(k);
//		System.out.println(((int)Math.pow(2 , 16)));
		/*
		Reader streamReader = new InputStreamReader(new FileInputStream("ocr_info_utf8_10.csv"), "gb18030");
		
		//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("ocr_info_utf8_10.csv"), "gb18030"));
//		CsvListReader reader = new CsvListReader(, CsvPreference.EXCEL_PREFERENCE);
//		List<String> contents = null;
//		while((contents = reader.read() ) != null){
//			System.out.println(contents.size());
////			for(String con : contents){
////				System.out.print(con + "\t");
////			}
//			if(contents.size() > 10){
//				String foldername = contents.get(contents.size() - 1);
//				String filename = contents.get(contents.size() - 2);
//				
//				System.out.println(foldername + File.separator + filename);
//				System.out.println(contents.size());
//			}
//		}
		
		CardBean cb, cardBean = new CardBean();
		CsvBeanReader beanReader = new CsvBeanReader(streamReader, CsvPreference.EXCEL_PREFERENCE);
		while((cb = beanReader.read(cardBean.getClass(), CardBean.header)) != null )
		{
			System.out.println(cb);
			
		}
		
		beanReader.close();
		streamReader.close();*/
//		String s = new String();
//		while((s = br.readLine()) != null){
//			System.out.println(s);
//			String[] split = s.split(",");
//			for(String sp : split){
//				System.out.println(sp);
//			}
//			System.out.println(split.length);
//		}
//		br.close();
	}

}
