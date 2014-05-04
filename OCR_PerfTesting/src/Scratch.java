
import net.sf.json.*;

public class Scratch {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("+8613211112222".substring(3));

		for(String s : "高级工程师@@@出口部经理sdf@@@dfsdfs@".split("@@@")){
			System.out.println(s);
		}
		Integer i = 0;
		System.out.println(i);
		int r = abc(i);
		System.out.println(i);
		System.out.println(r);
		
		JSONArray arr = JSONArray.fromObject("[{\"a\":\"abc\"}]");
		for(Object o : arr){
			
		}
	}
	
	public static int abc(Integer i){
		i++;
		i = i + 1;
		return i + 2;
	}

}
