/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.classic.page.user.usereditaccount;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.DateFormat;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.TimeZone;
import com.intland.codebeamer.integration.classic.page.user.usereditaccount.component.EditUserProfileFormAssertion;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

import net.datafaker.Faker;

@Test(groups = "UserEditAccountPage")
public class UserEditAccountPageTest extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		if (activeUser != null) {
			getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
		}
	}

	@Test(description = "User is able to visit edit account page")
	public void editAccountPageCanBeLoaded() {
		getClassicCodebeamerApplication(activeUser)
				.visitUserAccountEditPage();
	}

	@Test(description = "Regular User is able to edit account information form")
	public void editAccountFormIsReadyForRegularUser() {
		getClassicCodebeamerApplication(activeUser)
				.visitUserAccountEditPage()
				.assertEditUserProfileFormComponent(EditUserProfileFormAssertion::isFormEditable);
	}

	@Test(description = "User is not able to edit information with invalid data from MyUserAccountPage")
	public void userIsNotAbleToEditAccountWithInvalidDataFromMyUserAccountPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitMyUserAccountPage()
				.getUserAccountActionBarComponent()
				.visitUserAccountEditPage()
				.editUserProfileFormComponent(profileForm -> profileForm.fillOut()
						.userName(StringUtils.EMPTY)
						.firstName(StringUtils.EMPTY)
						.lastName(StringUtils.EMPTY)
						.email("test@mail")
						.phone("123ABC"))
				.save()
				.redirectedToUserEditAccountPage()
				.assertEditUserProfileFormComponent(form -> form
						.hasUserNameError()
						.hasFirstNameError()
						.hasLastNameError()
						.hasEmailError()
						.hasPhoneError());
	}

	@Test(description = "Regular User is able to edit account from MyUserAccountPage")
	public void regularUserIsAbleToEditAccountFromMyUserAccountPage() {
		Faker faker = new Faker();
		getClassicCodebeamerApplication(activeUser)
				.visitMyUserAccountPage()
				.getUserAccountActionBarComponent()
				.visitUserAccountEditPage()
				.editUserProfileFormComponent(profileForm -> profileForm.fillOut()
						.title(faker.name().title())
						.firstName(faker.name().firstName())
						.lastName(faker.name().lastName())
						.email(faker.internet().safeEmailAddress())
						.phone("9876543210")
						.mobile(faker.phoneNumber().cellPhone())
						.company(faker.company().industry())
						.address(faker.address().fullAddress())
						.city(faker.address().cityName())
						.zip(faker.address().zipCode())
						.state(faker.address().state())
						.country(Country.DE)
						.language(Language.DE)
						.skills(faker.hobby().activity())
						.timeZone(TimeZone.UTC)
						.dateFormat(DateFormat.FORMAT11))
				.save()
				.redirectedToMyUserAccountPage();
	}
}
