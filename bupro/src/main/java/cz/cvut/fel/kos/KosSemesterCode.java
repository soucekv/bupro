package cz.cvut.fel.kos;

/**
 * Util class for calculation of semester code
 * 
 * @author viktor
 * @see <a
 *      href="https://kosapi.fit.cvut.cz/projects/kosapi/wiki/SemesterFilter">KOS
 *      API documentation</a>
 * 
 */
public class KosSemesterCode {
	public static final String REGEX_PATTERN = "^[A-B][0-9]{2}[1-2]$";

	public static boolean validate(String code) {
		return code.matches(REGEX_PATTERN);
	}

	public static String encode(int year, Period period) {
		if (period == null) {
			throw new NullPointerException("Period is required for Semester code");
		}
		StringBuilder sb = new StringBuilder();
		int century = (year / 100) + 1;
		if (century != 20 && century != 21) {
			throw new IllegalArgumentException("Expecting only year in 20th and 21th century, but is " + year);
		}
		sb.append((century == 20) ? 'A' : 'B');
		sb.append(String.valueOf(year % 100));
		sb.append((Period.SUMMER == period) ? 2 : 1);
		String code = sb.toString();
		assert validate(code);
		return code;
	}

	public static Period decodePeriod(String code) {
		if (code == null) {
			throw new NullPointerException();
		}
		if (validate(code)) {
			throw new IllegalArgumentException("Invalid code " + code);
		}
		code = code.substring(code.length() - 1);
		int val = Integer.parseInt(code);
		if (val == 1) {
			return Period.WINTER;
		}
		if (val == 2) {
			return Period.SUMMER;
		}
		throw new IllegalArgumentException("Uknown period value " + code);
	}

	public static int decodeYear(String code) {
		if (code == null) {
			throw new NullPointerException();
		}
		if (validate(code)) {
			throw new IllegalArgumentException("Invalid code " + code);
		}
		int year = code.startsWith("A") ? 1900 : 2000;
		code = code.substring(1, 3);
		year += Integer.parseInt(code);
		return year;
	}

	public static String next(String code) {
		int year = decodeYear(code);
		Period period = decodePeriod(code);
		if (period == Period.WINTER) {
			return encode(year + 1, Period.SUMMER);
		}
		return encode(year, Period.WINTER);
	}

	public static String prev(String code) {
		int year = decodeYear(code);
		Period period = decodePeriod(code);
		if (period == Period.SUMMER) {
			return encode(year - 1, Period.WINTER);
		}
		return encode(year, Period.SUMMER);
	}
}
