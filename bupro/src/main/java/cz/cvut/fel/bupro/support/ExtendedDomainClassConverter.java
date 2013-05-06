package cz.cvut.fel.bupro.support;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.repository.support.Repositories;
import org.springframework.util.StringUtils;

import cz.cvut.fel.bupro.dao.TagGroupRepository;
import cz.cvut.fel.bupro.dao.TagRepository;
import cz.cvut.fel.bupro.model.Tag;
import cz.cvut.fel.bupro.model.TagGroup;

public class ExtendedDomainClassConverter<T extends ConversionService & ConverterRegistry> extends DomainClassConverter<T> {
	private final Log log = LogFactory.getLog(getClass());

	private Repositories repositories = null;

	public ExtendedDomainClassConverter(T conversionService) {
		super(conversionService);
	}

	private TagGroup getTagGroup(String value) {
		CrudRepository<?, Serializable> repository = repositories.getRepositoryFor(TagGroup.class);
		TagGroupRepository tagGroupRepository = TagGroupRepository.class.cast(repository);
		TagGroup tagGroup = tagGroupRepository.findByName(value);
		log.trace("TagGroup " + value + " = " + tagGroup);
		if (tagGroup == null) {
			tagGroup = new TagGroup(value);
		}
		return tagGroup;
	}

	private Tag getTag(String value) {
		CrudRepository<?, Serializable> repository = repositories.getRepositoryFor(Tag.class);
		TagRepository tagRepository = TagRepository.class.cast(repository);
		Tag tag = tagRepository.findByName(value);
		log.trace("Tag " + value + " = " + tag);
		if (tag == null) {
			tag = new Tag(value);
		}
		return tag;
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		if (source == null || !StringUtils.hasText(source.toString())) {
			return null;
		}
		log.trace("convert " + source + " sourceType " + sourceType.getType() + " targetType " + targetType.getType());
		if (sourceType.getType().equals(String.class) && targetType.getType().equals(Tag.class)) {
			return getTag(String.valueOf(source));
		} else if (sourceType.getType().equals(String.class) && targetType.getType().equals(TagGroup.class)) {
			return getTagGroup(String.valueOf(source));
		}
		return super.convert(source, sourceType, targetType);
	}

	public void setApplicationContext(ApplicationContext context) {
		super.setApplicationContext(context);
		this.repositories = new Repositories(context);
	}

}
