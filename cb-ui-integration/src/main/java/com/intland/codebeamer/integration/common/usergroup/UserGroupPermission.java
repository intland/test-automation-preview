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

package com.intland.codebeamer.integration.common.usergroup;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum UserGroupPermission {

    WIKI_EDIT_OWN_PAGE(1, "permission_wiki_edit_own_page"),
    ACCOUNT_ADMIN_OWN(2, "permission_account_admin_own"),
    ACCOUNT_ADMIN(4, "permission_account_admin"),
    ACCOUNT_ADDRESS_VIEW(8, "permission_account_address_view"),
    ACCOUNT_COMPANY_VIEW(16, "permission_account_company_view"),
    ACCOUNT_PHONE_VIEW(32, "permission_account_phone_view"),
    ACCOUNT_EMAIL_VIEW(64, "permission_account_email_view"),
    ACCOUNT_SKILLS_VIEW(128, "permission_account_skills_view"),
    ACCOUNT_ROLE_VIEW(256, "permission_account_role_view"),
    ACCOUNT_ROLE_ADMIN(512, "permission_account_role_admin"),
    DOCUMENT_ADD_GLOBAL(1024, "permission_document_add_global"),
    LABEL_PUBLIC_CREATE(2048, "permission_label_public_create"),
    LABEL_PUBLIC_ADMIN(4096, "permission_label_public_admin"),
    SYSTEM_PROJECT_CREATE(8192,"permission_system_project_create"),
    SYSTEM_ADMIN(16384, "permission_system_admin"),
    SERVICE_DESK(32768, "permission_service_desk"),
    QUERIES_VIEW(65536, "permission_queries_view"),
    ACCOUNT_MODIFY_OWN_TIMEZONE_DATEFORMAT(131072, "permission_account_modify_own_timezone_dateformat"),
    REVIEW(262144, "permission_review"),
    ALLOW_COMMENT_SUBSCRIPTION(524288, "permission_allow_comment_subscription"),
    API_PERMISSION(1048576, "permission_api_permission"),
    ONLY_API_PERMISSION(2097152, "permission_only_api_permission"),
    PROJECT_CATEGORY_ADMIN(4194304, "permission_project_category_admin"),
    MAINTENANCE_MODE_ACCESS(8388608, "permission_maintenance_mode_access"),
    SHARED_FIELD_ADMIN(16777216, "permission_shared_field_admin"),
    REVIEW_ADMINISTRATION(33554432, "permission_review_administration");

    private Integer bitMaks;

    private String permission;

    UserGroupPermission(Integer bitMaks, String permission) {
        this.bitMaks = bitMaks;
        this.permission = permission;
    }

    public static UserGroupPermission[] getRegularUserGroup() {
        return new UserGroupPermission[]{WIKI_EDIT_OWN_PAGE, ACCOUNT_ADMIN_OWN, ACCOUNT_ADDRESS_VIEW, ACCOUNT_COMPANY_VIEW,
                ACCOUNT_PHONE_VIEW, ACCOUNT_EMAIL_VIEW, ACCOUNT_SKILLS_VIEW, DOCUMENT_ADD_GLOBAL, LABEL_PUBLIC_CREATE,
                LABEL_PUBLIC_ADMIN, SYSTEM_PROJECT_CREATE, QUERIES_VIEW, REVIEW};
    }

    public static UserGroupPermission[] getSystemAdminGroup() {
        return new UserGroupPermission[]{WIKI_EDIT_OWN_PAGE, ACCOUNT_ADMIN_OWN, ACCOUNT_ADMIN, ACCOUNT_ADDRESS_VIEW,
                ACCOUNT_COMPANY_VIEW, ACCOUNT_PHONE_VIEW, ACCOUNT_EMAIL_VIEW, ACCOUNT_SKILLS_VIEW, ACCOUNT_ROLE_VIEW,
                ACCOUNT_ROLE_ADMIN, DOCUMENT_ADD_GLOBAL, LABEL_PUBLIC_CREATE, LABEL_PUBLIC_ADMIN, SYSTEM_PROJECT_CREATE,
                SYSTEM_ADMIN, QUERIES_VIEW, REVIEW, API_PERMISSION, PROJECT_CATEGORY_ADMIN, SHARED_FIELD_ADMIN};
    }

    public static UserGroupPermission[] getRegularUserGroupExcluding(UserGroupPermission... exclude) {
        List<UserGroupPermission> exclusionList = Arrays.stream(exclude).toList();
        return Arrays.stream(getRegularUserGroup())
                .filter(p -> !exclusionList.contains(p))
                .toArray(UserGroupPermission[]::new);
    }

    public static UserGroupPermission[] getRegularUserGroupIncluding(UserGroupPermission... include) {
        Set<UserGroupPermission> result = Arrays.stream(getRegularUserGroup())
                .collect(Collectors.toSet());
        result.addAll(Arrays.stream(include).toList());
        return result.stream()
                .toArray(UserGroupPermission[]::new);
    }

    public Integer getBitMaks() {
        return bitMaks;
    }

    public String getPermission() {
        return permission;
    }
}
