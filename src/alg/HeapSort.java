package alg;

import java.util.Arrays;

/**
 * @author zha
 * 堆排序，时间的复杂度是O（nlogn）
 */
public class HeapSort {

	public static void main(String[] args) {
		int[] array = { 5, 2, 4, 6, 1, 3, 23, 434, 55, 6, 788, 89, 0 };
		heapSort(array, 0 , array.length- 1);
		System.out.println(Arrays.toString(array));

	}

	private static void heapSort(int[] array, int from, int to) {
		// 首先是建立一个堆
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

	//建立一个堆，采用的是递归的方式，每次都感觉比较的奇妙的感觉
	private static void buildHeap(int[] array, int from, int to) {
		int length = to - from +1;
		int mid = from + (length >> 1);
		for (int i = mid ; i >= 0 ;i--) {
			MAXHeap(array,from ,to, i);
		}
	}

	//保证数组i为根的子树成为最大堆
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

	//左子树的下标
	private static int left(int i){
		return 2*(i+1)-1;
	}
	
	//右子树的下标
	private static int right(int i){
		return 2*(i+1);
	}
}
