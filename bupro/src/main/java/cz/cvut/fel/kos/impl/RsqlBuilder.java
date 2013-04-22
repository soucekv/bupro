package cz.cvut.fel.kos.impl;

/**
 * Helper class for construction of RSQL queries
 * 
 */
public class RsqlBuilder {
	private static final char AND = ';';
	private static final char OR = ',';

	private final StringBuilder sb = new StringBuilder();

	public RsqlBuilder startWith(String property, String value) {
		equal(property, String.valueOf(value) + "*");
		return this;
	}

	public RsqlBuilder equal(String property, Object value) {
		sb.append(String.valueOf(property));
		sb.append("==");
		sb.append(String.valueOf(value));
		return this;
	}

	public RsqlBuilder or() {
		sb.append(OR);
		return this;
	}

	public RsqlBuilder and() {
		sb.append(AND);
		return this;
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}
