/**
 * 
 */
package alg;

import java.util.Arrays;

/**
 * @author zha
 * 快速排序
 */
public class QuickSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] array = { 5, 2, 4, 6, 1, 3, 23, 434, 55, 6, 788, 89, 0 };
		quickSort(array, 0 , array.length- 1);
		System.out.println(Arrays.toString(array));

	}

	private static void quickSort(int[] array, int i, int j) {
		// TODO Auto-generated method stub
		
	}

}
