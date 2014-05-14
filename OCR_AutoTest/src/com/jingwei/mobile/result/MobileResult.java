package com.jingwei.mobile.result;

public class MobileResult extends ResultBase {
	
	public int onceBingo = 0;
	public int cardCount = 0;
	
	@Override
	public MobileResult Add(ResultBase rb) {
		if(rb == null){
			return this;
		}

		if(!(rb instanceof MobileResult)){
			return this;
		}
		
		this.Bingo += rb.Bingo;
		this.Count += rb.Count;
		
		this.Distance += rb.Distance;
		this.Length += rb.Length;
		
		this.filedMismatchCount += rb.filedMismatchCount;
		
		this.onceBingo += ((MobileResult)rb).onceBingo;
		this.cardCount += ((MobileResult)rb).cardCount;
		
		return this;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append(System.lineSeparator());
		sb.append(String.format("[%s]", this.getClass()));
		sb.append(System.lineSeparator());
		sb.append(String.format("[Bingo: %d] / [Count: %d] -- %f", Bingo, Count, new Double(Bingo) / Count));
		sb.append(System.lineSeparator());
		sb.append(String.format("[Distance: %d] / [Length: %d]", Distance, Length));
		sb.append(System.lineSeparator());
		sb.append(String.format("[BingoOnce: %d] / [CardCount: %d]", onceBingo, cardCount));
		sb.append(System.lineSeparator());
		sb.append(String.format("[FiledMismatchCount: %d]", filedMismatchCount));
		sb.append(System.lineSeparator());
		
		return sb.toString();
	}

}
