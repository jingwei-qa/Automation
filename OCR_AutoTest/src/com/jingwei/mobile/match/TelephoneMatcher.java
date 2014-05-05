package com.jingwei.mobile.match;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.ICardHeaders;
import com.jingwei.mobile.log.Log;
import com.jingwei.mobile.result.MobileResult;
import com.jingwei.mobile.result.ResultBase;
import com.jingwei.mobile.result.TelephoneResult;
import com.jingwei.mobile.util.Levenshtein;
import com.jingwei.mobile.util.Utility;

public class TelephoneMatcher extends MatchBase {

	@Override
	public ResultBase Match(CardBean cardBean, Card card) {
		
		TelephoneResult result = new TelephoneResult();
		
		String phones = cardBean.getPhone_company();
		String mobiles = cardBean.getMobile();
		if( (mobiles == null || mobiles == "") && (phones == null || phones == "") ){
			return result;
		}
		
		JSONArray ja = null; 
		
		try{
			ja = JSONArray.fromObject(phones);
		}catch(JSONException e){
			// TODO: log sth.
		}
		
		ArrayList<String> phoneList = new ArrayList<String>();
		for(Object jo : ja){
			if(jo.getClass() == JSONObject.class){
				String expected = ((JSONObject)jo).getString("v");
				
				/**
				 * For phone field, only count the phone number not start with +861
				 */
				if(!expected.startsWith("+861") && expected.length() > 0){
					phoneList.add(expected);
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
				if(!expected.startsWith("+861") && expected.length() > 0){
					phoneList.add(expected);
				}
			}
		}
		
		result.Count = phoneList.size();
		
		for(int i = 0; i < phoneList.size(); i++){
			String expected = phoneList.get(i);
			expected = Utility.TrimNConvert(expected);
			
			String actual = this.getMostLikeStr(expected, card.getValuesList());
			
			/**
			 * Get the attrib value in real result, 
			 * To indicate if the value had been specified wrong attribute
			 */
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
				result.Bingo++;
			}
		}
		
		return result;
	}

}
