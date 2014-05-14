package com.jingwei.mobile.result;

public class EmailResult extends ResultBase {

	@Override
	public EmailResult Add(ResultBase rb) {
		if(rb == null){
			return this;
		}
		
		
		if(!(rb instanceof EmailResult)){
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
