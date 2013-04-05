package cz.cvut.fel.bupro.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

public class FilterRequest implements Filterable {

	private final Map<String, String> map;

	public FilterRequest(Map<String, String> map) {
		if (map == null) {
			throw new NullPointerException();
		}
		this.map = map;
	}

	public Map<String, String> getFilter() {
		return Collections.unmodifiableMap(map);
	}

	private String escape(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	public String getParams() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry == null) {
				continue;
			}
			if (entry.getValue() == null || entry.getValue().isEmpty()) {
				continue;
			}
			if (entry.getKey() == null || entry.getKey().isEmpty()) {
				continue;
			}
			if (sb.length() > 0) {
				sb.append('&');
			}
			sb.append(escape("filter." + entry.getKey()));
			sb.append('=');
			sb.append(escape(entry.getValue()));
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return map.toString();
	}

}
