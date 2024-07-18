package com.intland.codebeamer.integration.classic.page.userregistration.component;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class UserRegistrationFormComponent extends AbstractCodebeamerComponent<UserRegistrationFormComponent, UserRegistrationFormAssertions> {

	public UserRegistrationFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "form#userForm");
	}

	public UserRegistrationFormComponent fillOut(Consumer<UserRegistrationFormBuilder> formBuilder) {
		formBuilder.accept(fillOut());
		return this;
	}

	public UserRegistrationFormBuilder fillOut() {
		return new UserRegistrationFormBuilder(this);
	}
	
	public CodebeamerLocator getUsernameField() {
		return this.locator("input#user\\.name");
	}

	public CodebeamerLocator getUsernameErrorElement() {
		return this.locator("#user\\.name\\.errors");
	}
	
	public CodebeamerLocator getPasswordField() {
		return this.locator("input#newPassword");
	}

	public CodebeamerLocator getPasswordErrorElement() {
		return this.locator("#newPassword\\.errors");
	}
	
	public CodebeamerLocator getConfirmPasswordField() {
		return this.locator("input#newPasswordAgain");
	}

	public CodebeamerLocator getFirstNameField() {
		return this.locator("input#user\\.firstName");
	}

	public CodebeamerLocator getLastNameField() {
		return this.locator("input#user\\.lastName");
	}

	public CodebeamerLocator getEmailField() {
		return this.locator("input#user\\.email");
	}

	public CodebeamerLocator getCompanyField() {
		return this.locator("input#user\\.company");
	}

	@Override
	public UserRegistrationFormAssertions assertThat() {
		return new UserRegistrationFormAssertions(this);
	}

}
