package com.jingwei.mobile.result;

public class TitleResult extends ResultBase {

	@Override
	public TitleResult Add(ResultBase rb) {
		if(rb == null){
			return this;
		}

		if(!(rb instanceof TitleResult)){
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
