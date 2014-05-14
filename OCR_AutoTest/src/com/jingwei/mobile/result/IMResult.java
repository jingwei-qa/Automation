package com.jingwei.mobile.result;

public class IMResult extends ResultBase {
	
	@Override
	public IMResult Add(ResultBase rb) {
		if(rb == null){
			return this;
		}
		
		if(rb.getClass() != this.getClass()){
			return this;
		}

		if(!(rb instanceof IMResult)){
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
