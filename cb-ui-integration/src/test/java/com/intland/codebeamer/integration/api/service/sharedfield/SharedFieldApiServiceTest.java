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

package com.intland.codebeamer.integration.api.service.sharedfield;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertThrows;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.FieldType;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;

@Test(groups = "SharedFieldApiServiceTest")
public class SharedFieldApiServiceTest extends AbstractBaseNGTests {

	private static final String SHARED_FIELD_NAME = "my_shared_field";

	private static final String DISPLAY_NAME = "Display Name";

	private static final String DESCRIPTION = "My Description";

	private SharedFieldApiService sharedFieldApiService;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		sharedFieldApiService = new SharedFieldApiService(getApplicationConfiguration());
	}

	@Test(description = "Shared field can be created via API")
	public void testCreatingSharedField() {
		sharedFieldApiService.createSharedField(SHARED_FIELD_NAME, builder -> builder
				.displayName(DISPLAY_NAME)
				.description(DESCRIPTION)
				.referenceFieldWithUserType());

		SharedField sharedField = sharedFieldApiService.getSharedFieldByName(SHARED_FIELD_NAME);

		assertEquals(sharedField.name(), SHARED_FIELD_NAME);
		assertEquals(sharedField.fieldType(), FieldType.REFERENCE);
		assertEquals(sharedField.displayName(), DISPLAY_NAME);
		assertEquals(sharedField.referenceType(), ArtifactType.USER_ACCOUNT);
		assertEquals(sharedField.description(), DESCRIPTION);
		assertNull(sharedField.memberType());
		assertNotNull(sharedField.id());
		sharedFieldApiService.deleteSharedFieldByName(SHARED_FIELD_NAME);
	}

	@Test(description = "Shared field can be deleted via API")
	public void testDeletingSharedField() {
		sharedFieldApiService.createSharedField(SHARED_FIELD_NAME, builder -> builder
				.displayName(DISPLAY_NAME)
				.referenceFieldWithProjectType());

		sharedFieldApiService.deleteSharedFieldByName(SHARED_FIELD_NAME);

		assertThrows(IllegalStateException.class, () -> sharedFieldApiService.getSharedFieldByName(SHARED_FIELD_NAME));
	}

}