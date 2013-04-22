package cz.cvut.fel.kos.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationEventHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;

import cz.cvut.fel.kos.KosClientFactory;

/**
 * Spring configuration class. This class configures internal structure of KOS
 * spring implementation.
 */
@Configuration
public class DefaultKosConfiguration {

	@Bean
	public ValidationEventHandler kosValidationEventHandler() {
		return new LogValidationEventHandler();
	}

	@Bean
	public Jaxb2Marshaller kosJaxbMarshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPaths("cz.cvut.fel.kos.jaxb", "cz.jirutka.atom.jaxb");
		jaxb2Marshaller.setValidationEventHandler(kosValidationEventHandler());
		return jaxb2Marshaller;
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory kosHttpClientFactory() {
		return new HttpComponentsClientHttpRequestFactoryBasicAuth();
	}

	@Bean
	public RestTemplate kosRestTemplate() {
		Jaxb2Marshaller jaxb2Marshaller = kosJaxbMarshaller();
		MarshallingHttpMessageConverter marshallingConverter = new MarshallingHttpMessageConverter(jaxb2Marshaller, jaxb2Marshaller);
		RestTemplate restTemplate = new RestTemplate(kosHttpClientFactory());
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>(2);
		messageConverters.add(marshallingConverter);
		messageConverters.add(new FormHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}

	@Bean
	public KosClientFactory kosClientFactory() {
		return new KosClientFactoryImpl();
	}

}
