package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.intland.codebeamer.integration.api.service.Member;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;
import com.intland.codebeamer.integration.classic.component.model.MemberReference;
import com.intland.codebeamer.integration.classic.component.model.RoleReference;
import com.intland.codebeamer.integration.classic.component.model.TrackerFieldReference;
import com.intland.codebeamer.integration.classic.component.model.UserReference;

public abstract class AbstractEditRoleAndMemberSelectorAssertions<C extends AbstractEditRoleAndMemberSelectorComponent<C, A>, A extends AbstractEditRoleAndMemberSelectorAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditRoleAndMemberSelectorAssertions(C component) {
		super(component);
	}

	public A hasHiddenValueField() {
		return assertAll("The fields should contains the following items: %s",
				() -> assertThat(getComponent().getValueHiddenField()).isAttached());
	}
	
	public A contains(Member... members) {
		return assertAll("The fields should contains the following items: %s".formatted(convertToString(members)),
				() -> assertThat(m -> Matchers.hasItems(m), members));
	}

	public A sameAs(Member... members) {
		return assertAll(
				"The field should be the same the following items in the following order: %s".formatted(convertToString(members)),
				() -> assertThat(m -> Matchers.contains(m), members));
	}

	private String convertToString(Member... members) {
		return Arrays.stream(members)
				.map(Member::toString)
				.collect(Collectors.joining(","));
	}

	private void assertThat(Function<MemberReference[], Matcher> f, Member... members) {
		assertThat(getComponent().getValueContainerElement()).isAttached();
		MatcherAssert.assertThat(getComponent().getMemberReferences(), f.apply(convertToMemberReferences(members)));
	}

	private MemberReference[] convertToMemberReferences(Member[] members) {
		return Arrays.stream(members).map(m -> {
			switch (m.getArtifactType()) {
			case USER_ACCOUNT: {
				return new UserReference(m.getName());
			}
			case ROLE: {
				return new RoleReference(m.getName());
			}
			case TRACKER: {
				return new TrackerFieldReference(m.getName());
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + m.getArtifactType());
			}
		}).toArray(MemberReference[]::new);
	}

}
