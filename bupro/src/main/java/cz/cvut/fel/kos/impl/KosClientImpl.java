package cz.cvut.fel.kos.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import cz.cvut.fel.kos.Configuration;
import cz.cvut.fel.kos.KosClient;
import cz.cvut.fel.kos.jaxb.Semester;
import cz.jirutka.atom.jaxb.Entry;

/**
 * KOS Client API implementation built on Spring REST template
 */
public class KosClientImpl implements KosClient {
	private final Log log = LogFactory.getLog(getClass());

	private final Configuration configuration;
	private final RestTemplate template;

	@Autowired
	public KosClientImpl(RestTemplate template, Configuration configuration) {
		this.template = template;
		this.configuration = configuration;
	}

	@PostConstruct
	public void configureKosCredentials() {
		ClientHttpRequestFactory rf = template.getRequestFactory();
		if (rf instanceof HttpComponentsClientHttpRequestFactory) {
			log.info("RESTtemplate configuring http BASIC AUTH");
			HttpComponentsClientHttpRequestFactory requestFactory = (HttpComponentsClientHttpRequestFactory) rf;
			DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();
			URL url = null;
			try {
				url = new URL(configuration.getUri());
			} catch (MalformedURLException e) {
				log.error("Malformed KOS URL", e);
			}
			String host = (url == null) ? null : url.getHost();
			AuthScope authScope = new AuthScope(host, -1, AuthScope.ANY_REALM);
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(configuration.getUsername(), configuration.getPassword());
			httpClient.getCredentialsProvider().setCredentials(authScope, credentials);
			log.info("RESTtemplate BASIC AUTH configured for host:" + host + " port:any");
		} else {
			log.warn("RESTtemplate request factory BASIC AUTH disabled!");
		}
	}

	@SuppressWarnings("unchecked")
	private <T> Entry<T> getForObject(String uri, Map<String, ?> urlVariables) {
		Entry<?> e = template.getForObject(uri, Entry.class, urlVariables);
		return (Entry<T>) e;
	}

	public Semester getSemmester() {
		Map<String, String> urlVariables = new HashMap<String, String>();
		Entry<Semester> entry = getForObject(configuration.getUri() + "semesters/current", urlVariables);
		return entry.getContent();
	}

	public Semester getSemmester(int index) {
		Map<String, String> urlVariables = new HashMap<String, String>();
		Entry<Semester> entry = getForObject(configuration.getUri() + "semesters/current", urlVariables);
		return entry.getContent();
	}

	public Semester getSemmester(String code) {
		Map<String, String> urlVariables = new HashMap<String, String>();
		Entry<Semester> entry = getForObject(configuration.getUri() + "semesters/current", urlVariables);
		return entry.getContent();
	}

}
