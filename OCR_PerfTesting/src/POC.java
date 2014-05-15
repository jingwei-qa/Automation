import com.jingwei.mobile.card.*;
import com.jingwei.mobile.testrun.TestRun;
import com.jingwei.mobile.testrun.TestRunMobileNPhone;

public class POC {
	
	public static void main(String[] args) throws Exception{
		
		String csvFile = "card_tb.csv";
		String rootPath = "/media/boedriver/picset1000_2";
		String configFilePath = "ocr_data/";
		double matchRate = 1.0;
		int fields = ICardFields.NAME;
		int amount = 1200;
		int startPos = 0;

		//int fields = ICardFields.NAME | ICardFields.MOBILE; // can add more | to match more fields

		//TestRunFax tr = new TestRunFax(csvFile, rootPath, configFilePath, amount, 70000);
		TestRunMobileNPhone tr= new TestRunMobileNPhone(csvFile, rootPath, configFilePath, amount, startPos, 1.0);
//		TestRunTitle tr = new TestRunTitle(csvFile, rootPath, configFilePath, amount, startPos, matchRate);
//		TestRunCompany tr = new TestRunCompany(csvFile, rootPath, configFilePath, amount, startPos, matchRate);
//		TestRun tr = new TestRun(csvFile, rootPath, configFilePath, fields, amount, matchRate, startPos);
		
		tr.start();

	}

}
