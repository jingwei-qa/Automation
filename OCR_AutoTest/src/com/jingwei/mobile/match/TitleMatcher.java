package com.jingwei.mobile.match;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.ICardHeaders;
import com.jingwei.mobile.result.MobileResult;
import com.jingwei.mobile.result.ResultBase;
import com.jingwei.mobile.result.TitleResult;
import com.jingwei.mobile.util.Levenshtein;
import com.jingwei.mobile.util.Utility;

public class TitleMatcher extends MatchBase {
	
	/**
	 * To verify the title field in ocr result
	 * TODO: if the title contains '@@@', means there are more than one title
	 * In this case, this test login is not compatible
	 */
	@Override
	public TitleResult Match(CardBean cardBean, Card card) {
		TitleResult result = new TitleResult();
		
		String titles = cardBean.getTitle();

		if(titles == null || titles == ""){
			return result;
		}
		
		ArrayList<String> titleList = new ArrayList<String>();
		
		/**
		 * TODO: if there are more than 1 title, 
		 * this logic could not verify the situation when 
		 * the ocr recognize all the titles in one string.
		 */
//		if(titles.contains("@@@")){
//			return result;
//		}
		
		for(String s : titles.split("@@@")){
			if(s != null && s != "" && s.toLowerCase() != "null"){
				titleList.add(s);
			}
		}
		
		result.Count =titleList.size();
		
		/**
		 * Start matching, recursively compare all the fields in ocr result.
		 * Find if there is any wrong field mapping 
		 */
		
		for(int i = 0; i < titleList.size(); i++){
			String expected = titleList.get(i);
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
				if(attribOfActual != ICardHeaders.NAMECARD_TITLE){
					result.filedMismatchCount++;
				}
				
				// to see if bingo
				result.Distance += distance;
				result.Length += expected.length();
				
				int len = expected.length();
				if( (len - distance) / len >= TITLEMATCHRATE){
					result.Bingo++;
				}
			}
		}
		
		return result;
	}

}
