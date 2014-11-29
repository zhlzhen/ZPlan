package alg;
/**
 * 
 * 杨氏矩阵查找，即矩阵中的任意一个元素其右边和下边的元素都大于它，其左边和下边的元素都小于它
 *
 */
public class MatriFind {
    /**
     * @param a     要查找的矩阵
     * @param width 矩阵的宽
     * @param height 矩阵的高
     * @param num  要查找的元素
     */
    public void find(int a[][],int width,int height,int num){
        //使指针指向第一行的最后一个元素
        int xPos = width - 1;
        int yPos = 0;
        while(xPos >= 0 && yPos <= height - 1){
        	//相等则直接的返回
            if(a[xPos][yPos] == num){
                System.out.println("xPos:"+xPos+" yPos:"+yPos);
                return;
            }else if(a[xPos][yPos] > num){
            	//大于所要寻找的目标向上需找
                xPos--;
            }else{
            	//小于要寻找的目标向右寻找
                yPos++;
            }
        }
        System.out.println("没有找到！");
    }
     
    public static void main(String[] args) {
        MatriFind mf = new MatriFind();
        int matrix[][] = {
                {1,2,8,9},
                {2,4,9,12},
                {4,7,10,13},
                {6,8,11,15}
        };
        mf.find(matrix,matrix[0].length, matrix.length, 15);
    }
}