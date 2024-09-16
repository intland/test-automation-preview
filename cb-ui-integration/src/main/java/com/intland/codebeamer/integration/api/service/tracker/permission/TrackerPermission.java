package com.intland.codebeamer.integration.api.service.tracker.permission;

import java.util.List;

/**
 * Naming and ordering must be the same as in TrackerPermission.java in cb.main
 */
public enum TrackerPermission {

	// You must not remove or change order of permissions, you can only add new permissions at the end !!

	ISSUE_VIEW_NOT_OWN,

	/** On UI: Item - Edit Any */
	ISSUE_EDIT_NOT_OWN,
	ISSUE_VIEW,
	ISSUE_ADD,
	ISSUE_EDIT,
	ISSUE_MASS_EDIT,
	ISSUE_CLOSE,
	ISSUE_DELETE,
	ISSUE_HISTORY_VIEW,

	/** Permission to view issue escalation schedules in a tracker*/
	ISSUE_ESCALATION_VIEW,

	/** Permission to view issue comments or attachments in a tracker*/
	ISSUE_ATTACHMENT_VIEW,

	/** Permission to add comments to issues in a tracker*/
	ISSUE_COMMENT_ADD,

	/** Permission to add attachments to issues in a tracker */
	ISSUE_ATTACHMENT_ADD,

	/** Permission to edit/delete any issue comments or attachments in a tracker */
	ISSUE_ATTACHMENT_EDIT,

	/** Permission to edit/delete own issue comments or attachments in a tracker */
	ISSUE_ATTACHMENT_EDIT_OWN,

	/** Permission to subscribe notifications on a single tracker item */
	ISSUE_SUBSCRIBE,

	/** Permission to manage subscriptions of other users for notifications on a single tracker item */
	ISSUE_SUBSCRIBE_OTHERS,

	/** Permission to subscribe notifications on the whole tracker */
	TRACKER_SUBSCRIBE,

	/** Permission to manage subscriptions of other users for notifications on the whole tracker */
	TRACKER_SUBSCRIBE_OTHERS,

	/** Permission to view subscriptions of other users for notifications on the whole tracker */
	TRACKER_SUBSCRIBERS_VIEW,

	/** Allows to administrate (create, update and delete) public views and item templates on the tracker */
	ADMIN_PUBLIC_VIEW,

	/** Allows to see tracker item associations */
	ISSUE_ASSOCIATION_VIEW,

	/** Allows to edit (create, update and delete) tracker item associations */
	ISSUE_ASSOCIATION_EDIT,

	/** Allows to merge tracker item */
	ISSUE_SUSPECTED_MERGE,

	BRANCH_MERGE,

	/** Item - Export permission */
	EXPORT,

	/** Permission to replace branch content by using working set update api */
	BRANCH_REPLACE_CONTENT,

	/** Permission to handle data administration tasks e.g. reqif import */
	DATA_ADMINISTRATION;

	/** Get the minimum permissions a project admin should have on any tracker in his project */
	public static List<TrackerPermission> getProjectAdminAccess() {
		return List.of(ISSUE_VIEW, ISSUE_VIEW_NOT_OWN, ISSUE_HISTORY_VIEW, ISSUE_ESCALATION_VIEW, ISSUE_ATTACHMENT_VIEW,
				ISSUE_SUBSCRIBE, ISSUE_SUBSCRIBE_OTHERS, TRACKER_SUBSCRIBE, TRACKER_SUBSCRIBE_OTHERS, TRACKER_SUBSCRIBERS_VIEW,
				ADMIN_PUBLIC_VIEW, ISSUE_ASSOCIATION_VIEW, ISSUE_SUSPECTED_MERGE, BRANCH_MERGE, EXPORT, BRANCH_REPLACE_CONTENT,
				DATA_ADMINISTRATION);
	}
}
