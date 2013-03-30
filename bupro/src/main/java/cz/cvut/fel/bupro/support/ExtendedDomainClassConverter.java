package cz.cvut.fel.bupro.support;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.repository.support.Repositories;
import org.springframework.util.StringUtils;

import cz.cvut.fel.bupro.dao.TagRepository;
import cz.cvut.fel.bupro.model.Tag;

public class ExtendedDomainClassConverter<T extends ConversionService & ConverterRegistry> extends DomainClassConverter<T> {

	private Repositories repositories = null;

	public ExtendedDomainClassConverter(T conversionService) {
		super(conversionService);
	}

	private Tag get(String tagString) {
		CrudRepository<?, Serializable> repository = repositories.getRepositoryFor(Tag.class);
		TagRepository tagRepository = TagRepository.class.cast(repository);
		Tag tag = tagRepository.findByName(tagString);
		if (tag == null) {
			tag = new Tag();
			tag.setName(tagString);
		}
		return tag;
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		if (source == null || !StringUtils.hasText(source.toString())) {
			return null;
		}
		if (sourceType.getType().equals(String.class) && targetType.getType().equals(Tag.class)) {
			return get(String.valueOf(source));
		}
		return super.convert(source, sourceType, targetType);
	}

	public void setApplicationContext(ApplicationContext context) {
		super.setApplicationContext(context);
		this.repositories = new Repositories(context);
	}

}
