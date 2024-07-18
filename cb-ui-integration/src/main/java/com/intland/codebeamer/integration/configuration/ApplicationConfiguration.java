package com.intland.codebeamer.integration.configuration;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.util.UriPathBuilder;

public record ApplicationConfiguration(String url, ApiUserConfiguration apiUserConfiguration) {

	public ApplicationConfiguration(String url, ApiUserConfiguration apiUserConfiguration) {
		this.url =  new UriPathBuilder(url).build();
		this.apiUserConfiguration = apiUserConfiguration;
	}
	
	public ApiUser getApiUser() {
		ApiUserConfiguration apiUserConfiguration = this.apiUserConfiguration();
		return new ApiUser(apiUserConfiguration.username(), apiUserConfiguration.password());
	}
	
}
