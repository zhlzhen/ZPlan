package iO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;



/**
 * @author zhailzh
 * */
public class IOUtil {

	// ����Ĵ�С
	static final int DEFAULT_BUFFER_SIZE = 1024;

	public static final Charset UTF8_CHARSET = Charset.forName("utf-8");

	// 1. �����ǹرգ��������ֽ������ַ������������ͬʱ������������������룬һ���ĸ�����

	/**
	 * �ر�����������{@link java.io.InputStream#close()}һ�������˲��׳�IO�쳣��
	 * 
	 * @param input
	 *            ������
	 */
	public static void close(InputStream input) {
		if (input == null) {
			return;
		}
		try {
			input.close();
		} catch (IOException ioException) {
		}
	}

	/**
	 * �ر����������{@link java.io.OutputStream#close()}һ�������˲��׳�IO�쳣��
	 * 
	 * @param output
	 *            �����
	 */
	public static void close(OutputStream output) {
		if (output == null) {
			return;
		}
		try {
			output.close();
		} catch (IOException ioException) {
		}
	}

	/**
	 * �������ر�����������{@link java.io.Reader#close()}һ�������˲��׳�IO�쳣��
	 * 
	 * @param input
	 *            ������
	 */
	public static void close(Reader input) {
		if (input == null) {
			return;
		}
		try {
			input.close();
		} catch (IOException ioe) {
		}
	}

	/**
	 * �������ر����������{@link java.io.Writer#close()}һ�������˲��׳�IO�쳣��
	 * 
	 * @param output
	 *            �����
	 */
	public static void close(Writer output) {
		if (output == null) {
			return;
		}
		try {
			output.close();
		} catch (IOException ioe) {
		}
	}

	// 2. �������������

	public static long copy(final InputStream input, final OutputStream output)
			throws IOException {

		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	public static void copy(InputStream input, Writer output)
			throws IOException {
		InputStreamReader in = new InputStreamReader(input);
		copy(in, output);

	}

	public static void copy(InputStream input, Writer output, String encoding)
			throws IOException {
		if (encoding == null) {
			copy(input, output);
		} else {
			InputStreamReader in = new InputStreamReader(input, encoding);
			copy(in, output);
		}
	}

	public static void copy(Reader input, OutputStream output)
			throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(output);
		copy(input, out);
		// XXX Unless anyone is planning on rewriting OutputStreamWriter, we
		// have to flush here.
		out.flush();

	}

	public static void copy(Reader input, OutputStream output, String encoding)
			throws IOException {
		if (encoding == null) {
			copy(input, output);
		} else {
			OutputStreamWriter out = new OutputStreamWriter(output, encoding);
			copy(input, out);
			// XXX Unless anyone is planning on rewriting OutputStreamWriter,
			// we have to flush here.
			out.flush();
		}
	}

	public static long copy(Reader input, Writer output) throws IOException {
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return count;
	}

	// 3.��ȡ����
	/**
	 * ���ļ��еõ��ֽ���
	 */
	public static ByteArrayOutputStream readByteStreamFromFile(File newFile)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream stream = new FileInputStream(newFile);
		copy(stream, out);
		close(stream);
		return out;
	}

	/**
	 * ���ļ�·���еõ��ֽ���
	 * */
	public static ByteArrayOutputStream readByteStreamFromFile(String filePath)
			throws IOException {
		File file = new File(filePath);
		if (file.exists()) {
			return readByteStreamFromFile(file);
		} else {
			throw new IOException(filePath + " not exist an file ");
		}
	}

	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	/**
	 * ��������������ת��Ϊ�ֽ�����
	 */
	public static byte[] toByteArray(Reader input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	public static byte[] toByteArray(Reader input, String encoding)
			throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output, encoding);
		return output.toByteArray();
	}

	/**
	 * ���ַ���ת��Ϊ�ֽ����顣
	 * 
	 * @deprecated
	 */
	public static byte[] toByteArray(String input) throws IOException {
		return input.getBytes();
	}

	/**
	 * �ַ���ת��Ϊ������
	 * */
	public static InputStream toInputStream(String input) {
		byte[] bytes = input.getBytes();
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * �ַ���ת��Ϊ������
	 * */
	public static InputStream toInputStream(String input, String encoding)
			throws IOException {
		byte[] bytes = encoding != null ? input.getBytes(encoding) : input
				.getBytes();
		return new ByteArrayInputStream(bytes);
	}

	@Deprecated
	public static String toString(byte[] input, String encoding)
			throws IOException {
		if (encoding == null) {
			return new String(input);
		} else {
			return new String(input, encoding);
		}
	}

	/**
	 * ��������ת��Ϊ�ַ���
	 * */
	public static String toString(InputStream in) throws IOException {

		StringWriter sw = new StringWriter();
		copy(in, sw);
		return sw.toString();
	}

	/**
	 * �������������ݰ�ָ�������ʽת��Ϊ�ַ�����
	 */
	public static String toString(InputStream input, String encoding)
			throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw, encoding);
		return sw.toString();
	}

	/**
	 * ��������������ת��Ϊ�ַ���
	 */
	public static String toString(Reader input) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw);
		return sw.toString();
	}

	/**
	 * д���ֽ�����
	 * */
	public static void write(byte[] data, OutputStream output)
			throws IOException {
		if (data != null) {
			output.write(data);
		}
	}

	/**
	 * д���ֽ�����
	 * */
	public static void write(byte[] data, Writer output) throws IOException {
		if (data != null) {
			output.write(new String(data));
		}
	}

	/**
	 * д���ֽ�����
	 * */
	public static void write(byte[] data, Writer output, String encoding)
			throws IOException {
		if (data != null) {
			if (encoding == null) {
				write(data, output);
			} else {
				output.write(new String(data, encoding));
			}
		}
	}

	/**
	 * д���ַ�����
	 */
	public static void write(char[] data, Writer output) throws IOException {
		if (data != null) {
			output.write(data);
		}
	}

	/**
	 * д���ַ�����
	 */
	public static void write(char[] data, OutputStream output)
			throws IOException {
		if (data != null) {
			output.write(new String(data).getBytes());
		}
	}

	/**
	 * д���ַ�����
	 */
	public static void write(char[] data, OutputStream output, String encoding)
			throws IOException {
		if (data != null) {
			if (encoding == null) {
				write(data, output);
			} else {
				output.write(new String(data).getBytes(encoding));
			}
		}
	}

	
	public static void write(CharSequence data, Writer output)
			throws IOException {
		if (data != null) {
			write(data.toString(), output);
		}
	}

	
	public static void write(CharSequence data, OutputStream output)
			throws IOException {
		if (data != null) {
			write(data.toString(), output);
		}
	}

	
	public static void write(CharSequence data, OutputStream output,
			String encoding) throws IOException {
		if (data != null) {
			write(data.toString(), output, encoding);
		}
	}

	
	public static void write(String data, Writer output) throws IOException {
		if (data != null) {
			output.write(data);
		}
	}
}
