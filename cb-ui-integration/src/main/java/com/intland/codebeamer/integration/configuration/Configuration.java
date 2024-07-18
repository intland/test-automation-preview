package com.intland.codebeamer.integration.configuration;

public record Configuration(ApplicationConfiguration applicationConfiguration,
		ScreenshotConfiguration screenshotConfiguration, ViewportConfiguration viewportConfiguration, TimeoutConfiguration timeoutConfiguration, TracingConfiguration tracingConfiguration,  DebugConfiguration debugConfiguration) {

}
