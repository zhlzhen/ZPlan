package alg;

import java.util.Arrays;

/**
 * @author zha 插入排序，基础的算法 算法复杂度为O(n2)
 */
public class InsertSort {

	public static void main(String[] args) {
		int[] array = { 5, 2, 4, 6, 1, 3, 23, 434, 55, 6, 788, 89, 0 };
		insertSort(array, 0, array.length);
		System.out.println(Arrays.toString(array));
	}

	public static   <T extends Comparable<T>> T[] insertSort(T[] array, int begine, int length) {
		// 插入排序要就插入前面的序列式有序的
		for (int i = begine + 1; i < length; i++) {
			 T key = array[i];
			int j = i - 1;
			for (; j >= begine; j--) {
				if (array[j].compareTo(key) > 0){
					array[j + 1] = array[j];
				} else {
					// 得到插入点的时候直接的跳出
					break;
				}
			}
			array[j + 1] = key;
		}

		return array;
	}

	public static int[] insertSort(int[] array, int begine, int length) {

		// 插入排序要就插入前面的序列式有序的
		for (int i = begine + 1; i < length; i++) {
			int key = array[i];
			int j = i - 1;
			for (; j >= begine; j--) {
				if (array[j] > key) {
					array[j + 1] = array[j];
				} else {
					// 得到插入点的时候直接的跳出
					break;
				}
			}
			array[j + 1] = key;
		}

		return array;
	}

}
