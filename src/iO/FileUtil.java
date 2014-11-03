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
	 * 删除文件。如果是目录，同时删除所有子目录。目录可以不为空。
	 * 如果指定文件不存在，则返回<code>true</code>。
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
	 * 清空目录，只留下目录名。如果指定目录不存在，则返回<code>true</code>。
	 * 如果指定目录为文件，则返回<code>false</code>。
	 */
	public static boolean cleanDirectory(File directory) {
		if (null == directory || !directory.exists()) {
			return true;
		}
		if (!directory.isDirectory()) {
			return false;
		}
		boolean result = true;
		//找到文件下面的子文件/目录
		File[] files = directory.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			result = result && delete(file);//递归的调用
		}
		return result;
	}

	/**
	 * 在退出JVM时删除文件。如果是目录，同时删除所有子目录。目录可以不为空。
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
	 * 在退出JVM时清空目录，只留下目录名。
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
	 * 以源文件名为目标文件名拷贝文件。目标目录可以不存在。
	 */
	public static File copy(final File sourceFile, final File destDir)
			throws IOException {
		if (null == sourceFile || !sourceFile.isFile() || null == destDir)
			return null;
		return copy(sourceFile, destDir, sourceFile.getName());
	}

	/**
	 * 以指定目标文件名拷贝文件。目标目录可以不存在。
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
	 * 将源文件移动到目标目录下。
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param destDir
	 *            目标目录
	 * @return 目标文件
	 * @throws IOException
	 *             拷贝中发生异常
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
	 * 将<code>URL</code>表示的转换为<code>File</code>的表示方法。
	 * 
	 * @param url
	 *            文件的<code>URL</code>
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
	 * 获取文件内容。
	 * 
	 * @param file
	 *            文件名
	 * @return 文件内容
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
	 * 将文件格式从DOS转换到UNIX。源文件将备份为以<code>.dos</code>为后缀的文件。
	 * 
	 * @param srcDosFile
	 *            源文件
	 * @param deleteOriFile
	 *            是否需要删除备份文件
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
						// 直接忽略第一个字节的"\n"
						bDiscardFirstNewLineByte = false;
						continue;
					}
					if (bCurrentByte == 13) { // 为 "\r"
						if (!bNeedConvert) {
							bNeedConvert = true;
						}
						bsOutBuffer[nOutBufferIndex++] = (byte) 10;// 将"\r"替换为'\n';

						if (i == (IOUtil.DEFAULT_BUFFER_SIZE - 1)) {// 作特殊标记,将下次读出的第一个字节的"\n"直接忽略
							bDiscardFirstNewLineByte = true;
						} else if (i == nReadByteCount - 1) {
							;// 不会再有下一个字节,do nothing
						} else if (bsInBuffer[i + 1] == 10) {// 如果下一个为'\n',则忽略
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
//			Logger.error("处理文件\"" + strOriginalDosFile + "\"时发生异常!", e);
		} finally {
			IOUtil.close(fis);
			IOUtil.close(fos);
		}

	}

	/**
	 * 创建相关的目录。目录结构为<code>strSource + &quot;/&quot; + strTarget</code>。
	 * 在拷贝文件时需要创建相同的目录结构时可调用此方法。
	 * <p>
	 * 创建日期:(2001-9-15 13:06:04)
	 * 
	 * @param strSource
	 *            strSource为相对路径时需以&quot;/&quot;开头，且包含文件名
	 * @param strTarget
	 *            目标目录名(加上strSource形成绝对路径)，null表示strSource为绝对路径名
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
			// 如果是绝对路径,则将盘符作为strTarget
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
	 * 将文件名与路径结合起来，返回文件的全路径名。处理给定的文件路径可能以或可能不以文件分隔符结尾。
	 * <p>
	 * 创建日期:(2001-8-11 11:35:08)
	 * 
	 * @param strPath
	 *            文件路径
	 * @param strFileName
	 *            文件名
	 */
	public static String concatPathAndFile(String strPath, String strFileName) {
		String strWhole = null;
		/* 如果strPath已包含路径分隔符，则直接与文件名拼接；否则先加路径分隔符，再拼接文件名。 */
		if (strPath.charAt(strPath.length() - 1) == File.separatorChar) {
			strWhole = strPath + strFileName;
		} else {
			strWhole = strPath + File.separatorChar + strFileName;
		}
		return strWhole;
	}

	/**
	 * 拷贝文件夹 将srcDir下的所有文件拷贝到dstDir下
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
	 * 通过NIO的方式拷贝文件，可以解决大文件拷贝的问题
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
	 * 将字节流写入文件
	 */
	public static File writeBytesToFile(byte[] fileData, String filePath,
			boolean append) throws IOException {

		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 这里面的文件的输出流，输入输出的名称是针对操作的目的来说的
		FileOutputStream outStream = new FileOutputStream(file, append);
		outStream.write(fileData);
		IOUtil.close(outStream);

		return file;
	}

	/**
	 * 那数组写进文件
	 * [1,2,3]写入文件会变为1,2,3
	 * */
	public static File writeIntArrayToFile(int[] array, String filePath,
			boolean append) throws IOException {
		String fileData = Arrays.toString(array);
		int length = fileData.length();
		return writeStringToFile(fileData.substring(1, length - 1), filePath,
				append);
	}

	/**
	 * 把字符串写入文件
	 * */
	public static File writeStringToFile(String fileData, String filePath,
			boolean append) throws IOException {
		return writeBytesToFile(fileData.getBytes(), filePath, append);
	}
}

