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

package com.intland.codebeamer.integration.api.user;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Map;
import java.util.Optional;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.api.service.user.Status;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.user.settings.CopyAttachments;
import com.intland.codebeamer.integration.api.service.user.settings.TransitionExecutionType;
import com.intland.codebeamer.integration.api.service.user.settings.UserSetting;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.DateFormat;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.TimeZone;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;

import net.datafaker.Faker;

@Test(groups = "UserApiServiceTest")
public class UserApiServiceTest extends AbstractBaseNGTests {

	private UserApiService userService;

	private User testUser;

	@BeforeMethod
	public void setUp() {
		userService = new UserApiService(getApplicationConfiguration());
		testUser = userService.createUser()
				.addToRegularUserGroup()
				.build();
	}

	@Test(description = "Update user profile details via API")
	public void updateUserDetailsViaApi() {
		Faker faker = new Faker();
		String username = faker.internet().username();
		//WHEN
		userService.updateUserDetails(testUser.getName(), builder -> builder
				.name(username)
				.title("Business Development")
				.email("new@gmail.com")
				.phone("1234567890")
				.mobile("0987654321")
				.status(Status.ACTIVATED)
				.company("New company")
				.address("New Address")
				.city("New city")
				.zip("123456")
				.state("MH")
				.country(Country.IN)
				.language(Language.EN)
				.skills("Java, Angular")
				.dateFormat(DateFormat.FORMAT9)
				.timeZone(TimeZone.ASIA_CALCUTTA));
		//THEN
		Optional<com.intland.swagger.client.model.User> updatedUser = findUserById(testUser.getId());
		assertTrue(updatedUser.isPresent(), "User should be present after update");
		assertEquals(updatedUser.get().getName(), username);
		assertEquals(updatedUser.get().getTitle(), "Business Development");
		assertEquals(updatedUser.get().getFirstName(), testUser.getFirstName());
		assertEquals(updatedUser.get().getLastName(), testUser.getLastName());
		assertEquals(updatedUser.get().getEmail(), "new@gmail.com");
		assertEquals(updatedUser.get().getPhone(), "1234567890");
		assertEquals(updatedUser.get().getMobile(), "0987654321");
		assertNotNull(updatedUser.get().getStatus(), "User status should not be null");
		assertEquals(updatedUser.get().getStatus().name(), Status.ACTIVATED.name());
		assertEquals(updatedUser.get().getCompany(), "New company");
		assertEquals(updatedUser.get().getAddress(), "New Address");
		assertEquals(updatedUser.get().getCity(), "New city");
		assertEquals(updatedUser.get().getZip(), "123456");
		assertEquals(updatedUser.get().getState(), "MH");
		assertEquals(updatedUser.get().getCountry(), Country.IN.name());
		assertEquals(updatedUser.get().getLanguage(), Language.EN.toString().toLowerCase());
		assertEquals(updatedUser.get().getSkills(), "Java, Angular");
		assertEquals(updatedUser.get().getDateFormat(), DateFormat.FORMAT9.getValue());
		assertEquals(updatedUser.get().getTimeZone(), TimeZone.ASIA_CALCUTTA.getValue());
	}

	@Test(description = "Update user preferences via API")
	public void updateUserPreferencesViaApi() {
		//GIVEN
		UserApiService testUserApiService = new UserApiService(getApplicationConfiguration(), testUser.getName());

		//WHEN
		testUserApiService.setUserPreferences(builder ->
				builder.enableOpenLinksInNewBrowserTab()
						.enableAlwaysDisplayContextMenuIcons()
						.enableDoubleClickEditOnWiki()
						.enableShowSectionComments()
						.enableShowUpstreamDownstreamRefArrowsOnHover()
						.enableStickyHeaders()
						.enableDisplayBranchSwitchWarningDialog()
						.setSizeOfReferenceAutocompleteHistory(10)
						.setCopyAttachmentsOnReferringItem(CopyAttachments.DO_NOT_COPY)
						.setTrackerTransitionExecutionSettings(TransitionExecutionType.USE_QUICK_TRANSITIONS)
						.setPreferredUISettings("CBX")
		);

		//THEN
		Map<UserSetting, String> userSettings = testUserApiService.getUserSettings();
		assertTrue(Boolean.parseBoolean(userSettings.get(UserSetting.NEW_BROWSER_WINDOW_TARGET)));
		assertTrue(Boolean.parseBoolean(userSettings.get(UserSetting.ALWAYS_DISPLAY_CONTEXT_MENU_ICONS)));
		assertTrue(Boolean.parseBoolean(userSettings.get(UserSetting.DOUBLE_CLICK_EDIT_ON_WIKI_SECTION)));
		assertTrue(Boolean.parseBoolean(userSettings.get(UserSetting.SHOW_SECTION_REVIEW_LINKS)));
		assertTrue(Boolean.parseBoolean(userSettings.get(UserSetting.SHOW_TRACKER_ITEM_RELATIONS_VIEWER_CONTROLS_ON_HOVER)));
		assertTrue(Boolean.parseBoolean(userSettings.get(UserSetting.STICKY_HEADERS)));
		assertTrue(Boolean.parseBoolean(userSettings.get(UserSetting.SHOW_BRANCH_SWITCH_WARNING)));
		assertEquals(Integer.valueOf(userSettings.get(UserSetting.MOST_RECENT_REFERENCE_HISTORY_SIZE)), 10);
		assertEquals(Integer.valueOf(userSettings.get(UserSetting.COPY_ATTACHMENTS_ON_NEW_REFERRING_ITEM)),
				Integer.valueOf(CopyAttachments.DO_NOT_COPY.getValue()));
		assertEquals(userSettings.get(UserSetting.TRANSITION_EXECUTION_SETTINGS),
				TransitionExecutionType.USE_QUICK_TRANSITIONS.name());
		assertEquals(userSettings.get(UserSetting.PREFERRED_USER_UI_SETTINGS), "CBX");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		userService.disableUser(testUser);
	}
}
