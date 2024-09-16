package com.intland.codebeamer.integration.test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.application.ClassicCodebeamerApplication;

public abstract class AbstractIntegrationClassicNGTests extends AbstractIntegrationNGTests {

	private User user;

	public ClassicCodebeamerApplication getClassicCodebeamerApplication() {
		return new ClassicCodebeamerApplication(getCodebeamerPage(), this.user);
	}
	
	public ClassicCodebeamerApplication getClassicCodebeamerApplication(User userDetails) {
		this.user = userDetails;
		return getClassicCodebeamerApplication(userDetails.getName(), UserApiService.DEFAULT_PASSWORD);
	}
	
	public ClassicCodebeamerApplication getClassicCodebeamerApplication(User userDetails, String password) {
		return getClassicCodebeamerApplication(userDetails.getName(), password);
	}
	
	public ClassicCodebeamerApplication getClassicCodebeamerApplication(String username, String password) {
		authenticate(username, password);
		return getClassicCodebeamerApplication();
	}

}