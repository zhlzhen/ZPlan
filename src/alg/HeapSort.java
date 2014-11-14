package alg;

import java.util.Arrays;

/**
 * @author zha
 * 算法的复杂度为O(nlogn)
 */
public class HeapSort {

	public static void main(String[] args) {
		int[] array = { 5, 2, 4, 6, 1, 3, 23, 434, 55, 6, 788, 89, 0 };
		heapSort(array, 0 , array.length- 1);
		System.out.println(Arrays.toString(array));

	}

	private static void heapSort(int[] array, int from, int to) {
		buildHeap(array,from,to);
		int length = to - from +1;
		swap(array,from,to);
		while(length > 1){
			for (int i = to - 1; i >= from; i--) {
				buildHeap(array, from, i);
				swap(array, i, from);
			}
			length--;
		}
		
	}

	private static void buildHeap(int[] array, int from, int to) {
		int length = to - from +1;
		int mid = from + (length >> 1);
		for (int i = mid ; i >= 0 ;i--) {
			MAXHeap(array,from ,to, i);
		}
	}

	private static void MAXHeap(int[] array, int from, int to, int index) {
		int left = left(index);
		int right = right(index);
		int lagest = 0;
		if(left <= to && array[left] > array[index]){
			lagest = left;
		}else{
			lagest = index;
		}
		
		if(right <= to && array[right] > array[lagest]){
			lagest = right;
		}
		
		if(lagest != index){
			swap(array,index,lagest);
			MAXHeap(array, from,to,lagest);
		}
	}

	private static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		
	}

	private static int left(int i){
		return 2*(i+1)-1;
	}
	
	private static int right(int i){
		return 2*(i+1);
	}
}
