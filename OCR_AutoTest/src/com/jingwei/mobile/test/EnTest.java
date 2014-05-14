package com.jingwei.mobile.test;

import java.util.HashMap;

import com.jingwei.mobile.card.Card;
import com.jingwei.mobile.card.CardBean;
import com.jingwei.mobile.card.ICardFields;
import com.jingwei.mobile.match.AddressMatcher;
import com.jingwei.mobile.match.CompanyMatcher;
import com.jingwei.mobile.match.EmailMatcher;
import com.jingwei.mobile.match.FaxMatcher;
import com.jingwei.mobile.match.IMMatcher;
import com.jingwei.mobile.match.MatchBase;
import com.jingwei.mobile.match.MobileMatcher;
import com.jingwei.mobile.match.NameEnMatcher;
import com.jingwei.mobile.match.NameMatcher;
import com.jingwei.mobile.match.TelephoneMatcher;
import com.jingwei.mobile.match.TitleMatcher;
import com.jingwei.mobile.match.WebsiteMatcher;

public class EnTest extends Test {

	public EnTest(CardBean cardBean, Card card) {
		super(cardBean, card);
	}

	/**
	 * Init the matchers, set a key value, so that we can use a circle to do all the matching
	 */
	static{
		matchers = new HashMap<Integer, MatchBase>();
		matchers.put(new Integer(ICardFields.NAME), new NameMatcher());
		matchers.put(new Integer(ICardFields.NAME_EN), new NameEnMatcher());
		matchers.put(new Integer(ICardFields.TITLE), new TitleMatcher());
		matchers.put(new Integer(ICardFields.PHONE_COMPANY), new TelephoneMatcher());
		matchers.put(new Integer(ICardFields.PHONE_FAX), new FaxMatcher());
		matchers.put(new Integer(ICardFields.MOBILE), new MobileMatcher());
		matchers.put(new Integer(ICardFields.EMAIL), new EmailMatcher());
		matchers.put(new Integer(ICardFields.COMPANY), new CompanyMatcher());
		matchers.put(new Integer(ICardFields.ADDRESS), new AddressMatcher());
		matchers.put(new Integer(ICardFields.IM), new IMMatcher());
		matchers.put(new Integer(ICardFields.WEBSITE), new WebsiteMatcher());
	}
	
}
