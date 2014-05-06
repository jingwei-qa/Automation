package com.jingwei.mobile.match;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.ICardHeaders;
import com.jingwei.mobile.result.FaxResult;
import com.jingwei.mobile.result.MobileResult;
import com.jingwei.mobile.util.Levenshtein;
import com.jingwei.mobile.util.Utility;

public class FaxMatcher extends MatchBase {

	@Override
	public FaxResult Match(CardBean cardBean, Card card) {

		FaxResult result = new FaxResult();
		
		String faxes = cardBean.getPhone_fax();

		if(faxes == null || faxes == ""){
			return result;
		}
		
		JSONArray ja = null; 
		
		try{
			ja = JSONArray.fromObject(faxes);
		}catch(JSONException e){
			// TODO: log sth.
		}
		
		ArrayList<String> faxList = new ArrayList<String>();
		
		for(Object jo : ja){
			if(jo.getClass() == JSONObject.class){
				String expected = ((JSONObject)jo).getString("v");
				expected = Utility.TrimNConvert(expected);
				faxList.add(expected);
			}
		}
		
		/**
		 * Start matching, recursively compare all the fields in ocr result.
		 * Find if there is any wrong field mapping 
		 */
		result.Count = faxList.size();
		
		for(int i = 0; i < faxList.size(); i++){
			String expected = faxList.get(i);
			
			String actual = this.getMostLikeStr(expected, card.getValuesList());
			int indexOfActual = card.getValuesList().indexOf(actual);
			int attribOfActual = card.getAttribList().get(indexOfActual);
			
			if(attribOfActual != ICardHeaders.NAMECARD_FAX){
				result.filedMismatchCount++;
			}
			
			/**
			 * Trim the +86 for the mobile numbers, then, do matching
			 */
			if(expected.startsWith("+86") && !actual.startsWith("+86")){
				expected = expected.substring(3);
			}
			
			int distance = Levenshtein.Compare(expected, actual);
			result.Distance += distance;
			result.Length += expected.length();
			
			if(distance == 0){
				result.Bingo++;
			}
		}
		
		return result;
	}
}
