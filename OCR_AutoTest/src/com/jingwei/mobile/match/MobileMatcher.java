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
import com.jingwei.mobile.util.Levenshtein;
import com.jingwei.mobile.util.Utility;

public class MobileMatcher extends MatchBase {

	
	/**
	 * Comparing the mobile field from csv record to OCR result.
	 * --------------------------------------------------------
	 * 
	 * Steps:
	 *   1. Get all the phones from csv mobile field
	 *   2. For every mobile record, get the most like value from ocr result.
	 *   3. Compare the two string and log the result.
	 *   
	 */
	@Override
	public ResultBase Match(CardBean cardBean, Card card) {
		
		MobileResult result = new MobileResult();
		
		String mobiles = cardBean.getMobile();
		String phones = cardBean.getPhone_company(); 

		if( (mobiles == null || mobiles == "") && (phones == null || phones == "") ){
			return result;
		}
		
		JSONArray ja = null; 
		
		try{
			ja = JSONArray.fromObject(phones);
		}catch(JSONException e){
			// TODO: log sth.
		}
		
		ArrayList<String> mobileList = new ArrayList<String>();
		
		for(Object jo : ja){
			if(jo.getClass() == JSONObject.class){
				JSONObject jsonObj = (JSONObject)jo;
				if(jsonObj.containsKey("v")){
					String expected = jsonObj.getString("v");
					
					/**
					 * For phone field, only count the phone number not start with +861
					 */
					if(expected.startsWith("+861") && expected.length() > 0){
						mobileList.add(expected);
					}
				}
			}
		}

		try{
			ja = JSONArray.fromObject(mobiles);
		}catch(JSONException e){
			// TODO: log sth.
		}
		
		for(Object jo : ja){
			if(jo.getClass() == JSONObject.class){
				String expected = ((JSONObject)jo).getString("v");
				
				/**
				 * For phone field, only count the phone number not start with +861
				 */
				if(expected.startsWith("+861") && expected.length() > 0){
					mobileList.add(expected);
				}
			}
		}
		
		/**
		 * Start matching, recursively compare all the fields in ocr result.
		 * Find if there is any wrong field mapping 
		 */
		result.Count = mobileList.size();
//		Utility.PrintArray(mobileList);
		
		for(int i = 0; i < mobileList.size(); i++){
			String expected = mobileList.get(i);
			expected = Utility.TrimNConvert(expected);
			
			String actual = this.getMostLikeStr(expected, card.getValuesList());
//			System.out.println("Seeking : " + actual);
			 
			int indexOfActual = card.getValuesList().indexOf(actual);
			int attribOfActual = card.getAttribList().get(indexOfActual);
			
			if(attribOfActual != ICardHeaders.NAMECARD_CELLPHONE && attribOfActual != ICardHeaders.NAMECARD_TELPHONE){
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
				result.Bingo ++;
			}
		}
		
		result.onceBingo = result.Bingo > 0 ? 1 : 0;
		result.cardCount = 1;
		return result;
	}
}
