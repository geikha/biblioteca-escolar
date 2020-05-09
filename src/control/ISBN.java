package control;

public class ISBN {

	private static String ISBN10toISBN13(String s) {
		int[] digits = stringToDigits("978" + s);
		int checkSum = checkISBN13(digits);
		return "978-" + s.substring(0, s.length() - 1) + checkSum;
	}

	public static String formatISBN(String s) {
		if (s.replaceAll("-", "").length() == 10) {
			return ISBN10toISBN13(s).replaceAll("-", "");
		} else if (s.replaceAll("-", "").length() != 13)
			throw new IllegalArgumentException();
		return s.replaceAll("-", "");
	}

	public static int checkISBN10(int[] digits) {
		int i, s = 0, t = 0;
		for (i = 0; i < 10; i++) {
			t += digits[i];
			s += t;
		}
		return s % 11;
	}

	public static int checkISBN13(int[] digits) {
		int tot = 0;
		for (int i = 0; i < 12; i++) {
			tot += (i % 2 == 0) ? digits[i] * 1 : digits[i] * 3;
		}
		int checksum = 10 - (tot % 10);
		if (checksum == 10) {
			checksum = 0;
		}
		return checksum;
	}

	public static boolean validateISBNDigits(int[] digits) {
		switch (digits.length) {
		case 10:
			return checkISBN10(digits) == 0;
		case 13:
			return checkISBN13(digits) == digits[12];
		}
		return false;
	}

	public static boolean ISBNValido(String s) {
		char[] ch = s.toCharArray();

		if (ch[0] == '-')
			return false;

		if (ch[ch.length - 1] == '-')
			return false;

		for (int i = 1; i < ch.length; i++)
			if (ch[i] == '-' && ch[i - 1] == '-')
				return false;

//		int cantGuiones = 0;
//		for (int i = 0; i < ch.length; i++)
//			if (ch[i] == '-')
//				cantGuiones++;
 		ch = s.replaceAll("-", "").toCharArray();
//		if (cantGuiones == 0 || (ch.length == 10 && cantGuiones == 3) || (ch.length == 13 && cantGuiones == 4)) {
			if (ch.length == 10 || ch.length == 13) {
				int[] digits = stringToDigits(s);
				return validateISBNDigits(digits);
			}
//		}
		return false;
	}

	public static int[] stringToDigits(String s) {
		char[] ch = s.replaceAll("-", "").toCharArray();
		int[] digits = new int[ch.length];
		for (int i = 0; i < ch.length; i++)
			digits[i] = Integer.parseInt(String.valueOf(ch[i]));
		return digits;
	}

}
