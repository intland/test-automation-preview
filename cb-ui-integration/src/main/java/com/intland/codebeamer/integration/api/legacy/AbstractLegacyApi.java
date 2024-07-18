package com.intland.codebeamer.integration.api.legacy;

import java.io.IOException;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.util.UriPathBuilder;

public class AbstractLegacyApi {

	private static final Logger logger = LogManager.getLogger();
	
	private String applicationUrl;

	private CodebeamerPasswordAuthenticator authenticator;
	
	private ObjectMapper objectMapper;

	public AbstractLegacyApi(String applicationUrl, ApiUser apiUser) {
		super();
		this.applicationUrl = new UriPathBuilder(applicationUrl).path("rest").build();
		this.authenticator = new CodebeamerPasswordAuthenticator(apiUser.username(), apiUser.password());
		this.objectMapper = new ObjectMapper(); 
	}

	protected <T extends Serializable> T sendPost(String endpoint, Serializable body, Class<T> responseType) throws URISyntaxException, IOException, InterruptedException {
		
		String bodyAsJson = objectMapper.writeValueAsString(body);
		
		try (HttpClient client = HttpClient.newHttpClient()) {
			HttpRequest request = HttpRequest.newBuilder()
					  .uri(new UriPathBuilder(applicationUrl).path(endpoint).buildURI())
					  .headers("Content-Type", "application/json;charset=UTF-8")
					  .headers("accept", "application/json")
					  .POST(HttpRequest.BodyPublishers.ofString(bodyAsJson))
					  .build();
		
			HttpResponse<String> response = HttpClient.newBuilder()
					  .authenticator(authenticator).build()
					  .send(request, BodyHandlers.ofString());
			
			if (isOkayStatus(response)) {
				return objectMapper.readValue(response.body(), responseType);
			}
			
			throw new LegacyApiException(getExceptionInformation(response));
		}
		
	}

	private boolean isOkayStatus(HttpResponse<String> response) {
		return response.statusCode() == 200 || response.statusCode() == 201 || response.statusCode() == 202 || response.statusCode() == 203;
	}

	private RestException getExceptionInformation(HttpResponse<String> response) throws JsonProcessingException, JsonMappingException {
		String body = response.body();
		try {
			return objectMapper.readValue(body, RestException.class);
		} catch (Exception e) {
			logger.info("Response body cannot be processed due to", e);
			return new RestException("NA", body);
		}
	}
	
	record RestException(String exception, String message) {}
	
	class CodebeamerPasswordAuthenticator extends Authenticator {

		private String username;

		private String password;

		public CodebeamerPasswordAuthenticator(String username, String password) {
			super();
			this.username = username;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password.toCharArray());
		}
	}
	
}
