package cz.cvut.fel.kos;

import java.util.Collection;
import java.util.Locale;

import cz.cvut.fel.kos.jaxb.Label;

/**
 * Class for localization of {@link Label}
 * 
 * @author Viktor Souček
 * 
 */
public class Translator {

	private final Locale locale;
	private final Locale defaultLocale;

	/**
	 * Creates new instance of translator with default locale
	 * {@link Locale#ENGLISH}
	 * 
	 * @param locale
	 */
	public Translator(Locale locale) {
		this(locale, Locale.ENGLISH);
	}

	public Translator(Locale locale, Locale defaultLocale) {
		this.locale = locale;
		this.defaultLocale = defaultLocale;
	}

	/**
	 * Finds label in language specified by this instance locale. If no label is
	 * found with specified language, method fallbacks on default locale of this
	 * instance.
	 * 
	 * @param labels
	 * @return label instance with language determined by this translator locale
	 *         and default locale or <code>null</code> if there is no such label
	 */
	public Label localizedLabel(Collection<? extends Label> labels) {
		Label defaultLangLabel = null;
		for (Label label : labels) {
			Locale lang = new Locale(label.getLang());
			if (locale.equals(lang)) {
				return label;
			}
			if (defaultLocale == null) {
				continue;
			}
			if (defaultLocale.equals(lang)) {
				defaultLangLabel = label;
			}
		}
		return defaultLangLabel;
	}

	/**
	 * Finds and obtains localized string from collection of labels. It is more
	 * convenient version of {@link #localizedLabel(Collection)} method.
	 * 
	 * @param labels
	 * @return localized label value or <code>null</code> if no label is found
	 * @see {@link #localizedLabel(Collection)}
	 */
	public String localizedString(Collection<? extends Label> labels) {
		Label label = localizedLabel(labels);
		return (label == null) ? null : label.getValue();
	}

}
