package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import java.util.regex.Pattern;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerItemHistoryAssertions
		extends AbstractCodebeamerComponentAssert<TrackerItemHistoryComponent, TrackerItemHistoryAssertions> {

	protected TrackerItemHistoryAssertions(TrackerItemHistoryComponent component) {
		super(component);
	}

	public TrackerItemHistoryAssertions isSubmittedBy(final UserId userId) {
		return assertAll("Submitter should be the user with id: %d".formatted(userId.id()), () ->
			MatcherAssert.assertThat(getComponent().getSubmitter(), Matchers.equalTo(userId))
		);
	}

	public TrackerItemHistoryAssertions isSubmittedBy(final String userName) {
		return assertAll("Submitter should be the user with name: %s".formatted(userName), () -> {
			User user = getComponent().getCodebeamerPage().getDataManagerService().getUserApiServiceWithConfigUser()
					.findUserByName(userName);
			MatcherAssert.assertThat(getComponent().getSubmitter(), Matchers.equalTo(user.getUserId()));
		});
	}


	public TrackerItemHistoryAssertions versionEqualsTo(final Integer version) {
		return assertAll("Version should be %d".formatted(version), () ->
			assertThat(getComponent().getVersion()).hasText(version.toString()));
	}

	public TrackerItemHistoryAssertions actionEqualsTo(final String action) {
		return assertAll("Action should be %s".formatted(action), () ->
			assertThat(getComponent().getAction()).hasText(action));
	}

	public TrackerItemHistoryAssertions hasNumberOfFieldChanges(final int fieldChangeNumber) {
		return assertAll("There should %s field changes".formatted(Integer.valueOf(fieldChangeNumber)), () ->
			assertThat(getComponent().getFieldChangesLocator()).hasCount(fieldChangeNumber));
	}

	public TrackerItemHistoryAssertions hasFieldChange(final String fieldName) {
		return assertAll("There should be a field change for %s field".formatted(fieldName), () ->
			MatcherAssert.assertThat(getComponent().getFieldChanges().get(fieldName), Matchers.notNullValue())
		);
	}

	public TrackerItemHistoryAssertions hasFieldChangeWithNewValue(final String fieldName, final String newValue) {
		return hasFieldChange(fieldName)
			.assertAll("There should be a field change for %s field with new value: %s".formatted(fieldName, newValue), () ->
				assertThat(getComponent().getFieldChanges().get(fieldName).newValue()).hasText(newValue));
	}

	public TrackerItemHistoryAssertions hasFieldChangeWithOldAndNewValue(final String fieldName, final String oldValue, final String newValue) {
		return hasFieldChangeWithNewValue(fieldName, newValue)
			.assertAll("There should be a field change for %s field with old value: %s".formatted(fieldName, oldValue), () ->
				assertThat(getComponent().getFieldChanges().get(fieldName).oldValue()).hasText(oldValue));
	}

	public TrackerItemHistoryAssertions isBaselineCreationIndicator(final String baselineName) {
		return assertAll("Version indicator should show creation of baseline: %s".formatted(baselineName),
			() -> assertThat(getComponent().getVersionIndicatorText()).hasText(Pattern.compile("%s.*".formatted(baselineName)))
		);
	}

}
