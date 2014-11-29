package alg;

/**
 * 通过键盘输入一串小写字母(a~z)组成的字符串。请编写一个字符串压缩程序，将字符串中连续出席的重复字母进行压缩，
 * 并输出压缩后的字符串。
 * 压缩规则：
 *     1、仅压缩连续重复出现的字符。比如字符串"abcbc"由于无连续重复字符，压缩后的字符串还是"abcbc"。
 *     2、压缩字段的格式为"字符重复的次数+字符"。例如：字符串"xxxyyyyyyz"压缩后就成为"3x6yz"。
 * */
public class ZipString {
	
	public static void main(String[] args){
		String test = "aaa";
		String zipString = zipString(test);
		System.out.println(zipString);
		System.out.println(zipString("cccddecc"));
		System.out.println(zipString("adef"));
		System.out.println(zipString("pppppppp"));
	}

	//压缩字符串
	private static String zipString(String test) {
		StringBuilder builder = new StringBuilder();
		if(test != null && test.length() > 0){
			char[] chars = test.toCharArray();
			char pre = chars[0];
			int number = 1;
			for (int i = 1; i < chars.length; i++) {
				char temp = chars[i];
				//如果相等则递增
				if(temp == pre){
					number ++;
				}else{
					//如果不相等，首先处理前面已经结束的逻辑
					if(number > 1){
						builder.append(number).append(pre);
					}else{
						builder.append(pre);
					}
					
					number = 1;
					pre = temp;
				}
			}
			
			//最后的一个字符，在循环结束的时候添加
			if(number > 1){
				builder.append(number).append(pre);
			}else{
				builder.append(pre);
			}
			
		}
		return builder.toString();
	}

}
