package com.intland.codebeamer.integration.api.legacy;

import com.intland.codebeamer.integration.api.legacy.AbstractLegacyApi.RestException;

public class LegacyApiException extends RuntimeException {

	private RestException exceptionInformation;

	public LegacyApiException(RestException exceptionInformation) {
		super(exceptionInformation.message());
		this.exceptionInformation = exceptionInformation;
	}
	
	public boolean isArtifactNameConflictException() {
		return "ArtifactNameConflictException".equals(this.exceptionInformation.exception());
	}
}

