package cz.cvut.fel.bupro.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class FilterableArgumentResolver implements HandlerMethodArgumentResolver {
	public static final String FILTER_PREFIX = "filter.";

	private final Log log = LogFactory.getLog(getClass());

	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(Filterable.class);
	}

	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
			throws Exception {
		assert parameter.getParameterType().equals(Filterable.class);
		Map<String, String> map = new HashMap<String, String>();
		Iterator<String> it = webRequest.getParameterNames();
		while (it.hasNext()) {
			String name = it.next();
			if (name.startsWith(FILTER_PREFIX)) {
				String value = webRequest.getParameter(name);
				name = name.substring(FILTER_PREFIX.length());
				log.trace("filter param [" + name + "," + value + "]");
				map.put(name, value);
			}
		}
		return new FilterRequest(map);
	}

}
