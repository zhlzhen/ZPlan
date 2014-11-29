package regularExpression.current;

public class Snippet {
	
	public static void main(String[] args){
		if("testaa".trim() == "testaa".trim()){
			System.out.println("que ding wei yi");
		}
		
		String s1 = "abc"+"def";
		String s2 = new String(s1);
		if(s1 == s2) {
			System.out.println("d");
	}else if(s1.equals(s2)) {
			System.out.println("ddd");
		}else {
			System.out.println("hh");
	}

	}
}

