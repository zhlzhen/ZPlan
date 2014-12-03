package alg;

import java.util.Arrays;

/**
 * @author zha
 * 字符串包含
 */
public class Alg2StringContain {

	public static void main(String[] args) {
		String tt = "abcdefg";
		String t = "cfh";
		System.out.println(StringContain(tt, t));
		System.out.println(isContain(tt,t));
		System.out.println(isContainByHashCode(tt,t));
		
		/**
		 * 如果两个字符串的字符一样，但是顺序不一样，被认为是兄弟字符串，
		 * 比如bad和adb即为兄弟字符串，现提供一个字符串，如何在字典中迅速找到它的兄弟字符串，请描述数据结构和查询过程。
		 * */
		//结合上面的思想，在字典中有一类的数据叫做字母相同但是顺序不同的词，可以先排序然后在进行比较确认，考虑到了重复字符的问题
		//记录hashtable的办法也是可以的，不过需要记录每个字符出现的次数，比较同一个字符出现的次数来判断是否是兄弟字符串

	}

	/**
	 * 如果我们使用hashtable把出现的字符保存起来，然后遍历短的字符串就能够确定每一个字符是否包含
	 * 更近一步，我们可以使用bit为来标记字符的出现
	 * */
	private static boolean isContainByHashCode(String tt, String t) {
		int hash = 0;
	    for (int i = 0; i < tt.length(); ++i)
	    {
	        hash |= (1 << (tt.toCharArray()[i] - 'A'));
	    }
	    for (int i = 0; i < t.length(); ++i)
	    {
	        if ((hash & (1 << (t.toCharArray()[i] - 'A'))) == 0)
	        {
	            return false;
	        }
	    }
	    return true;
	}

	//排序比较
	private static boolean isContain(String tt, String t) {
		char[] ttchar = tt.toCharArray();
		Arrays.sort(ttchar);
		char[] tchar = t.toCharArray();
		Arrays.sort(tchar);
		for (int pa = 0, pb = 0; pb < tchar.length;)
	    {
	        while ((pa < ttchar.length) && (ttchar[pa] < tchar[pb]))
	        {
	            ++pa;
	        }
	        if ((pa >= ttchar.length) || (ttchar[pa] > ttchar[pb]))
	        {
	            return false;
	        }
	        //相等的条件下
	        ++pb;
	    }
		return true;
	}

	//暴力比较
	private static boolean StringContain(String tt,String t)
	{
	    for (int i = 0; i < t.length(); ++i) {
	        int j;
	        for (j = 0; (j < tt.length()) && (tt.charAt(j) != t.charAt(i)); ++j)
	            ;
	        if (j >= tt.length())
	        {
	            return false;
	        }
	    }
	    return true;
	}
}
