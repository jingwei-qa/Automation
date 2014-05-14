package com.jingwei.mobile.result;

public class NameResult extends ResultBase {

		
	@Override
	public NameResult Add(ResultBase rb) {
		if(rb == null){
			return this;
		}

		if(!(rb instanceof NameResult)){
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
