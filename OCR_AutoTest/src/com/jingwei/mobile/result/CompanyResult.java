package com.jingwei.mobile.result;

public class CompanyResult extends ResultBase {

	@Override
	public CompanyResult Add(ResultBase rb) {
		if(rb == null){
			return this;
		}

		if(!(rb instanceof CompanyResult)){
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
