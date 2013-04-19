package cz.cvut.fel.kos.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import cz.cvut.fel.kos.Configuration;
import cz.cvut.fel.kos.KosClient;
import cz.cvut.fel.kos.KosClientFactory;

public class KosClientFactoryImpl implements KosClientFactory {

	@Autowired
	private RestTemplate kosRestTemplate;

	public KosClient createInstance(Configuration configuration) {
		if (kosRestTemplate == null) {
			throw new IllegalStateException("Can't create Spring implementation of KOS client - missing REST template instance");
		}
		return new KosClientImpl(kosRestTemplate, configuration);
	}

}
