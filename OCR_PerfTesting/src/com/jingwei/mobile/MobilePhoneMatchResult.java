package com.jingwei.mobile;

public class MobilePhoneMatchResult {
	public int mobileBingo;
	public int mobileCount;
	
	public int phoneBingo;
	public int phoneCount;
	
	public int mobileDistance;
	public int mobileLength;
	
	public int phoneDistance;
	public int phoneLength;
	
	public int phonePositiveCount;
	public int phonePositiveBingo;
	
	public MobilePhoneMatchResult(){
		this.mobileBingo = 0;
		this.mobileCount = 0;
		this.mobileDistance = 0;
		this.mobileLength = 0;
		
		this.phoneBingo = 0;
		this.phoneCount = 0;
		this.phoneDistance = 0;
		this.phoneLength = 0;
		
		this.phonePositiveBingo = 0;
		this.phonePositiveCount = 0;
	}
	
	public MobilePhoneMatchResult add(MobilePhoneMatchResult m){
		this.mobileBingo += m.mobileBingo;
		this.mobileCount += m.mobileCount;
		
		this.phoneBingo += m.phoneBingo;
		this.phoneCount += m.phoneCount;
		
		this.mobileDistance += m.mobileDistance;
		this.mobileLength += m.mobileLength;
		
		this.phoneDistance += m.phoneDistance;
		this.phoneLength += m.phoneLength;
		
		this.phonePositiveBingo += m.phonePositiveBingo;
		this.phonePositiveCount += m.phonePositiveCount;
		
		return this;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append(System.lineSeparator());
		sb.append("----------- Mobile -----------");
		sb.append(System.lineSeparator());
		
		sb.append(String.format("Bingo / Count:\t[%d] / [%d]", this.mobileBingo, this.mobileCount) );
		sb.append(System.lineSeparator());
		
		sb.append(String.format("Distance / Length:\t[%d] / [%d]", this.mobileDistance, this.mobileLength) );
		sb.append(System.lineSeparator());
		
		sb.append("----------- Phone -----------");
		sb.append(System.lineSeparator());
		
		sb.append(String.format("Bingo / Count:\t[%d] / [%d]", this.phoneBingo, this.phoneCount) );
		sb.append(System.lineSeparator());
		
		sb.append(String.format("Distance / Length:\t[%d] / [%d]", this.phoneDistance, this.phoneLength) );
		sb.append(System.lineSeparator());
		
		sb.append(String.format("Postive: Bingo / Count:\t[%d] / [%d]", this.phonePositiveBingo, this.phonePositiveCount) );
		sb.append(System.lineSeparator());
		
		
		return sb.toString();
	}
}
