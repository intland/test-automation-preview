package com.intland.codebeamer.integration.classic.page.userregistration.component;

import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class UserRegistrationFormBuilder {

	@Component("User registration form")
	private UserRegistrationFormComponent userRegistrationFormComponent;

	public UserRegistrationFormBuilder(UserRegistrationFormComponent userRegistrationFormComponent) {
		this.userRegistrationFormComponent = userRegistrationFormComponent;
	}

	public UserRegistrationFormBuilder username(String userName) {
		this.userRegistrationFormComponent.getUsernameField().fill(userName);
		return this;
	}
	
	public UserRegistrationFormBuilder password(String password) {
		this.userRegistrationFormComponent.getPasswordField().readonlyWrapper(field -> field.fill(password));
		return this;
	}
	
	public UserRegistrationFormBuilder confirmPassword(String password) {
		this.userRegistrationFormComponent.getConfirmPasswordField().readonlyWrapper(field -> field.fill(password));
		return this;
	}
	
	public UserRegistrationFormBuilder firstName(String firstName) {
		this.userRegistrationFormComponent.getFirstNameField().fill(firstName);
		return this;
	}
	
	public UserRegistrationFormBuilder lasttName(String lasttName) {
		this.userRegistrationFormComponent.getLastNameField().fill(lasttName);
		return this;
	}

	public UserRegistrationFormBuilder email(String email) {
		this.userRegistrationFormComponent.getEmailField().fill(email);
		return this;
	}
	
	public UserRegistrationFormBuilder company(String company) {
		this.userRegistrationFormComponent.getCompanyField().fill(company);
		return this;
	}
	
	public UserRegistrationFormBuilder empty() {
		// TODO empty every field ???
		return this;
	}
	
}
