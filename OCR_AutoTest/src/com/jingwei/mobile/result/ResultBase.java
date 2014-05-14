package com.jingwei.mobile.result;

public abstract class ResultBase {
	
	boolean isValidRecord = false;;
	
	public int Bingo = 0;
	public int Count = 0;
	
	public int Distance = 0;
	public int Length = 0;
	
	public int filedMismatchCount = 0;
	
	public abstract ResultBase Add(ResultBase rb);
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append(System.lineSeparator());
		sb.append(String.format("[%s]", this.getClass()));
		sb.append(System.lineSeparator());
		sb.append(String.format("[Bingo: %d] / [Count: %d] -- %f", Bingo, Count, new Double(Bingo) / Count));
		
		sb.append(System.lineSeparator());
		sb.append(String.format("[Distance: %d] / [Length: %d]", Distance, Length));
		sb.append(System.lineSeparator());
		sb.append(String.format("[FiledMismatchCount: %d]", filedMismatchCount));
		sb.append(System.lineSeparator());
		
		return sb.toString();
	}

}
