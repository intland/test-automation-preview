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
package com.intland.codebeamer.integration.api.service.user.settings;

public enum UserSetting {
	SHOW_IDS_PREFERENCE(1),
	SHOW_PARAGRAPH_PREFERENCE(2),
	MARK_SUSPECTED_LINKS(3),
	PREFERRED_EDITOR_MODE(4),
	MAXIMUM_ISSUE_COUNT(5),
	SHOW_TEST_CASES_FOR_REQUIREMENTS(6),
	ISSUE_DETAILS_CLOSED(9),
	REQUIREMENT_LIBRARY_TRACKERS(11),
	ISSUE_WIKI_FIELD_SIZES(12),
	VERSION_STATS_TREE_STATE(13),
	PROJECT_BROWSER_PROPERTIES(14),
	PLANNER_LEFT_PANE_WIDTH(15),
	VERSION_STATS_SHOW_RELEASED(16),
	PROJECT_BROWSER_CATEGORY_ORDER(17),
	SHOW_OFFICE_EDIT_INSTRUCTIONS(18),
	EXPIRY_DATE(19),
	PLANNER_ITEM_ACCORDION_STATE(20),
	DOCUMENT_VIEW_ITEM_ACCORDION_STATE(21),
	/**
	 * @since CB-8.2.0
	 */
	@Deprecated
	TRACEABILITY_BROWSER_SHOW_ASSOCIATIONS(22),
	/**
	 * @since CB-8.2.0
	 */
	@Deprecated
	TRACEABILITY_BROWSER_SHOW_RELATIONS(23),
	TRACEABILITY_BROWSER_EXCLUDED_TRACKER_TYPE_IDS(24),
	RELEASE_STATS_AUTO_SAVE_TREE_STATE(25),
	WIKI_TREE_SEARCH_IN(26),
	/**
	 * @since CB-7.6 Support user attribute is no logger supported
	 */
	@Deprecated
	IS_SUPPORT_USER(27),
	TRACEABILITY_BROWSER_SHOW_HIDDEN_TRACKERS(28),
	ENABLE_RATING(29),
	/**
	 * @since CB-8.2.0
	 */
	@Deprecated
	TRACEABILITY_BROWSER_ENABLE_D_N_D(30),
	PROJECT_BROWSER_DEFAULT_TAB(31),
	TESTRUNNER_REPORT_BUG_DEFAULT_TRACKER_ID(33),    // The id of the tracker selected to report bugs during test runs
	COVERAGE_BROWSER_TREE_WIDTH(34),        // an Integer stores the coverage browser's first column width if that is resized
	/**
	 * @since CB-8.2.0
	 */
	@Deprecated
	TRACEABILITY_BROWSER_EXCLUDE_FOLDERS(36),
	PLANNER_TRACKER_FILTER(37),
	RELEASE_STATS_FILTER_PANEL_STATE(38),
	DEFAULT_TRACKER_VIEWS(39),
	TRACKER_ITEM_RELATIONS_EXCLUDED_TRACKER_TYPE_IDS(40),
	TRACKER_ITEM_RELATIONS_DIRECTION(41),
	WORD_IMPORT_OPTIONS(42),
	TESTRUNNER_WINDOW_DIMENSIONS(
			43),    // the saved location/size for the TestRunner window, see keepWindowDimensions(...) js function
	/**
	 * @since CB-8.2.0
	 */
	@Deprecated
	TRACEABILITY_BROWSER_SHOW_OUTGOING_ASSOCIATIONS(44),
	/**
	 * @since CB-8.2.0
	 */
	@Deprecated
	TRACEABILITY_BROWSER_SHOW_OUTGOING_RELATIONS(45),
	ACTIVATION_PARAMS(46),
	/**
	 * @since CB-10.0.0
	 */
	@Deprecated
	LIBRARY_TREE_FILTERS(47),
	WIKI_RECENTLY_UPDATED(48),
	WIKI_OUTLINERS(49),
	RELEASE_GANTT_CHART_HEIGHT(50),
	DOCUMENT_VIEW_TEMPORARY_ROOT(51),
	TRACKER_ITEM_RELATIONS_VIEW_ID(53),
	PASSWORD_RESET(54),
	WYSIWYG_PASTE_AS_TEXT(
			55),        /* boolean value (true/false): if the wysiwyg will use paste-as-text flag, defaults to false */
	DOCUMENT_VIEW_COMPACT_MODE(56), /* if true the doc view will hide empty summaries */
	/**
	 * @since CB-10.1 SP1 Suggestions of the main page will always appear
	 */
	@Deprecated
	TRACEABILITY_BROWSER_SHOW_SUGGESTIONS(57),
	/**
	 * @since CB-8.2.0 The new Traceability Tab (instead of the Relations box) is always closed by default.
	 */
	@Deprecated
	TRACKER_ITEM_RELATIONS_OPEN(58),
	LAST_SELECTED_QUERY_ID(59),
	SHOW_COUNTS(60),
	TRACKER_CONFIGURATION_DIAGRAM_SELECTED_TRACKERS(61),
	TRACKER_HOME_PAGE_CONFIGURATION(62),

	/**
	 * @since CB-8.2
	 * @deprecated since Gina
	 */
	@Deprecated
	DOORS_BRIDGE_LOGIN(63),
	SHOW_BRANCH_BADGES(64),
	SYNCHRONIZE_TREE(65),
	ADVANCED_SEARCH_CONFIGURATION(66),

	/**
	 * @since CB-9.0
	 * @deprecated since Gina
	 */
	@Deprecated
	JIRA_SERVER_LOGIN(67),
	SLACK_USER_ID(2001),
	SLACK_USER_TOKEN(2002),

	/**
	 * @since CB-9.1
	 */
	PLANNER_SHOW_PARENTS(68),

	/**
	 * @since CB-9.2
	 */
	RELEASE_DASHBOARD_BURNDOWN_CONFIGURATION(69),

	/**
	 * the inheritance mode used when creating new branches
	 *
	 * @since cb-9.2
	 */
	BRANCH_PERMISSION_INHERITANCE_MODE(70),

	/**
	 * States of collapsed groups on Planner
	 *
	 * @since CB-9.3
	 */
	PLANNER_GROUP_CLOSED_STATES(71),
	PLANNER_NO_GROUP_CLOSED_STATES(72),

	/**
	 * New settings in the User Preferences dialog
	 *
	 * @since CB-9.3
	 */
	NEW_BROWSER_WINDOW_TARGET(73), // flag for opening window in new browser tab or same tab

	ALWAYS_DISPLAY_CONTEXT_MENU_ICONS(74), // flag for display context menu icons always or only on hover
	OPEN_PLANNER_PRODUCT_BACKLOG(75), // flag for opening the Product Backlog immediately if open Planner
	RECENT_REPORT_FIELDS(76),
	REPORT_FINDER_OPTION_IDS(
			77), // stored option IDs of Report finder overlay (-1 My Reports, 0 All Shared Reports, [projectID] Shared Reports from Projects;
	DOUBLE_CLICK_EDIT_ON_DOC_VIEW(78),
	DOUBLE_CLICK_EDIT_ON_WIKI_SECTION(79),

	/**
	 * Chosen view for Release trackers; accepted values: RELEASE_DASHBOARD, PLANNER
	 *
	 * @since CB-9.3
	 */
	RELEASE_VIEW(81),
	MARK_UNRESOLVED_DEPENDENCIES(82),
	/**
	 * Default value for displaying the relation viewer arrows. When set to true, arrows only appear when the user hovers over the tracker item.
	 *
	 * @since CB-9.4
	 */
	SHOW_TRACKER_ITEM_RELATIONS_VIEWER_CONTROLS_ON_HOVER(83),

	/**
	 * The id of previously selected TestSet tracker where the new TestSets are created
	 *
	 * @since CB-9.4
	 */
	PREVIOUS_TESTSET_TRACKER_ID_FOR_TESTCASE_SELECTION(85),

	COPY_ATTACHMENTS_ON_NEW_REFERRING_ITEM(86),
	SHOW_BRANCH_SWITCH_WARNING(88),
	DISPLAY_REMOVE_ICONS(89),
	TRANSITION_EXECUTION_SETTINGS(90),
	SHOW_SECTION_REVIEW_LINKS(91),
	STICKY_HEADERS(92),
	/**
	 * Work item details user settings (Show description on the top)g
	 *
	 * @since CB-10.0
	 */
	SHOW_DESCRIPTION_ON_TOP(93),
	CUSTOMIZED_TABS(94),
	MOST_RECENT_REFERENCE_HISTORY_SIZE(95),
	REFERENCE_SELECTOR_SHOW_CHILDREN(96),
	/**
	 * Timestamp of latest crucial user data update (user activated state, user type, user name, password)
	 */
	USER_IDENTITY_LAST_UPDATE_TIMESTAMP(97),
	/**
	 * Expiration time of the user's most recently generated JWT token
	 */
	JWT_EXPIRATION_TIMESTAMP(98),
	REFERENCE_SELECTOR_SHOW_ANCESTOR_ITEMS(99),
	REFERENCE_SELECTOR_SHOW_DESCENDANT_ITEMS(100),
	/**
	 * Password hashes and creation dates.
	 */
	PASSWORD_EXPIRATIONS(101),

	/**
	 * user lockout information.
	 */
	USER_LOCKOUT(102),

	CURRENT_WORKINGSET_IDS_PER_PROJECT(103),

	/**
	 * Use remarking feature for the Table View
	 */
	TABLE_VIEW_REMARKING(104),

	DEFAULT_REFERENCE_VIEWS(105),

	SKIPPED_EMAIL_TYPES(106),

	USE_SUMMARY_EMAIL(107),

	SHOW_CBQL_LINT_WARNINGS(108),

	SHOW_WARNINGS_FROM_REPORT_TO_TRACKER(109),

	LAST_OPENED_NOTIFACTION_TIME(110),

	/**
	 * Since CB merge
	 */
	FIRST_LOGIN_USER(111),
	IS_USER_INVITEE(112),
	/**
	 * Splitting ANGULAR_TIMELINE_CONFIG from cbx into following 3 settings.
	 */
	COMMENT_ORDER(113),

	TIMELINE_ORDER(114),

	TIMELINE_FILTERS(115),

	DOCUMENT_TRACKER_DOC_VIEW_TREE_SETTINGS(116),

	DOCUMENT_TRACKER_DOC_VIEW_TREE_SEARCH_SETTINGS(117),

	// we need to keep the setting id for backward compatibility
	ANGULAR_DEFAULT_DASHBOARD(1003),
	BOOKMARKS(1006),
	ANGULAR_DASHBOARD_HOME_ID(1014),

	ANGULAR_NOTIFICATION_FILTER_CONFIG(118),

	ANGULAR_LAST_OPENED_NOTIFICATION_TIME(119),

	ANGULAR_EMAIL_TEMPLATE_PREFERENCE(1018),

	TABLE_PROPERTIES(120),

	FAVORITE_PROJECTS(121),

	GOOGLE_AUTH_CREDENTIALS(122),

	MICROSOFT_AUTH_CREDENTIALS(123),

	PREFERRED_USER_UI_SETTINGS(124),

	DISABLED_WIDGET_IDS(125),

	ANGULAR_MENU_CONFIG(1004);

	private final int id;

	UserSetting(int id) {
		this.id = id;
	}

	public Integer getId() {
		return Integer.valueOf(id);
	}
}
