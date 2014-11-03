package iO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FileUtil {
	
	/**
	 * ɾ���ļ��������Ŀ¼��ͬʱɾ��������Ŀ¼��Ŀ¼���Բ�Ϊ�ա�
	 * ���ָ���ļ������ڣ��򷵻�<code>true</code>��
	 */
	public static boolean delete(File file) {
		if (null == file || !file.exists()) {
			return true;
		}
		if (file.isDirectory()) {
			return cleanDirectory(file) && file.delete();
		} else {
			return file.delete();
		}
	}

	/**
	 * ���Ŀ¼��ֻ����Ŀ¼�������ָ��Ŀ¼�����ڣ��򷵻�<code>true</code>��
	 * ���ָ��Ŀ¼Ϊ�ļ����򷵻�<code>false</code>��
	 */
	public static boolean cleanDirectory(File directory) {
		if (null == directory || !directory.exists()) {
			return true;
		}
		if (!directory.isDirectory()) {
			return false;
		}
		boolean result = true;
		//�ҵ��ļ���������ļ�/Ŀ¼
		File[] files = directory.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			result = result && delete(file);//�ݹ�ĵ���
		}
		return result;
	}

	/**
	 * ���˳�JVMʱɾ���ļ��������Ŀ¼��ͬʱɾ��������Ŀ¼��Ŀ¼���Բ�Ϊ�ա�
	 */
	public static void deleteOnExit(File file) throws IOException {
		if (null != file && file.exists()) {
			if (file.isDirectory()) {
				cleanDirectoryOnExit(file);
				file.deleteOnExit();
			} else {
				file.deleteOnExit();
			}
		}
	}

	/**
	 * ���˳�JVMʱ���Ŀ¼��ֻ����Ŀ¼����
	 */
	public static void cleanDirectoryOnExit(File directory) throws IOException {
		if (null == directory || !directory.exists()) {
			// do nothing
			return;
		}
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(directory
					+ " is not a directory.");
		}

		IOException exception = null;
		File[] files = directory.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			try {
				deleteOnExit(file);
			} catch (IOException ioe) {
				exception = ioe;
			}
		}
		if (null != exception) {
			throw exception;
		}
	}

	/**
	 * ��Դ�ļ���ΪĿ���ļ��������ļ���Ŀ��Ŀ¼���Բ����ڡ�
	 */
	public static File copy(final File sourceFile, final File destDir)
			throws IOException {
		if (null == sourceFile || !sourceFile.isFile() || null == destDir)
			return null;
		return copy(sourceFile, destDir, sourceFile.getName());
	}

	/**
	 * ��ָ��Ŀ���ļ��������ļ���Ŀ��Ŀ¼���Բ����ڡ�
	 */
	public static File copy(final File sourceFile, final File destDir,
			final String fileName) throws IOException {
		String _fileName = fileName;
		if (null == sourceFile || !sourceFile.isFile() || null == destDir)
			return null;
		if (null == _fileName)
			_fileName = sourceFile.getName();
		if (sourceFile.getParentFile().equals(destDir)) {
			return sourceFile;
		}
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		if (!destDir.isDirectory()) {
			throw new IOException("Destination must be a directory.");
		}

		File target = new File(destDir, fileName);
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				sourceFile));
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(target));
		try {
			IOUtil.copy(in, out);
		} finally {
			out.flush();
			IOUtil.close(in);
			IOUtil.close(out);
		}
		return target;
	}

	/**
	 * ��Դ�ļ��ƶ���Ŀ��Ŀ¼�¡�
	 * 
	 * @param sourceFile
	 *            Դ�ļ�
	 * @param destDir
	 *            Ŀ��Ŀ¼
	 * @return Ŀ���ļ�
	 * @throws IOException
	 *             �����з����쳣
	 */
	public static File move(final File sourceFile, final File destDir)
			throws IOException {
		File target = copy(sourceFile, destDir);
		if (null != sourceFile)
			sourceFile.delete();
		return target;
	}

	/**
	 * Implements the same behaviour as the "touch" utility on Unix. It creates
	 * a new file with size 0 or, if the file exists already, it is opened and
	 * closed without modifying it, but updating the file date and time.
	 * 
	 * @param file
	 *            the File to touch
	 * @throws IOException
	 *             If an I/O problem occurs
	 */
	public static void touch(File file) throws IOException {
		if (null == file)
			return;
		if (!file.exists()) {
			OutputStream out = new FileOutputStream(file);
			IOUtil.close(out);
		} else
			file.setLastModified(System.currentTimeMillis());
	}

	/**
	 * Convert the array of Files into an array of URLs.
	 * 
	 * @param files
	 *            the array of files
	 * @return the array of URLs
	 * @throws IOException
	 *             if an error occurs
	 */
	public static URL[] toURLs(File[] files) throws IOException {
		if (null == files)
			return new URL[0];
		URL[] urls = new URL[files.length];
		for (int i = 0; i < urls.length; i++) {
			urls[i] = files[i].toURI().toURL();
		}
		return urls;
	}

	/**
	 * ��<code>URL</code>��ʾ��ת��Ϊ<code>File</code>�ı�ʾ������
	 * 
	 * @param url
	 *            �ļ���<code>URL</code>
	 * @return The equivalent <code>File</code> object, or <code>null</code> if
	 *         the URL's protocol is not <code>file</code>
	 */
	public static File toFile(URL url) {
		if (null == url || url.getProtocol().equals("file") == false) {
			return null;
		} else {
			String filename = url.getFile().replace('/', File.separatorChar);
			return new File(filename);
		}
	}

	/**
	 * ��ȡ�ļ����ݡ�
	 * 
	 * @param file
	 *            �ļ���
	 * @return �ļ�����
	 * @throws IOException
	 */
	public static String content(File file) throws IOException {
		if (null == file)
			return null;
		InputStream in = new FileInputStream(file);
		try {
			return IOUtil.toString(in);
		} finally {
			IOUtil.close(in);
		}
	}

	

	

	/**
	 * ���ļ���ʽ��DOSת����UNIX��Դ�ļ�������Ϊ��<code>.dos</code>Ϊ��׺���ļ���
	 * 
	 * @param srcDosFile
	 *            Դ�ļ�
	 * @param deleteOriFile
	 *            �Ƿ���Ҫɾ�������ļ�
	 */
	public static void dos2Unix(File srcDosFile, boolean deleteOriFile) {
		if (srcDosFile.isDirectory())
			return;
		String strOriginalDosFile = srcDosFile.getAbsolutePath();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File destUnixFile = new File(strOriginalDosFile + ".unix");
		try {
			fis = new FileInputStream(srcDosFile);
			fos = new FileOutputStream(destUnixFile);

			boolean bNeedConvert = false;
			boolean bFileFinished = false;
			boolean bDiscardFirstNewLineByte = false;

			byte[] bsInBuffer = new byte[IOUtil.DEFAULT_BUFFER_SIZE];
			byte[] bsOutBuffer = new byte[IOUtil.DEFAULT_BUFFER_SIZE];

			while (!bFileFinished) {
				int nReadByteCount = fis.read(bsInBuffer, 0,
						IOUtil.DEFAULT_BUFFER_SIZE);
				int nOutBufferIndex = 0;
				for (int i = 0; i < nReadByteCount; i++) {
					byte bCurrentByte = bsInBuffer[i];
					if (i == 0 && bDiscardFirstNewLineByte
							&& bCurrentByte == 10) {
						// ֱ�Ӻ��Ե�һ���ֽڵ�"\n"
						bDiscardFirstNewLineByte = false;
						continue;
					}
					if (bCurrentByte == 13) { // Ϊ "\r"
						if (!bNeedConvert) {
							bNeedConvert = true;
						}
						bsOutBuffer[nOutBufferIndex++] = (byte) 10;// ��"\r"�滻Ϊ'\n';

						if (i == (IOUtil.DEFAULT_BUFFER_SIZE - 1)) {// ��������,���´ζ����ĵ�һ���ֽڵ�"\n"ֱ�Ӻ���
							bDiscardFirstNewLineByte = true;
						} else if (i == nReadByteCount - 1) {
							;// ����������һ���ֽ�,do nothing
						} else if (bsInBuffer[i + 1] == 10) {// �����һ��Ϊ'\n',�����
							i++;
						}
					} else {
						bsOutBuffer[nOutBufferIndex++] = bCurrentByte;
					}
				}
				if (nOutBufferIndex > 0)
					fos.write(bsOutBuffer, 0, nOutBufferIndex);
				if (nReadByteCount == -1
						|| nReadByteCount < IOUtil.DEFAULT_BUFFER_SIZE) {
					bFileFinished = true;
				}
			}
			IOUtil.close(fis);
			if (fos != null) {
				fos.flush();
				IOUtil.close(fos);
			}
			if (bNeedConvert) {
				File fDos = new File(strOriginalDosFile + ".dos");
				srcDosFile.renameTo(fDos);
				if (deleteOriFile)
					fDos.delete();
				destUnixFile.renameTo(new File(strOriginalDosFile));
			} else {
				destUnixFile.delete();
			}
		} catch (Exception e) {
//			Logger.error("�����ļ�\"" + strOriginalDosFile + "\"ʱ�����쳣!", e);
		} finally {
			IOUtil.close(fis);
			IOUtil.close(fos);
		}

	}

	/**
	 * ������ص�Ŀ¼��Ŀ¼�ṹΪ<code>strSource + &quot;/&quot; + strTarget</code>��
	 * �ڿ����ļ�ʱ��Ҫ������ͬ��Ŀ¼�ṹʱ�ɵ��ô˷�����
	 * <p>
	 * ��������:(2001-9-15 13:06:04)
	 * 
	 * @param strSource
	 *            strSourceΪ���·��ʱ����&quot;/&quot;��ͷ���Ұ����ļ���
	 * @param strTarget
	 *            Ŀ��Ŀ¼��(����strSource�γɾ���·��)��null��ʾstrSourceΪ����·����
	 * @exception IOException
	 *                @
	 */
	public static void createDirectoryAsNeeded(String strSource,
			String strTarget) throws IOException {
		if (strSource.indexOf("\\") >= 0) {
			strSource = strSource.replaceAll("\\", "/");
		}
		StringTokenizer st = new StringTokenizer(strSource, "/");
		if (strTarget == null && st.hasMoreElements()) {
			// ����Ǿ���·��,���̷���ΪstrTarget
			strTarget = st.nextElement().toString();
		}
		StringBuffer newPath = new StringBuffer();
		if (strTarget != null) {
			newPath.append(strTarget);
		}
		while (st.hasMoreElements()) {
			String str = st.nextElement().toString();
			if (!st.hasMoreElements())
				break;
			newPath.append(File.separator);
			newPath.append(str);
			File dir = new File(newPath.toString());
			if (dir.exists()) {
				continue;
			} else {
				dir.mkdir();
			}
		}
	}

	/**
	 * ���ļ�����·����������������ļ���ȫ·����������������ļ�·�������Ի���ܲ����ļ��ָ�����β��
	 * <p>
	 * ��������:(2001-8-11 11:35:08)
	 * 
	 * @param strPath
	 *            �ļ�·��
	 * @param strFileName
	 *            �ļ���
	 */
	public static String concatPathAndFile(String strPath, String strFileName) {
		String strWhole = null;
		/* ���strPath�Ѱ���·���ָ�������ֱ�����ļ���ƴ�ӣ������ȼ�·���ָ�������ƴ���ļ����� */
		if (strPath.charAt(strPath.length() - 1) == File.separatorChar) {
			strWhole = strPath + strFileName;
		} else {
			strWhole = strPath + File.separatorChar + strFileName;
		}
		return strWhole;
	}

	/**
	 * �����ļ��� ��srcDir�µ������ļ�������dstDir��
	 * 
	 * @author cch
	 * @since V55
	 * @param srcDir
	 * @param dstDir
	 */
	public static void copyDirectory(File srcDir, File dstDir) {
		if (srcDir.isDirectory()) {
			if (!dstDir.exists()) {
				dstDir.mkdir();
			}

			String[] children = srcDir.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(srcDir, children[i]), new File(dstDir,
						children[i]));
			}
		} else {
			// This method is implemented in e1071 Copying a File
			copyFile(srcDir, dstDir);
		}
	}

	/**
	 * ͨ��NIO�ķ�ʽ�����ļ������Խ�����ļ�����������
	 * 
	 * @author cch
	 * @since V55
	 * @param srcDir
	 * @param dstDir
	 */
	public static void copyFile(File srcDir, File dstDir) {
		try {
			// Create channel on the source
			FileChannel srcChannel = new FileInputStream(srcDir).getChannel();

			// Create channel on the destination
			FileChannel dstChannel = new FileOutputStream(dstDir).getChannel();

			// Copy file contents from source to destination
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

			// Close the channels
			srcChannel.close();
			dstChannel.close();
		} catch (IOException e) {
//			Logger.error("", e);
		}
	}
	

	/**
	 * ���ֽ���д���ļ�
	 */
	public static File writeBytesToFile(byte[] fileData, String filePath,
			boolean append) throws IOException {

		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		// ��������ļ���������������������������Բ�����Ŀ����˵��
		FileOutputStream outStream = new FileOutputStream(file, append);
		outStream.write(fileData);
		IOUtil.close(outStream);

		return file;
	}

	/**
	 * ������д���ļ�
	 * [1,2,3]д���ļ����Ϊ1,2,3
	 * */
	public static File writeIntArrayToFile(int[] array, String filePath,
			boolean append) throws IOException {
		String fileData = Arrays.toString(array);
		int length = fileData.length();
		return writeStringToFile(fileData.substring(1, length - 1), filePath,
				append);
	}

	/**
	 * ���ַ���д���ļ�
	 * */
	public static File writeStringToFile(String fileData, String filePath,
			boolean append) throws IOException {
		return writeBytesToFile(fileData.getBytes(), filePath, append);
	}
}

