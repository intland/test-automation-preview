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

package com.intland.codebeamer.integration.api.applicationconfiguration;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.applicationconfiguration.ApplicationConfigurationRestApiService;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;
import com.intland.swagger.client.model.ApplicationConfiguration;
import com.intland.swagger.client.model.ApplicationConfigurationResponse;

@Test(groups = "ApplicationConfigurationRestApiService")
public class ApplicationConfigurationRestApiServiceTest extends AbstractBaseNGTests {

	private static final Logger logger = LogManager.getLogger(ApplicationConfigurationRestApiServiceTest.class);

	ApplicationConfigurationRestApiService applicationConfigApiService;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		applicationConfigApiService = new ApplicationConfigurationRestApiService(getApplicationConfiguration());
	}

	@Test(description = "To get application configuration")
	public void testGetApplicationConfiguration() {
		try {
			ApplicationConfigurationResponse configResponseModel = applicationConfigApiService.getApplicationConfiguration();
			assertNotNull(configResponseModel);
			assertNotNull(configResponseModel.getConfigurationPageModel());
			assertNotNull(configResponseModel.getConfigurationPageModel().getConfiguration());
			assertNotNull(configResponseModel.getConfigurationPageModel().getConfigurations());
			assertFalse(configResponseModel.getConfigurationPageModel().getConfigurations().isEmpty());
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertTrue(Pattern.matches("Application Configuration not found", e.getMessage()));
		}
	}

	@Test(description = "To get application configuration when version id is provided")
	public void testGetApplicationConfigurationWIthVersionId() {
		try {
			ApplicationConfigurationResponse configResponseModel = applicationConfigApiService.getApplicationConfiguration(1);
			assertNotNull(configResponseModel);
			assertNotNull(configResponseModel.getConfigurationPageModel());
			assertNotNull(configResponseModel.getConfigurationPageModel().getConfiguration());
			assertNotNull(configResponseModel.getConfigurationPageModel().getConfigurations());
			assertFalse(configResponseModel.getConfigurationPageModel().getConfigurations().isEmpty());
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertTrue(Pattern.matches("Application Configuration not found", e.getMessage()));
		}
	}

	@Test(description = "To Save new config in application configuration")
	public void testSaveApplicationConfiguration() {
		try {
			String configurationString = applicationConfigApiService.getConfigurationString("sampleTag", "value");
			applicationConfigApiService.saveApplicationConfiguration(configurationString);

			ApplicationConfigurationResponse applicationConfigResponse = applicationConfigApiService.getApplicationConfiguration();
			assertNotNull(applicationConfigResponse.getConfigurationPageModel());
			assertNotNull(applicationConfigResponse.getConfigurationPageModel().getConfiguration());
			ApplicationConfiguration updatedConfig = applicationConfigResponse.getConfigurationPageModel().getConfiguration();

			assertNotNull(updatedConfig);
			assertNotNull(updatedConfig.getConfiguration());
			assertTrue(updatedConfig.getConfiguration().contains("sampleTag"));
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	@Test(description = "To set application configuration parameter")
	public void testSetApplicationConfigParameter() {
		try {
			String updatedConfig = applicationConfigApiService.setApplicationConfigParameter("mail.props.prop.text",
					"NewTestValue");
			assertNotNull(updatedConfig);
			assertTrue(updatedConfig.contains("NewTestValue"));
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	@Test(description = "To get configuration value when tag is provided")
	public void testGetApplicationConfigParameter() {
		try {
			String updatedConfig = applicationConfigApiService.getApplicationConfigParameter("mail.props.prop.text");
			assertNotNull(updatedConfig);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
}
