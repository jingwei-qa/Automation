package com.jingwei.mobile.log;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * To log the details for logical code.
 * Any string to log will be append a time stamp before logging.
 * @author nan.lin3@renren-inc.com
 * @DateModifled: Apr 17, 2014
 */
public class Log {
	
	/**
	 * Init the default log file name, with current time
	 */
	static{
		
		Calendar calendar = Calendar.getInstance();
		Log.defaultLog = String.format(
				"jingwei_%d_%d_%d-%d_%d_%d.log", 
				calendar.get(Calendar.YEAR), 
				calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.HOUR),
				calendar.get(Calendar.MINUTE),
				calendar.get(Calendar.SECOND));
	}


	// IGNORE THIS METHOD
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println(Log.defaultLog);
		
		System.out.println(Calendar.getInstance().toString());
		System.out.println(new Date().toString());
		
		Log log = Log.GetInstance("a.log");
		//Log log = Log.GetInstance("/home/administrator/a.log");
		log.Write("abcdefg");

	}
	
	private String logFilePath;
	
	/**
	 * Private constructor to build a new log instance.
	 * @param logFilePath, where to write the log. 
	 * 		if the file exists, will append to the end of the log
	 * 		else, create a new file. by default, will use working directory.
	 */
	private Log(String logFilePath){
		this.logFilePath = logFilePath; 
	}
	
	/**
	 * Write log to the log instance
	 * @param str
	 * @throws IOException
	 */
	public void Write(String str) throws IOException{
		
		FileWriter writer = null;
		
		try{
			writer = new FileWriter(logFilePath, true);
			java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String ss = format1.format(new Date());
			String cont = String.format("%s - %s\n", ss, str);
			writer.write(cont);
			System.out.println(cont);
			
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			writer.close();
		}
		
	}
	
	/**
	 * Make a static method to write log in default log file name,
	 * So it's more easier to write log in a by default file. 
	 * @param str
	 */
	public static void Log(String str){
		Log log = Log.GetInstance();
		try {
			log.Write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Use a private map to store the different logs
	 */
	private static Map<String, Log> logMap;
	
	/**
	 * give a default file 
	 */
	private static String defaultLog;
	
	/**
	 * Return a log instance, which write log to the file given by logFilePath. 
	 * @param logFilePath, the log file path, support absolute file path.
	 * @return a log instance,
	 */
	public static Log GetInstance(String logFilePath){
		if(logMap == null){
			logMap = new HashMap<String, Log>();
		}
		
		if(logMap.containsKey(logFilePath)){
			return logMap.get(logFilePath);
		}else{
			Log log = new Log(logFilePath);
			logMap.put(logFilePath, log);
			return logMap.get(logFilePath);
		}
		
		
	}

	/**
	 * Support write to default log in working directory
	 * @return an log instance, which write log to the default log file.
	 */
	public static Log GetInstance(){
		return GetInstance(defaultLog);
	}

}
