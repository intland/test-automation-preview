package com.intland.codebeamer.integration.api.builder.association;

public enum AssociationType {

	DEPENDS(1),
	PARENT(2),
	CHILD(3),
	RELATED(4),
	DERIVED(5),
	INVALIDATES(8),
	COPY_OF(9),
	PRE_ACTION_REUSE(10),
	POST_ACTION_REUSE(11);

	private final int idValue;

	AssociationType(int idValue) {
		this.idValue = idValue;
	}

	public int getIdValue() {
		return idValue;
	}
}
