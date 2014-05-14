package com.jingwei.mobile.match;

import java.util.ArrayList;
import java.util.List;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.ICardHeaders;
import com.jingwei.mobile.result.EmailResult;
import com.jingwei.mobile.result.ResultBase;
import com.jingwei.mobile.result.WebsiteResult;
import com.jingwei.mobile.util.Levenshtein;
import com.jingwei.mobile.util.Utility;

public class WebsiteMatcher extends MatchBase {

	public static String[] Prefixes = {"http://", "https://"};
	
	@Override
	public WebsiteResult Match(CardBean cardBean, Card card) {

		WebsiteResult result = new WebsiteResult();
		
		String website = cardBean.getWebsite();

		if(website == null || website == ""){
			return result;
		}
		
		List<String> websites = new ArrayList<String>();
		for(String em : website.split(WEBSITESPLITOR)){
			websites.add(em);
		}
		
		result.Count = websites.size();
		
		/**
		 * Start matching, recursively compare all the fields in ocr result.
		 * Find if there is any wrong field mapping 
		 */
		
		String expected = website;
		expected = Utility.TrimNConvert(expected);
		
		List<String> processedActualValueList = Utility.TrimNConvert(card.getValuesList());
		String actual = this.getMostLikeStr(expected, processedActualValueList);
		String originActual = actual;
		for(String pre: WebsiteMatcher.Prefixes){
			if(expected.startsWith(pre)){
				expected = expected.substring(pre.length());
			}
			
			if(actual.startsWith(pre)){
				actual = actual.substring(pre.length());
			}
		}
		
		int distance = Levenshtein.Compare(expected, actual);
		if(distance == expected.length()){
			// means not found similar field in ocr result
			// failed one, ignore this match
		}else{
			// ELSE means found a similar one in ocr result
			// To see if the attribute is right:
			int indexOfActual = processedActualValueList.indexOf(originActual);
			int attribOfActual = card.getAttribList().get(indexOfActual);
			if(attribOfActual != ICardHeaders.NAMECARD_WEBURL){
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
