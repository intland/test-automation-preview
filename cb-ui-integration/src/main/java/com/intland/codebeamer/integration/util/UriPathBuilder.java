package com.intland.codebeamer.integration.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;

public class UriPathBuilder {

	private static final String SLASH = "/";
	
	private StringBuilder pathBuilder = new StringBuilder();

	public UriPathBuilder(String base) {
		super();
		this.pathBuilder = new StringBuilder(cleanUrl(base));
	}
	
	public UriPathBuilder path(String path) {
		pathBuilder.append(cleanUrl(path));
		return this;
	}
	
	public String build() {
		return StringUtils.removeEnd(pathBuilder.toString(), SLASH);
	}
	
	public URI buildURI()  {
		try {
			return new URI(build());
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private String cleanUrl(String url) {
		return StringUtils.removeStart(StringUtils.removeEnd(url, SLASH), SLASH) + SLASH;
	}
	
}
