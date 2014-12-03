package alg;

/**
 * 求取最大的子数列
 * */

public class SubMaxArray {

	public static void main(String[] args) {
		int[] a = new int[] { 1, -2, 3, 10, -4, 7, 2, -5 };
		int[] b = new int[] { -6, 2, 4, -7, 5, 3, 2, -1, 6, -9, 10, -2 };
		int max = maxSum(a, a.length);
		System.out.println(max);

		int maxb = maxSum(b, b.length);
		System.out.println(maxb);
		
		long wmax = maxSubSum4(a);
		System.out.println(wmax);

		long malxb = maxSubSum4(b);
		System.out.println(malxb);
	}

	// 暴力的循环
	static int maxSum(int[] A, int n) {
		int maximum = Integer.MIN_VALUE;
		int sum = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				for (int k = i; k <= j; k++) {
					sum += A[k];
				}
				if (sum > maximum) {
					maximum = sum;
				}
				sum = 0; // 这里要记得清零，否则的话sum最终存放的是所有子数组的和。
			}
		}
		return maximum;
	}

	// 省略重复的计算
	static int maxSumModify(int[] a) {
		int maxSum = 0;
		for (int i = 0; i < a.length; i++) {
			int thisSum = 0;
			for (int j = i; j < a.length; j++) {
				thisSum += a[j];
				if (thisSum > maxSum)
					maxSum = thisSum;
			}
		}
		return maxSum;
	}

	/**
	 * 分治算法 最大的子数列，在前半部分，在后半部分 或者从中间进行计算
	 * */
	static long maxSumRec(int[] a, int left, int right) {
		if (left == right) {
			if (a[left] > 0)
				return a[left];
			else
				return 0;
		}
		int center = (left + right) / 2;
		long maxLeftSum = maxSumRec(a, left, center);
		long maxRightSum = maxSumRec(a, center + 1, right);

		// 求出以左边对后一个数字结尾的序列最大值
		long maxLeftBorderSum = 0, leftBorderSum = 0;
		for (int i = center; i >= left; i--) {
			leftBorderSum += a[i];
			if (leftBorderSum > maxLeftBorderSum)
				maxLeftBorderSum = leftBorderSum;
		}

		// 求出以右边对后一个数字结尾的序列最大值
		long maxRightBorderSum = 0, rightBorderSum = 0;
		for (int j = center + 1; j <= right; j++) {
			rightBorderSum += a[j];
			if (rightBorderSum > maxRightBorderSum)
				maxRightBorderSum = rightBorderSum;
		}

		return max3(maxLeftSum, maxRightSum, maxLeftBorderSum
				+ maxRightBorderSum);
	}

	static long maxSubSum3(int[] a) {
		return maxSumRec(a, 0, a.length - 1);
	}

	static long max3(long a, long b, long c) {
		if (a < b) {
			a = b;
		}
		if (a > c)
			return a;
		else
			return c;
	}

	// 线性的算法O(N)
	/**
	 * 算法的基础：如果a[i] 为负数，那么他不可能是最大子序列的开始，推论是最大子数列的开头的数组的和不可能是一个负数，
	 * 也就是说为负数的子序列不可能是最大子序列的开始。
	 * */
	static long maxSubSum4(int[] a) {
		long maxSum = 0, thisSum = 0;
		for (int j = 0; j < a.length; j++) {
			thisSum += a[j];
			if (thisSum > maxSum)
				maxSum = thisSum;
			else if (thisSum < 0)
				thisSum = 0;
		}
		return maxSum;
	}

	/**
	 * 如果全部为负数的情况下，最大的子序列是为一个元素都没有的零，
	 * 还是最大的负数？
	 * */
	static int maxsum(int[] a)
	{
		int max = a[0]; // 全负情况，返回最大数
		int sum = 0;
		for (int j = 0; j < a.length; j++) {
			if (sum >= 0){ // 如果加上某个元素，sum>=0的话，就加
				sum += a[j];
			}else{
				sum = a[j]; // 如果加上某个元素，sum<0了，就不加
			}
			if (sum > max)
				max = sum;
		}
		return max;
	}
}
