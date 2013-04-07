package cz.cvut.fel.bupro.support;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

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

	/**
	 * Decode property name from {@link Page} instance
	 * <pre>
	 * page.sort='__${T(cz.cvut.fel.bupro.support.Parameter).pageOrder(page)}__'
	 * </pre>
	 * @param page
	 * @return 1st sort order property name
	 */
	public static String pageOrder(Page<?> page) {
		if (page == null) {
			return "";
		}
		Sort sort = page.getSort();
		if (sort == null) {
			return "";
		}
		Iterator<Order> it = sort.iterator();
		if (it.hasNext()) {
			return it.next().getProperty();
		}
		return "";
	}

	/**
	 * Decode sort direction from {@link Page} instance
	 * <pre>
	 * page.sort.dir='__${T(cz.cvut.fel.bupro.support.Parameter).pageOrderDir(page)}__'
	 * </pre>
	 * @param page
	 * @return 1st sort order direction 'asc' or 'desc'
	 */
	public static String pageOrderDir(Page<?> page) {
		if (page == null) {
			return "";
		}
		Sort sort = page.getSort();
		if (sort == null) {
			return "";
		}
		Iterator<Order> it = sort.iterator();
		if (it.hasNext()) {
			Direction direction = it.next().getDirection();
			return (direction == null) ? "" : direction.toString().toLowerCase(Locale.US);
		}
		return "";
	}
}
