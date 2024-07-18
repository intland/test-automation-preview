package com.intland.codebeamer.integration.test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.application.ClassicCodebeamerApplication;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

public abstract class AbstractIntegrationClassicNGTests extends AbstractIntegrationNGTests {

	public ClassicCodebeamerApplication getClassicCodebeamerApplication() {
		return new ClassicCodebeamerApplication(getCodebeamerPage(), new DataManagerService(getApplicationConfiguration()));
	}
	
	public ClassicCodebeamerApplication getClassicCodebeamerApplication(User userDetails) {
		return getClassicCodebeamerApplication(userDetails.getName(), UserApiService.DEFAULT_PASSWORD);
	}
	
	public ClassicCodebeamerApplication getClassicCodebeamerApplication(User userDetails, String password) {
		return getClassicCodebeamerApplication(userDetails.getName(), password);
	}
	
	public ClassicCodebeamerApplication getClassicCodebeamerApplication(String username, String password) {
		ClassicCodebeamerApplication classicCodebeamerApplication = getClassicCodebeamerApplication();
		
		classicCodebeamerApplication
			.visitLoginPage()
			.getLoginFormComponent()
			.login(username, password)
			.redirectedToUserMyWikiPage();
		
		return classicCodebeamerApplication;
	}
	
}