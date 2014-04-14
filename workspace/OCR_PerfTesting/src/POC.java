
import java.io.*;
import java.util.List;

import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

import com.jingwei.mobile.card.*;

public class POC {
	
	public static void main(String[] args) throws Exception{ 
		
		int ii = 1;
		int iii =2;
		int iiii = 4;
		int k = ii | iii | iiii;
		System.out.println(" operation: " + (16 | 8));
		if( (24 & 32) != 0){
			System.out.println("Failed");	
		}
		System.out.println(k);
		System.out.println(((int)Math.pow(2 , 16)));
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
