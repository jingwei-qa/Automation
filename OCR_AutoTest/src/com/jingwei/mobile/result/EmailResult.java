package com.jingwei.mobile.result;

public class EmailResult extends ResultBase {

	public int Bingo = 0;
	public int Count = 0;
	
	public int Distance = 0;
	public int Length = 0;
	
	public int filedMismatchCount = 0;
	
	@Override
	public EmailResult Add(ResultBase rb) {
		if(rb.getClass() != this.getClass()){
			return this;
		}
		
		EmailResult result = new EmailResult();
		EmailResult target = (EmailResult)rb;
		
		result.Bingo = this.Bingo + target.Bingo;
		result.Count = this.Count + target.Count;
		
		result.Distance = this.Distance + target.Distance;
		result.Length = this.Length + target.Length;
		
		result.filedMismatchCount = this.filedMismatchCount + target.filedMismatchCount;
		
		return result;
	}

}
