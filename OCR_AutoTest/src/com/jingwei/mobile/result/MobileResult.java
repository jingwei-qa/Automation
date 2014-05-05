package com.jingwei.mobile.result;

public class MobileResult extends ResultBase {
	
	public int mobileBingo = 0;
	public int mobileCount = 0;
	
	public int mobileDistance = 0;
	public int mobileLength = 0;
	
	public int filedMismatchCount = 0;

	@Override
	public MobileResult Add(ResultBase rb) {

		if(rb.getClass() != this.getClass()){
			return this;
		}
		
		MobileResult result = new MobileResult();
		MobileResult target = (MobileResult)rb;
		
		result.mobileBingo = this.mobileBingo + target.mobileBingo;
		result.mobileCount = this.mobileCount + target.mobileCount;
		
		result.mobileDistance = this.mobileDistance + target.mobileDistance;
		result.mobileLength = this.mobileLength + target.mobileLength;
		
		result.filedMismatchCount = this.filedMismatchCount + target.filedMismatchCount;
		
		return result;
	}

}
