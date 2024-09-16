package com.intland.codebeamer.integration.api.service.user;

import java.util.HashMap;
import java.util.Map;

import com.intland.codebeamer.integration.api.service.user.settings.CopyAttachments;
import com.intland.codebeamer.integration.api.service.user.settings.TransitionExecutionType;
import com.intland.codebeamer.integration.api.service.user.settings.UserSetting;

public class UserPreferenceBuilder {

	private final Map<UserSetting, String> preferences;

	public UserPreferenceBuilder() {
		preferences = new HashMap<>();
	}

	public UserPreferenceBuilder enableOpenLinksInNewBrowserTab() {
		return openLinksInNewBrowserTab(true);
	}

	public UserPreferenceBuilder disableOpenLinksInNewBrowserTab() {
		return openLinksInNewBrowserTab(false);
	}

	public UserPreferenceBuilder enableAlwaysDisplayContextMenuIcons() {
		return alwaysDisplayContextMenuIcons(true);
	}

	public UserPreferenceBuilder disableAlwaysDisplayContextMenuIcons() {
		return alwaysDisplayContextMenuIcons(false);
	}

	public UserPreferenceBuilder enableDoubleClickEditOnWiki() {
		return doubleClickEditOnWiki(true);
	}

	public UserPreferenceBuilder disableDoubleClickEditOnWiki() {
		return doubleClickEditOnWiki(false);
	}

	public UserPreferenceBuilder enableShowSectionComments() {
		return showSectionComments(true);
	}

	public UserPreferenceBuilder disableShowSectionComments() {
		return showSectionComments(false);
	}

	public UserPreferenceBuilder enableShowUpstreamDownstreamRefArrowsOnHover() {
		return showUpstreamDownstreamRefArrowsOnHover(true);
	}

	public UserPreferenceBuilder disableShowUpstreamDownstreamRefArrowsOnHover() {
		return showUpstreamDownstreamRefArrowsOnHover(false);
	}

	public UserPreferenceBuilder enableStickyHeaders() {
		return stickyHeaders(true);
	}

	public UserPreferenceBuilder disableStickyHeaders() {
		return stickyHeaders(false);
	}

	public UserPreferenceBuilder enableDisplayBranchSwitchWarningDialog() {
		return displayBranchSwitchWarningDialog(true);
	}

	public UserPreferenceBuilder disableDisplayBranchSwitchWarningDialog() {
		return displayBranchSwitchWarningDialog(false);
	}

	public UserPreferenceBuilder setSizeOfReferenceAutocompleteHistory(int sizeOfReferenceAutocompleteHistory) {
		preferences.put(UserSetting.MOST_RECENT_REFERENCE_HISTORY_SIZE, String.valueOf(sizeOfReferenceAutocompleteHistory));
		return this;
	}

	public UserPreferenceBuilder setCopyAttachmentsOnReferringItem(CopyAttachments copyAttachmentsOnReferringItem) {
		preferences.put(UserSetting.COPY_ATTACHMENTS_ON_NEW_REFERRING_ITEM,
				String.valueOf(copyAttachmentsOnReferringItem.getValue()));
		return this;
	}

	public UserPreferenceBuilder setTrackerTransitionExecutionSettings(TransitionExecutionType transitionExecutionType) {
		preferences.put(UserSetting.TRANSITION_EXECUTION_SETTINGS, transitionExecutionType.name());
		return this;
	}

	public UserPreferenceBuilder setPreferredUISettings(String preferredUISettings) {
		preferences.put(UserSetting.PREFERRED_USER_UI_SETTINGS, preferredUISettings);
		return this;
	}

	public com.intland.swagger.client.model.UserSetting build() {
		com.intland.swagger.client.model.UserSetting userSetting = new com.intland.swagger.client.model.UserSetting();
		for (Map.Entry<UserSetting, String> entry : preferences.entrySet()) {
			userSetting.putSettingsItem(String.valueOf(entry.getKey().getId()), entry.getValue());
		}
		return userSetting;
	}

	private UserPreferenceBuilder openLinksInNewBrowserTab(boolean openLinksInNewBrowserTab) {
		preferences.put(UserSetting.NEW_BROWSER_WINDOW_TARGET, String.valueOf(openLinksInNewBrowserTab));
		return this;
	}

	private UserPreferenceBuilder alwaysDisplayContextMenuIcons(boolean alwaysDisplayContextMenuIcons) {
		preferences.put(UserSetting.ALWAYS_DISPLAY_CONTEXT_MENU_ICONS, String.valueOf(alwaysDisplayContextMenuIcons));
		return this;
	}

	private UserPreferenceBuilder doubleClickEditOnWiki(boolean doubleClickEditOnWiki) {
		preferences.put(UserSetting.DOUBLE_CLICK_EDIT_ON_WIKI_SECTION, String.valueOf(doubleClickEditOnWiki));
		return this;
	}

	private UserPreferenceBuilder showSectionComments(boolean showSectionComments) {
		preferences.put(UserSetting.SHOW_SECTION_REVIEW_LINKS, String.valueOf(showSectionComments));
		return this;
	}

	private UserPreferenceBuilder showUpstreamDownstreamRefArrowsOnHover(boolean showControls) {
		preferences.put(UserSetting.SHOW_TRACKER_ITEM_RELATIONS_VIEWER_CONTROLS_ON_HOVER, String.valueOf(showControls));
		return this;
	}

	private UserPreferenceBuilder stickyHeaders(boolean stickyHeaders) {
		preferences.put(UserSetting.STICKY_HEADERS, String.valueOf(stickyHeaders));
		return this;
	}

	private UserPreferenceBuilder displayBranchSwitchWarningDialog(boolean showBranchSwitchWarning) {
		preferences.put(UserSetting.SHOW_BRANCH_SWITCH_WARNING, String.valueOf(showBranchSwitchWarning));
		return this;
	}
}
