package cz.cvut.fel.reposapi.assembla.client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Assembla Space representation
 * 
 * @author Viktor Soucek
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Space {

	private String id;

	private String name;

	private String description;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return getClass().getName() + " [id=" + id + ", name=" + name + "]";
	}

}
