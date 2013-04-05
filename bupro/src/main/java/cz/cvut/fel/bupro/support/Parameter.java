package cz.cvut.fel.bupro.support;

import java.util.Map;

import cz.cvut.fel.bupro.filter.FilterableArgumentResolver;

/**
 * Util class to process URL paramters
 */
public class Parameter {

	/**
	 * Preprocessor function. Transforms map of parameters to string using thymeleaf syntax for building URL.
	 * <pre>
	 * th:href="@{${url}(page.page=${page.totalPages},page.size=${page.size}__${T(cz.cvut.fel.bupro.support.Parameter).encode(params)}__)}"
	 * </pre>
	 * @param map
	 * @return
	 */
	public static String encodeFilter(Map<String, String> map) {
		if (map.isEmpty()) {
			return "";
		}
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
			sb.append(',');
			sb.append(FilterableArgumentResolver.FILTER_PREFIX + entry.getKey());
			sb.append('=');
			sb.append('\'');
			sb.append(entry.getValue());
			sb.append('\'');
		}
		return sb.toString();
	}
}
