package alg;

/**
 * @author zha 字符串之间的转化
 */
public class Alg3StringToint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String intv = "1232192373290";
//		int value = Integer.parseInt(intv);
//		System.out.println(value);
	}

	// JDK的源码还是比较好的研究对象
	public static int parseInt(String s, int radix)
			throws NumberFormatException {
		/*
		 * WARNING: This method may be invoked early during VM initialization
		 * before IntegerCache is initialized. Care must be taken to not use the
		 * valueOf method.
		 */

		if (s == null) {
			throw new NumberFormatException("null");
		}

		if (radix < Character.MIN_RADIX) {
			throw new NumberFormatException("radix " + radix
					+ " less than Character.MIN_RADIX");
		}

		if (radix > Character.MAX_RADIX) {
			throw new NumberFormatException("radix " + radix
					+ " greater than Character.MAX_RADIX");
		}

		int result = 0;
		boolean negative = false;
		int i = 0, len = s.length();
		int limit = -Integer.MAX_VALUE;
		int multmin;
		int digit;

		if (len > 0) {
			char firstChar = s.charAt(0);
			if (firstChar < '0') { // Possible leading "+" or "-"
				if (firstChar == '-') {
					negative = true;
					limit = Integer.MIN_VALUE;
				} else if (firstChar != '+')
					throw new NumberFormatException(s);

				if (len == 1) // Cannot have lone "+" or "-"
					throw new NumberFormatException(s);
				i++;
			}
			multmin = limit / radix;
			while (i < len) {
				// Accumulating negatively avoids surprises near MAX_VALUE
				digit = Character.digit(s.charAt(i++), radix);
				if (digit < 0) {
					throw new NumberFormatException(s);
				}
				if (result < multmin) {
					throw new NumberFormatException(s);
				}
				result *= radix;
				if (result < limit + digit) {
					throw new NumberFormatException(s);
				}
				result -= digit;
			}
		} else {
			throw new NumberFormatException(s);
		}
		return negative ? result : -result;
	}
}
