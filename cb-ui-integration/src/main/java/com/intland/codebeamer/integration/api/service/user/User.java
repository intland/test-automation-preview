package com.intland.codebeamer.integration.api.service.user;

import com.intland.codebeamer.integration.api.service.Member;
import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;

public class User extends Member {

	private UserId id;

	private String username;

	private String firstName;

	private String lastName;

	private String email;

	public User(Integer id) {
		this.id = new UserId(id);
	}

	public User(UserId id, String username, String firstName, String lastName, String email) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public UserId getUserId() {
		return id;
	}

	public int getId() {
		return id.id().intValue();
	}

	public String getName() {
		return username;
	}

	public ArtifactType getArtifactType() {
		return ArtifactType.USER_ACCOUNT;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}
}
