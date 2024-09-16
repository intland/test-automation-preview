package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import java.util.function.Function;

public record FieldLocators(Function<String, String> readFieldLocator,
							Function<String, String> editFieldLocator,
							Function<String, String> inlineEditFieldLocator) {
}
