package com.jingwei.mobile.match;

import java.util.List;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.ICardHeaders;
import com.jingwei.mobile.result.FaxResult;
import com.jingwei.mobile.result.IMResult;
import com.jingwei.mobile.result.ResultBase;
import com.jingwei.mobile.util.Levenshtein;
import com.jingwei.mobile.util.Utility;

public class IMMatcher extends MatchBase {


	@Override
	public IMResult Match(CardBean cardBean, Card card) {

		IMResult result = new IMResult();
		
		String im = cardBean.getIm();

		if(im == null || im == ""){
			return result;
		}
		
		result.Count = 1;
		
		/**
		 * Start matching, recursively compare all the fields in ocr result.
		 * Find if there is any wrong field mapping 
		 */
		
		String expected = im;
		expected = Utility.TrimNConvert(expected);
		
		List<String> processedActualValueList = Utility.TrimNConvert(card.getValuesList());
		String actual = this.getMostLikeStr(expected, processedActualValueList);
		
		int distance = Levenshtein.Compare(expected, actual);
		if(distance == expected.length()){
			// means not found similar field in ocr result
			// failed one, ignore this match
		}else{
			// ELSE means found a similar one in ocr result
			// To see if the attribute is right:
			int indexOfActual = processedActualValueList.indexOf(actual);
			int attribOfActual = card.getAttribList().get(indexOfActual);
			if(attribOfActual != ICardHeaders.NAMECARD_FAX){
				result.filedMismatchCount++;
			}
			
			// to see if bingo
			result.Distance += distance;
			result.Length += expected.length();
			
			int len = expected.length();
			if( (len - distance) / len >= FAXMATCHRATE){
				result.Bingo++;
			}
		}
		
		return result;
	}

}
