package cz.cvut.fel.bupro.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class FilterSpecification<T> implements Specification<T> {
	private final String name;
	private final String value;

	public FilterSpecification(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	protected Predicate like(Root<T> root, CriteriaBuilder cb) {
		return cb.like(root.<String> get(name), value + "%");
	}

	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return like(root, cb);
	}
}
