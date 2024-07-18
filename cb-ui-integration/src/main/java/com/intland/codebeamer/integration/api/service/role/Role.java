package com.intland.codebeamer.integration.api.service.role;

import com.intland.codebeamer.integration.api.service.Member;
import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;

public class Role extends Member {

	private RoleId id;

	private String name;

	public Role(RoleId id, String name) {
		this.id = id;
		this.name = name;
	}

	public RoleId getRoleId() {
		return id;
	}

	public int getId() {
		return id.id().intValue();
	}

	public String getName() {
		return name;
	}

	public ArtifactType getArtifactType() {
		return ArtifactType.ROLE;
	}

}
