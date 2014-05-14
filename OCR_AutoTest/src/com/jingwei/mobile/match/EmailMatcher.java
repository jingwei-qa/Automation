package com.jingwei.mobile.match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.ICardHeaders;
import com.jingwei.mobile.result.CompanyResult;
import com.jingwei.mobile.result.EmailResult;
import com.jingwei.mobile.result.ResultBase;
import com.jingwei.mobile.util.Levenshtein;
import com.jingwei.mobile.util.Utility;
import com.jingwei.mobile.log.Log;

public class EmailMatcher extends MatchBase {
	
//	static{
//		emailMatchLog = Log.GetInstance("email.log");
//	}
	
//	public static Log emailMatchLog;

	@Override
	public EmailResult Match(CardBean cardBean, Card card) throws IOException{

		
		EmailResult result = new EmailResult();
		
		String email = cardBean.getEmail();

		if(email == null || email == ""){
			return result;
		}
		
		List<String> emails = new ArrayList<String>();
		for(String em : email.split(EMAILSPLITOR)){
			String tmp = Utility.TrimNConvert(em);
			if(tmp.length() > 0){
				emails.add(em);
//				emailMatchLog.Write("Exp: " + em);
			}
		}
		
		result.Count = emails.size();
		
		/**
		 * Start matching, recursively compare all the fields in ocr result.
		 * Find if there is any wrong field mapping 
		 */
		for(String expected : emails){
			expected = Utility.TrimNConvert(expected);
			
			List<String> processedActualValueList = Utility.TrimNConvert(card.getValuesList());
			String actual = this.getMostLikeStr(expected, processedActualValueList);
			
//			emailMatchLog.Write("Comparing: " + expected + " " + actual);
			int distance = Levenshtein.Compare(expected, actual);
			if(distance == expected.length()){
				// means not found similar field in ocr result
				// failed one, ignore this match
			}else{
				// ELSE means found a similar one in ocr result
				// To see if the attribute is right:
				int indexOfActual = processedActualValueList.indexOf(actual);
				int attribOfActual = card.getAttribList().get(indexOfActual);
				if(attribOfActual != ICardHeaders.NAMECARD_EMAIL){
					result.filedMismatchCount++;
				}
				
				// to see if bingo
				result.Distance += distance;
				result.Length += expected.length();
				
				int len = expected.length();
				if( (len - distance) / len >= EMAILMATCHRATE){
					result.Bingo++;
				}
			}
			
//			emailMatchLog.Write(result.toString());
		}
		return result;
	}

}
