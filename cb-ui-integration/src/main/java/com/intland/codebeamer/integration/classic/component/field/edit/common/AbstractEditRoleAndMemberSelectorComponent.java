package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.util.List;
import java.util.stream.IntStream;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.common.dialog.EditRoleAndMemberDialogComponent;
import com.intland.codebeamer.integration.classic.component.model.MemberReference;
import com.intland.codebeamer.integration.classic.component.model.RoleReference;
import com.intland.codebeamer.integration.classic.component.model.TrackerFieldReference;
import com.intland.codebeamer.integration.classic.component.model.UserReference;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public abstract class AbstractEditRoleAndMemberSelectorComponent<C extends AbstractEditRoleAndMemberSelectorComponent<C, A>, A extends AbstractEditRoleAndMemberSelectorAssertions<C, A>>
		extends AbstractEditFieldComponent<C, A> {

	@Component(value = "Edit role and members dialog", includeInSitemap = false)
	protected EditRoleAndMemberDialogComponent roleAndMemberDialogComponent;

	public AbstractEditRoleAndMemberSelectorComponent(CodebeamerPage codebeamerPage, String selector) {
		super(codebeamerPage, selector);
		this.roleAndMemberDialogComponent = new EditRoleAndMemberDialogComponent(codebeamerPage);
	}

	public AbstractEditRoleAndMemberSelectorComponent(CodebeamerPage codebeamerPage, String selector, String frameLocator) {
		super(codebeamerPage, frameLocator, selector);
		this.roleAndMemberDialogComponent = new EditRoleAndMemberDialogComponent(codebeamerPage);
	}

	public CodebeamerLocator getElement(MemberReference member) {
		getValueContainerElement().waitForAttached();
		return getValueContainerElement().concat("li:has(p:text-is('%s'))".formatted(member.name()));
	}

	public CodebeamerLocator getRemoveElement(MemberReference member) {
		return getElement(member).concat("span.token-input-delete-token-facebook");
	}

	public CodebeamerLocator getValueHiddenField() {
		return this.locator("input[type='hidden'][id^='dynamicChoice_references'][value]");
	}

	public CodebeamerLocator getValueContainerElement() {
		return this.locator("ul.token-input-list-facebook");
	}

	public CodebeamerLocator getValueField() {
		return this.locator("ul li input[type=text]");
	}

	public CodebeamerLocator getValueElement() {
		return this.locator("ul li:has(p.name)");
	}

	protected void doSelectMember(MemberReference member) {
		if (member instanceof UserReference) {
			this.roleAndMemberDialogComponent.selectUser(member.name());
		} else if (member instanceof RoleReference) {
			this.roleAndMemberDialogComponent.selectRole(member.name());
		} else if (member instanceof TrackerFieldReference) {
			this.roleAndMemberDialogComponent.selectTrackerField(member.name());
		} else {
			throw new IllegalStateException("%s is not supported".formatted(member.getClass().getName()));
		}
	}

	protected List<MemberReference> getMemberReferences() {
		assertThat().hasHiddenValueField();
		String[] members = getValueHiddenField()
				.getAttribute("value").split(",");

		List<CodebeamerLocator> valueElements = getValueElement().all();
		return IntStream.range(0, members.length)
				.mapToObj(i -> extractMemberReference(i, members[i], valueElements.get(i)))
				.toList();
	}

	private MemberReference extractMemberReference(int i, String member, CodebeamerLocator locator) {
		String[] ids = member.trim().split("-");
		ArtifactType type = ArtifactType.findById(Integer.valueOf(ids[0]).intValue());
		String name = locator.concat("p.name").text();

		return switch (type) {
			case USER_ACCOUNT -> new UserReference(name);
			case ROLE -> new RoleReference(name);
			case TRACKER -> new TrackerFieldReference(name);
			default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
	}

}
