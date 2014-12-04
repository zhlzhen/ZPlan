package alg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhailzh
 * 全排列，字符串的全排列
 * 含有重复的全排
 * 字符的所有的组合
 */
public class Alg5StringQuanPaiLie {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String test = "abc";
		printQuanPaiLie(test);
		
		String tt = "abc";
		printQuan(tt,3);
		
		//要求不重复的字符串
		String kk = "abcd";
		perm(kk);
	}
	
	// 求字符串中所有字符的组合abc>a,b,c,ab,ac,bc,abc
		public static void perm(String s) {
			List<String> result = new ArrayList<String>();
//			for (int i = 1; i <= s.length(); i++) {
//				perm(s, i, result);
//			}
			perm(s, 2, result);
		}

		// 从字符串s中选择m个字符
		public static void perm(String s, int m, List<String> result) {

			// 如果m==0，则递归结束。输出当前结果
			if (m == 0) {
				for (int i = 0; i < result.size(); i++) {
					System.out.print(result.get(i));
				}
				System.out.println();
				return;
			}

			if (s.length() != 0) {
				//可以说这种的写法，比较的有意思
				// 选择当前元素
				result.add(s.charAt(0) + "");
				perm(s.substring(1, s.length()), m - 1, result);
				result.remove(result.size() - 1);
				// 不选当前元素
				perm(s.substring(1, s.length()), m, result);
			}
		}
	
	
	private static void printQuan(String tt,int limit) {
		char[] chars = tt.toCharArray();
		char[] res = new char[chars.length];
		printQuan(res,chars,0,limit);
		
	}


	//递归调用
	private static void printQuan(char[] res,char[] chars, int repos,int limit) {
		
		if(repos == limit){
			for (int i = 0; i < limit; i++) {
				System.out.print(res[i]);
			}
			System.out.println();
		}else{
			for (int i = 0; i < chars.length; i++) {
				res[repos] = chars[i];
				printQuan(res, chars, repos+1, limit);
			}
		}
		
	}


	private static void printQuanPaiLie(String test) {
		//每次选择一个元素，把剩下的全排列
		char[] chars = test.toCharArray();
		QuanPaiLie(chars,0,chars.length-1);
	}

	/**
	 * 设想：首先是固定前面的，全排后面的，所以会有
	 *  QuanPaiLie1（chars，from ，to）中调用 QuanPaiLie1（chars，from+1，to）
	 *  循环的出口是：当from 和 to 相等（还是差一）的时候，这个时候表示为一个序列了
	 *  应该是输出的是一个序列。
	 *  我们在看循环体：当QuanPaiLie1（chars，from ，to）中调用 QuanPaiLie1（chars，from+1，to）的时候
	 *  我们简化条件，使用三个字符的情况，在QuanPaiLie1（chars，2 ，3）调用到QuanPaiLie1（chars，3 ，3）
	 *  自然能够想到2和3互换，但是这个是在QuanPaiLie1（chars，3 ，3）调用完成以后，然后在想3,3，调用完成以后，
	 *  那么原来的顺序已经发生变化，并且变化的是轮流的变化，也就是说调用QuanPaiLie1（chars，1，3）的时候们就会要求
	 *  1和2换，1和3换，按照这个思路，就应该是循环变量i 和  from 。那么现在只剩下一个症结所在，我i 和from 交换以后怎么能够
	 *  在交换回来，不然的话，就会成无需的交换，办法就是交换两次。
	 *
	 * */
	private static void QuanPaiLie(char[] chars,int from,int to) {
		if(to < 1){
			return;
		}
		if(from == to){
			for (int i = 0; i <= to; i++) {
				System.out.print(chars[i]);
			}
			System.out.println();
		}else{
			for (int i = from; i <= to; i++) {
				if(i != from){
					swap(chars,i,from);
				}
				QuanPaiLie(chars, from+1, to);
				if(i != from){
					swap(chars,i,from);
				}
			}
		}
	}


	private static void swap(char[] chars, int i, int from) {
		char temp = chars[i];
		chars[i] = chars[from];
		chars[from] = temp;
		
	}

}
