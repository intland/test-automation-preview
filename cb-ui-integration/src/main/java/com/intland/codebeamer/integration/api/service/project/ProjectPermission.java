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

/**
 *
 */
package com.intland.codebeamer.integration.api.service.project;

import java.util.List;

public enum ProjectPermission {

	WIKI_SPACE_VIEW,

	DOCUMENT_VIEW,

	DOCUMENT_VIEW_HISTORY,

	DOCUMENT_ADD,

	DOCUMENT_UNPACK,

	DOCUMENT_SUBSCRIBE,

	DOCUMENT_SUBSCRIBE_OTHERS,

	DOCUMENT_SUBSCRIBERS_VIEW,

	TRACKER_VIEW,

	TRACKER_ADMIN,

	TRACKER_REPORT,

	CMDB_VIEW,

	CMDB_ADMIN,

	BASELINE_VIEW,

	BASELINE_ADMIN,

	CHAT_VIEW,

	SCM_VIEW,

	SCM_ADMIN,

	MEMBERS_VIEW,

	MEMBERS_ADMIN,

	MEMBER_ROLE_VIEW,

	MEMBER_ROLE_ADMIN,

	PROJECT_ADMIN,

	WORKING_SET_VIEW,

	WORKING_SET_ADMIN;

	public int bit() {
		return (1 << ordinal());
	}
	
	public static List<ProjectPermission> projectAdmin() {
		return List.of(WIKI_SPACE_VIEW, DOCUMENT_VIEW, DOCUMENT_VIEW_HISTORY, DOCUMENT_ADD, DOCUMENT_UNPACK,
				DOCUMENT_SUBSCRIBE, DOCUMENT_SUBSCRIBE_OTHERS, DOCUMENT_SUBSCRIBERS_VIEW, TRACKER_VIEW, TRACKER_ADMIN,
				TRACKER_REPORT, CMDB_VIEW, CMDB_ADMIN, BASELINE_VIEW, BASELINE_ADMIN, CHAT_VIEW, SCM_VIEW, SCM_ADMIN,
				MEMBERS_VIEW, MEMBERS_ADMIN, MEMBER_ROLE_VIEW, MEMBER_ROLE_ADMIN, PROJECT_ADMIN, WORKING_SET_VIEW,
				WORKING_SET_ADMIN);
	}
	
	public static List<ProjectPermission> developer() {
		return List.of(WIKI_SPACE_VIEW, DOCUMENT_VIEW, DOCUMENT_VIEW_HISTORY, DOCUMENT_ADD, DOCUMENT_UNPACK,
				DOCUMENT_SUBSCRIBE, DOCUMENT_SUBSCRIBE_OTHERS, DOCUMENT_SUBSCRIBERS_VIEW, TRACKER_VIEW,
				TRACKER_REPORT, CMDB_VIEW, BASELINE_VIEW, CHAT_VIEW, SCM_VIEW, SCM_ADMIN,
				MEMBERS_VIEW, MEMBER_ROLE_VIEW, WORKING_SET_VIEW);
	}
}
