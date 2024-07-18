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

package com.intland.codebeamer.integration.classic.component.field.read;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.intland.codebeamer.integration.api.service.Member;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class RoleAndMemberSelectorAssertions
		extends AbstractCodebeamerComponentAssert<RoleAndMemberSelectorComponent, RoleAndMemberSelectorAssertions> {

	protected RoleAndMemberSelectorAssertions(RoleAndMemberSelectorComponent component) {
		super(component);
	}

	public RoleAndMemberSelectorAssertions contains(Member... members) {
		String memberAsString = Arrays.stream(members).map(Member::toString).collect(Collectors.joining(","));
		return assertAll("The fields should contains the following items: %s".formatted(memberAsString),
				() -> {
					assertThat(getComponent().getMemberContainer()).isAttached();
					MatcherAssert.assertThat(getComponent().getMembers(), Matchers.hasItems(members));
				});
	}

	public RoleAndMemberSelectorAssertions sameAs(Member... members) {
		String memberAsString = Arrays.stream(members).map(Member::toString).map(String::valueOf)
				.collect(Collectors.joining(","));
		return assertAll("The field should contains the following items in the following order: %s".formatted(memberAsString),
				() -> {
					assertThat(getComponent().getMemberContainer()).isAttached();
					MatcherAssert.assertThat(getComponent().getMembers(), Matchers.contains(members));
				});
	}

}
