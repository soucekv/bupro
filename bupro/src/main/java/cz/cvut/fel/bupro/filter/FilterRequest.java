package cz.cvut.fel.bupro.filter;

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

	public boolean isEmpty() {
		if (map.isEmpty()) {
			return true;
		}
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String value = entry.getValue();
			System.out.println("FilterRequest.isEmpty() val '" + value + "'");
			if (value != null && !value.trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return map.toString();
	}

}
