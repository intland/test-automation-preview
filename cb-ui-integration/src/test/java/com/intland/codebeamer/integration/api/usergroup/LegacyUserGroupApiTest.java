package com.intland.codebeamer.integration.api.usergroup;

import static com.intland.codebeamer.integration.api.service.usergroup.UserGroupPermission.SYSTEM_ADMIN;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.legacy.LegacyApiException;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroup;
import com.intland.codebeamer.integration.api.service.usergroup.legacy.LegacyUserGroupApi;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;

@Test(groups = "LegacyUserGroupApi")
public class LegacyUserGroupApiTest extends AbstractBaseNGTests {
	
	@Test(description = "Group can be created via API")
	public void createNewGroupViaApi() {
		// Given
		LegacyUserGroupApi legacyUserGroupApi = new LegacyUserGroupApi(getApplicationConfiguration());

		// When
		UserGroup group = legacyUserGroupApi.createUserGroup(getRandomText("MySystemAdminGroup"),
				"MySystemAdminGroup Description", SYSTEM_ADMIN);
		
		// Then
		assertNotNull(group, "GroupId cannot be null");
	}

	@Test(description = "Group can be created via API")
	public void exceptionThrownWhenGroupsExistViaApi() {
		// Given
		String groupName = getRandomText("MySystemAdminGroup");
		LegacyUserGroupApi legacyUserGroupApi = new LegacyUserGroupApi(getApplicationConfiguration());
		legacyUserGroupApi.createUserGroup(groupName, "MySystemAdminGroup Description", SYSTEM_ADMIN);
		
		// When
		try {
			legacyUserGroupApi.createUserGroup(groupName, "MySystemAdminGroup Description", SYSTEM_ADMIN);
			fail("Exception must be thrown");
		} catch (Exception e) {
			// Then
			assertTrue(e instanceof LegacyApiException, "Api must be LegacyApiException");
			assertTrue(((LegacyApiException) e).isArtifactNameConflictException(),
					"Root cause must be a ArtifactNameConflictException");
		}
	}
	
}
