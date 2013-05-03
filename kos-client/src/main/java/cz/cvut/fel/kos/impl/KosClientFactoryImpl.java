package cz.cvut.fel.kos.impl;

import org.springframework.web.client.RestTemplate;

import cz.cvut.fel.kos.Configuration;
import cz.cvut.fel.kos.KosClient;
import cz.cvut.fel.kos.KosClientFactory;

public class KosClientFactoryImpl implements KosClientFactory {

	private final RestTemplate restTemplate;

	public KosClientFactoryImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public KosClient createInstance(Configuration configuration) {
		if (restTemplate == null) {
			throw new IllegalStateException("Can't create Spring implementation of KOS client - missing REST template instance");
		}
		return new KosClientImpl(restTemplate, configuration);
	}

}
