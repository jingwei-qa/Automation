package com.jingwei.mobile.result;

public class FaxResult extends ResultBase {
	
	@Override
	public FaxResult Add(ResultBase rb) {
		if(rb == null){
			return this;
		}
		

		if(!(rb instanceof FaxResult)){
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
