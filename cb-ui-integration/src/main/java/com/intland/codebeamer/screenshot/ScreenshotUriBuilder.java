package com.intland.codebeamer.screenshot;

import java.net.URI;

@FunctionalInterface
public interface ScreenshotUriBuilder {

	URI build(String randomFileName);

}
