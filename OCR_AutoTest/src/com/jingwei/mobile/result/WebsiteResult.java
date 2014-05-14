package com.jingwei.mobile.result;

public class WebsiteResult extends ResultBase{

	@Override
	public WebsiteResult Add(ResultBase rb) {
		if(rb == null){
			return this;
		}

		if(!(rb instanceof WebsiteResult)){
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
