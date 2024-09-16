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

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.Member;
import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.api.service.role.RoleId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class RoleAndMemberSelectorComponent
		extends AbstractCodebeamerComponent<RoleAndMemberSelectorComponent, RoleAndMemberSelectorAssertions>
		implements InlineEditable<RoleAndMemberSelectorComponent> {

	private static final String ROLE_PATTERN = "\\[ROLE:(\\d+)\\] (\\w+)";

	private static final String USER_PATTERN = "\\[USER:(\\d+)\\]";

	public RoleAndMemberSelectorComponent(CodebeamerPage codebeamerPage, String fieldLocator) {
		super(codebeamerPage, fieldLocator);
	}
	
	public List<Member> getMembers() {
		return getMemberElements().all().stream().map(this::getMember)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(Member.class::cast)
				.toList();
	}

	public CodebeamerLocator getMemberElements() {
		return this.locator(" a[data-wikilink^='[USER'],span[title^='[ROLE']");
	}

	@Override
	public CodebeamerLocator getValueElement() {
		return this.locator(" div.choice-field-wrapper");
	}

	@Override
	public RoleAndMemberSelectorAssertions assertThat() {
		return new RoleAndMemberSelectorAssertions(this);
	}

	private Optional<User> getUser(String attribute) {
		Pattern pattern = Pattern.compile(USER_PATTERN);
		Matcher matcher = pattern.matcher(attribute);

		if (matcher.find()) {
			return Optional.of(new User(Integer.parseInt(matcher.group(1))));
		}

		return Optional.empty();
	}

	private Optional<Role> getRole(String attribute) {
		Pattern pattern = Pattern.compile(ROLE_PATTERN);
		Matcher matcher = pattern.matcher(attribute);

		if (matcher.find()) {
			return Optional.of(new Role(new RoleId(Integer.parseInt(matcher.group(1))), matcher.group(2)));
		}

		return Optional.empty();
	}

	private Optional<? extends Member> getMember(CodebeamerLocator locator) {
		String dataWikiLink = locator.getAttribute("data-wikilink");
		String title = locator.getAttribute("title");

		if (dataWikiLink != null) {
			return getUser(dataWikiLink);
		}

		if (title != null) {
			return getRole(title);
		}
		return Optional.empty();
	}

}
