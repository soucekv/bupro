package cz.cvut.fel.bupro.filter;

import java.util.Map;

/**
 * Abstract interface for filtering information
 */
public interface Filterable {
	Map<String, String> getFilter();
}
