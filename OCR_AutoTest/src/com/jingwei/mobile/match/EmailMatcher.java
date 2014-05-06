package com.jingwei.mobile.match;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.ICardHeaders;
import com.jingwei.mobile.result.CompanyResult;
import com.jingwei.mobile.result.EmailResult;
import com.jingwei.mobile.result.ResultBase;
import com.jingwei.mobile.util.Levenshtein;
import com.jingwei.mobile.util.Utility;

public class EmailMatcher extends MatchBase {

	@Override
	public EmailResult Match(CardBean cardBean, Card card) {

		EmailResult result = new EmailResult();
		
		String email = cardBean.getEmail();

		if(email == null || email == ""){
			return result;
		}
		
		result.Count = 1;
		
		/**
		 * Start matching, recursively compare all the fields in ocr result.
		 * Find if there is any wrong field mapping 
		 */
		
		String expected = email;
		expected = Utility.TrimNConvert(expected);
		
		String actual = this.getMostLikeStr(expected, card.getValuesList());
		
		int distance = Levenshtein.Compare(expected, actual);
		if(distance == expected.length()){
			// means not found similar field in ocr result
			// failed one, ignore this match
		}else{
			// ELSE means found a similar one in ocr result
			// To see if the attribute is right:
			int indexOfActual = card.getValuesList().indexOf(actual);
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
		
		return result;
	}

}
