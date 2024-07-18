package com.intland.codebeamer.integration.classic.page.userregistration.component;

import java.util.List;

public class UserRegistrationFormBuilder {

	// TOOD Should be ENUM
	private static final List<String> SUPPORTED_TZ = List.of("Pacific/Midway", "HST", "US/Alaska",
			"America/Los_Angeles", "America/Denver", "America/Chicago", "America/New_York", "America/Puerto_Rico",
			"US/East-Indiana", "America/Sao_Paulo", "America/Argentina/Buenos_Aires", "Canada/Atlantic",
			"Canada/Newfoundland", "Atlantic/Cape_Verde", "UTC", "Europe/London", "Europe/Berlin", "Europe/Moscow",
			"Asia/Riyadh", "Africa/Cairo", "Asia/Istanbul", "Asia/Tehran", "Asia/Dubai", "Asia/Karachi",
			"Asia/Calcutta", "Asia/Dacca", "Asia/Bangkok", "Asia/Shanghai", "Australia/Perth", "Asia/Tokyo",
			"Asia/Seoul", "Australia/Darwin", "Australia/Sydney", "SST", "NST");

	// TOOD Should be ENUM
	private static final List<String> SUPPORTED_DATA_FORMATS = List.of("MMM dd yyyy", "MMM dd, yy", "MMM dd, yyyy",
			"dd/MM/yy", "dd/MM/yyyy", "dd MMM yyyy", "yyyy/MM/dd", "EEEE, MMMM d, yyyy", "dd. MMM. yyyy", "dd. MMM. yy",
			"dd.MM.yyyy", "dd.MM.yy", "MMMM d, yyyy", "dd. MMMM yyyy", "EEE, MMM d, yy", "EEE d/MM yy",
			"EEE MMMM d, yyyy", "MM-dd", "yy-MM-dd", "yyyy-MM-dd", "yyyy.MM.dd", "dd-MMM-yyyy", "dd-MMM-yy", "MMM dd",
			"MMM/dd/yyyy", "MM/dd/yyyy");

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
