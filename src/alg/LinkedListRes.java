/**
 * 
 */
package alg;

/**
 * @author zha
 * 链表翻转：
 * 1、链表翻转。给出一个链表和一个数k，比如链表1→2→3→4→5→6，k=2，则翻转后2→1→4→3→6→5，
 * 若k=3,翻转后3→2→1→6→5→4，若k=4，翻转后4→3→2→1→5→6，用程序实现
 */
public class LinkedListRes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] arraya = {1,2,3,4,5,6};
		
		LinkedArray array = new LinkedArray(arraya );
		array.print();

		arraya = reserve(arraya,1);
		array = new LinkedArray(arraya);
		array.print();
	}

	private static int[] reserve(int[] arraya, int i) {
		//首先是翻转1 - i 
//		reserve(arraya,0 , i-1);
		
		//在翻转i - length
//		reserve(arraya, i, arraya.length-1);
		
		//再翻转1 - length
//		reserve(arraya, 0, arraya.length-1);
		
		for (int j = 0; j < arraya.length/i; j++) {
			reserve(arraya,j*i , (j+1)*i-1);
		}

		return arraya;
	}

	private static void reserve(int[] arraya, int from, int to) {
		to = to < arraya.length-1 ? to :  arraya.length-1;  
		for (int i = from ,j = to; i < j ; i++,j--) {
			int temp = arraya[i];
			arraya[i] = arraya[j];
			arraya[j] = temp;
			
		}
		
		
	}

}
