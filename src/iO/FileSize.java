/**
 * 
 */
package iO;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * @author zha
 * �ļ��Ĵ�С��longֵ�ķ�Χ֮��
 */
public class FileSize
{
    public long getFileSizes(File f) throws Exception{//ȡ���ļ���С
        long s=0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s= fis.available();
        } else {
            f.createNewFile();
            System.out.println("�ļ�������");
        }
        return s;
    }
    
    
    /**
     * ȡ���ļ��еĴ�С
     * **/
    public long getFileSize(File fileDir)throws Exception//ȡ���ļ��д�С
    {
        long size = 0;
        File flist[] = fileDir.listFiles();
        for (int i = 0; i < flist.length; i++)
        {
            if (flist[i].isDirectory())
            {
                size = size + getFileSize(flist[i]);
            } else
            {
                size = size + flist[i].length();
            }
        }
        return size;
    }
    
    public String FormetFileSize(long fileS) {//ת���ļ���С
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
   
    public long getlist(File f){//�ݹ���ȡĿ¼�ļ�����
        long size = 0;
        File flist[] = f.listFiles();
        size=flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;

 
   }
}