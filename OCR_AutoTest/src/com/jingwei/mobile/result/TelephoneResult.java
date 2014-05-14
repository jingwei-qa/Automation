package com.jingwei.mobile.result;

public class TelephoneResult extends ResultBase {

	@Override
	public TelephoneResult Add(ResultBase rb) {
		if(rb == null){
			return this;
		}
		

		if(!(rb instanceof TelephoneResult)){
			return this;
		}
		
		this.Bingo += rb.Bingo;
		this.Count += rb.Count;
		
		this.Distance += rb.Distance;
		this.Length += rb.Length;
		
		this.filedMismatchCount += rb.filedMismatchCount;
		
		
		return this;
	}

}
