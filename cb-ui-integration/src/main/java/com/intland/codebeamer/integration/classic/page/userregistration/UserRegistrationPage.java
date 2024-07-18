package com.intland.codebeamer.integration.classic.page.userregistration;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.userregistration.component.UserRegistrationFormAssertions;
import com.intland.codebeamer.integration.classic.page.userregistration.component.UserRegistrationFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("UserRegistrationPage")
public class UserRegistrationPage extends AbstractCodebeamerPage<UserRegistrationPage> {
    
	private static final String CREATE_USER = "createUser.spr";
	
	@Component("User registration form")
	private UserRegistrationFormComponent userRegistrationFormComponent;

	public UserRegistrationPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.userRegistrationFormComponent = new UserRegistrationFormComponent(codebeamerPage);
	}

	@Action("Visit")
	public UserRegistrationPage visit() {
		navigate(CREATE_USER);
		return isActive();
	}

	@Override
	public UserRegistrationPage isActive() {
		assertUrl(CREATE_USER, "User registration page should be the active page");
		return this;
	}
	
	public UserRegistrationFormComponent getUserRegistrationFormComponent() {
		return userRegistrationFormComponent;
	}

	public UserRegistrationPage userRegistrationFormComponent(Consumer<UserRegistrationFormComponent> formConsumer) {
		formConsumer.accept(userRegistrationFormComponent);
		return this;
	}
	
	public UserRegistrationPage assertUserRegistrationFormComponent(Consumer<UserRegistrationFormAssertions> assertion) {
		assertion.accept(getUserRegistrationFormComponent().assertThat());
		return this;
	}

	@Action("Save")
	public UserRegistrationPageNavigation save() {
		this.getCodebeamerPage().locator("#userForm input[type='submit']:not([name])").click();
		return new UserRegistrationPageNavigation(getCodebeamerPage());
	}
	
	@Action("Cancel")
	public UserRegistrationPageNavigation cancel() {
		this.getCodebeamerPage().locator("#userForm input[type='submit'][name='_cancel']").click();
		return new UserRegistrationPageNavigation(getCodebeamerPage());
	}

}
