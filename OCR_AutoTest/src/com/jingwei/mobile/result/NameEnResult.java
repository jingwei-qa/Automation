package com.jingwei.mobile.result;

public class NameEnResult extends ResultBase {

	@Override
	public NameEnResult Add(ResultBase rb) {
		if(rb == null){
			return this;
		}

		if(!(rb instanceof NameEnResult)){
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
