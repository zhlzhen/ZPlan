package alg;

/**
 * @author zha
 *  字符串反转
 */
public class Alg1StringReserv {
	
	public static void main(String[] args) {
		String test = "ilovebaofeng";
		int index = 7 ;
		String re = reserve(test,index);
		System.out.println(re);
		
		String test2 = "I am a student.";
		char index2 = ' ';
		String re2 = reserve(test2,index2);
		System.out.println(re2);

	}
	
	//根据某一个特定的字符进行翻转
	private static String reserve(String test, char index) {
		
		char[] chars = test.toCharArray();
		int from = 0,to = 0;
		for (int i = 0; i < chars.length; i++) {
			char temp = chars[i];
			if(temp == index){
				to = i-1;
				turnOver(chars,from,to);
				from = i+1;
			}
		}
		
		if(to < chars.length - 1){
			turnOver(chars, from, chars.length-1);
		}
		
		turnOver(chars,0,chars.length-1);
		return new String(chars);
	}

	private static String reserve(String test, int index) {
		int length = test.length();
		int begine = (length - index - 1)%length;
		char[] chars = test.toCharArray();
		turnOver(chars,0,begine);
		turnOver(chars,begine+1,length-1);
		turnOver(chars,0,length-1);
		return new String(chars);
	}

	private static void turnOver(char[] chars, int begine, int to) {
		if(begine < to){
			for (; begine < to; begine++,to--) {
				swap(chars,begine,to);
			}
		}
		
	}

	private static void swap(char[] chars, int begine, int to) {
		char temp = chars[begine];
		chars[begine] = chars[to];
		chars[to] = temp;
		
	}

}
