package com.jingwei.mobile.result;

public class AddressResult extends ResultBase {
	
	@Override
	public AddressResult Add(ResultBase rb) {
		if(rb == null){
			return this;
		}
		
		if(!(rb instanceof AddressResult)){
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
