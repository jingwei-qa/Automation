package com.jingwei.mobile.result;

public class TelephoneResult extends ResultBase {

	public int Bingo = 0;
	public int Count = 0;
	
	public int Distance = 0;
	public int Length = 0;
	
	public int filedMismatchCount = 0;

	@Override
	public TelephoneResult Add(ResultBase rb) {
		
		if(rb.getClass() != this.getClass()){
			return this;
		}
		
		TelephoneResult result = new TelephoneResult();
		TelephoneResult target = (TelephoneResult)rb;
		
		result.Bingo = this.Bingo + target.Bingo;
		result.Count = this.Count + target.Count;
		
		result.Distance = this.Distance + target.Distance;
		result.Length = this.Length + target.Length;
		
		result.filedMismatchCount = this.filedMismatchCount + target.filedMismatchCount;
		
		return result;
	}

}
