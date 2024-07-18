package com.intland.codebeamer.integration.api.service.project;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroup;

public class ProjectBuilder {

	private String projectName;
	
	private ProjectApiService projectApiService;

	private Map<String, List<ProjectPermission>> roles;

	private Map<String, List<String>> userToRoles;

	private Map<String, List<String>> groupToRoles;
	
	public ProjectBuilder(String projectName, ProjectApiService projectApiService) {
		this.projectName = projectName;
		this.projectApiService = projectApiService;
		this.roles = new HashMap<>();
		this.userToRoles = new HashMap<>();
		this.groupToRoles = new HashMap<>();
	}

	public ProjectBuilder addRole(String roleName, ProjectPermission... projectPermission) {
		return addRole(roleName, Arrays.asList(projectPermission));
	}
	
	public ProjectBuilder addRole(Role role, List<ProjectPermission> projectPermission) {
		return addRole(role.getName(), projectPermission);
	}
	
	public ProjectBuilder addRole(String roleName, List<ProjectPermission> projectPermission) {
		roles.computeIfAbsent(roleName, r -> new LinkedList<ProjectPermission>()).addAll(projectPermission);
		return this;
	}

	public ProjectBuilder addUserAs(User user, String roleName) {
		return addUserAs(user.getName(), roleName);
	}
	
	public ProjectBuilder addUserAs(String username, String roleName) {
		userToRoles.computeIfAbsent(username, r -> new LinkedList<String>()).add(roleName);
		return this;
	}
	
	public ProjectBuilder addGroupAs(UserGroup group, String roleName) {
		return addGroupAs(group.name(), roleName);
	}
	
	public ProjectBuilder addGroupAs(String groupName, String roleName) {
		groupToRoles.computeIfAbsent(groupName, r -> new LinkedList<String>()).add(roleName);
		return this;
	}
	
	public Project build() {
		Project project = projectApiService.createProjectFromTemplate(projectName);
		try {
		
			for (Entry<String, List<ProjectPermission>> role : roles.entrySet()) {
				projectApiService.addRoles(project.id(), role.getKey(), role.getValue());
			}
			
			for (Entry<String, List<String>> user : userToRoles.entrySet()) {
				projectApiService.addUserWithRoles(project.id(), List.of(user.getKey()), user.getValue());
			}
			
			for (Entry<String, List<String>> group : groupToRoles.entrySet()) {
				projectApiService.addGroupWithRoles(project.id(), List.of(group.getKey()), group.getValue());
			}
			
			return project;
		} catch (Exception e) {
			projectApiService.deleteProject(project.id());
			throw e;
		}
	}

}
