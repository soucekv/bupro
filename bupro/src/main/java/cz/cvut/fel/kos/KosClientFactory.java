package cz.cvut.fel.kos;

public interface KosClientFactory {

	/**
	 * Creates new {@link KosClient} instance configured by
	 * {@link Configuration}
	 *
	 * @param configuration
	 *            used by client
	 * @return new {@link KosClient} instance configured by
	 *         {@link Configuration}
	 */
	KosClient createInstance(Configuration configuration);
}
