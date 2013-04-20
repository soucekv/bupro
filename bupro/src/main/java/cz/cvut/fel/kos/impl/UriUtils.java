package cz.cvut.fel.kos.impl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UriUtils {

	private UriUtils() {
		// Disable instances
	}

	public static URI mergeAsURL(URI base, String relative) throws MalformedURLException, URISyntaxException {
		return new URL(base.toURL(), relative).toURI();
	}

	public static URI mergeAsURL(URI base, URI relative) throws MalformedURLException, URISyntaxException {
		return mergeAsURL(base, relative.toString());
	}

}
