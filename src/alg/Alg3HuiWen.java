package alg;

/**
 * @author zha
 * 回文的判断
 */
public class Alg3HuiWen {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String value = "abssba";
		System.out.println(ishuiwen(value));

	}

	private static boolean ishuiwen(String value) {
		if(value != null && value.length() > 1){
			for (int i = 0; i < value.length()/2; i++) {
				if(value.charAt(i) != value.charAt(value.length()-i-1)){
					return false;
				}
			}
			
			return true;
		}
		return false;
	}

}
