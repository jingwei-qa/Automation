package com.jingwei.mobile.match;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.ICardHeaders;
import com.jingwei.mobile.result.NameEnResult;
import com.jingwei.mobile.result.NameResult;
import com.jingwei.mobile.result.ResultBase;
import com.jingwei.mobile.util.Levenshtein;
import com.jingwei.mobile.util.Utility;

public class NameEnMatcher extends MatchBase {

	@Override
	public ResultBase Match(CardBean cardBean, Card card) {

		NameEnResult result = new NameEnResult();
		
		String name = cardBean.getName_en();

		if(name == null || name == ""){
			return result;
		}
		
		result.Count = 1;
		
		/**
		 * Start matching, recursively compare all the fields in ocr result.
		 * Find if there is any wrong field mapping 
		 */
		
		String expected = name;
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
			if(attribOfActual != ICardHeaders.NAMECARD_NAME_EN){
				result.filedMismatchCount++;
			}
			
			// to see if bingo
			result.Distance += distance;
			result.Length += expected.length();
			
			int len = expected.length();
			if( (len - distance) / len >= NAMEENMATCHRATE){
				result.Bingo++;
			}
		}
		
		return result;
	}
}
