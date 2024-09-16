package com.intland.codebeamer.integration.test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.application.NextgenCodebeamerApplication;
import com.intland.codebeamer.integration.util.CBXPathUtil;

public abstract class AbstractIntegrationNextgenNGTests extends AbstractIntegrationNGTests {

	public NextgenCodebeamerApplication getNextgenCodebeamerApplication() {
		return new NextgenCodebeamerApplication(getCodebeamerPage());
	}
	
	public NextgenCodebeamerApplication getNextgenCodebeamerApplication(User userDetails) {
		return getNextgenCodebeamerApplication(userDetails.getName(), UserApiService.DEFAULT_PASSWORD);
	}
	
	public NextgenCodebeamerApplication getNextgenCodebeamerApplication(User userDetails, String password) {
		return getNextgenCodebeamerApplication(userDetails.getName(), password);
	}
	
	public NextgenCodebeamerApplication getNextgenCodebeamerApplication(String username, String password) {
		NextgenCodebeamerApplication nextgenCodebeamerApplication = new NextgenCodebeamerApplication(getCodebeamerPage());

		nextgenCodebeamerApplication
			.visitLoginPage()
			.getLoginFormComponent()
			.login(username, password)
			.redirectedToProjectBrowserPage();
		
		return nextgenCodebeamerApplication;
	}
	
	@Override
	protected String getApplicationUrl() {
		return CBXPathUtil.getApplicationUrl(super.getApplicationUrl());
	}
	
}