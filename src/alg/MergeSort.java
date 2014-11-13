package alg;

import java.util.Arrays;

/**
 * @author zha
 * 归并算法，重在思想
 */
public class MergeSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] array = { 5, 2, 4, 6, 1, 3, 23, 434, 55, 6, 788, 89, 0 };
		mergeSort(array, 0, array.length-1);
		System.out.println(Arrays.toString(array));

	}

	private static void mergeSort(int[] array, int start, int end ) {

		if(start < end){
			int mid = (start + end)>> 1;
			mergeSort(array, start, mid);
			mergeSort(array, mid+1, end);
			merge(array, start, mid,end);
		}
		
		
		
		
	}

	/**
	 * 合并排序中合并的两端都是有序的,哨兵牌的使用
	 * */
	private static void merge(int[] array, int start, int mid, int end) {
		// 创建两个变量，用来存储中间的变量
		int firstl = mid - start+1;
		int secondl = end -mid;
		int[] first = new int[firstl +1];
		int[] second = new int[secondl+1];
		
		for (int i = 0; i < firstl; i++) {
			first[i] = array[start+i];
		}
		
		for (int i = 0; i < secondl; i++) {
			second[i] = array[mid +i +1];
		}
		
		first[firstl] = Integer.MAX_VALUE;
		second[secondl] = Integer.MAX_VALUE;
		
		int ii = 0 , jj =0;
		for (int pp = start; pp <= end; pp++) {
			if(first[ii] <= second[jj]){
				array[pp] = first[ii];
				ii++;
			}else{
				array[pp] = second[jj];
				jj++;
			}
		}
		
		
	}

}
