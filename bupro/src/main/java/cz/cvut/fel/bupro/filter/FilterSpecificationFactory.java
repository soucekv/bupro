package cz.cvut.fel.bupro.filter;

import org.springframework.data.jpa.domain.Specification;


public interface FilterSpecificationFactory<T> {
	Specification<T> create(String value);
}
