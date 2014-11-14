package alg;

import java.util.Arrays;

/**
 * @author zha �������򣬻������㷨 �㷨���Ӷ�ΪO(n2)
 */
public class InsertSort {

	public static void main(String[] args) {
		int[] array = { 5, 2, 4, 6, 1, 3, 23, 434, 55, 6, 788, 89, 0 };
		insertSort(array, 0, array.length);
		System.out.println(Arrays.toString(array));
	}

	public static   <T extends Comparable<T>> T[] insertSort(T[] array, int begine, int length) {
		// ��������Ҫ�Ͳ���ǰ�������ʽ�����
		for (int i = begine + 1; i < length; i++) {
			 T key = array[i];
			int j = i - 1;
			for (; j >= begine; j--) {
				if (array[j].compareTo(key) > 0){
					array[j + 1] = array[j];
				} else {
					// �õ�������ʱ��ֱ�ӵ�����
					break;
				}
			}
			array[j + 1] = key;
		}

		return array;
	}

	public static int[] insertSort(int[] array, int begine, int length) {

		// ��������Ҫ�Ͳ���ǰ�������ʽ�����
		for (int i = begine + 1; i < length; i++) {
			int key = array[i];
			int j = i - 1;
			for (; j >= begine; j--) {
				if (array[j] > key) {
					array[j + 1] = array[j];
				} else {
					// �õ�������ʱ��ֱ�ӵ�����
					break;
				}
			}
			array[j + 1] = key;
		}

		return array;
	}

}
