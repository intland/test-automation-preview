package com.intland.codebeamer.integration.api.builder.association;

import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.swagger.client.model.Association;
import com.intland.swagger.client.model.AssociationTypeReference;
import com.intland.swagger.client.model.TrackerItemReference;

public class AssociationBuilder {

	private final Association association = new Association();

	public AssociationBuilder from(TrackerItemId trackerItemId) {
		association.setFrom(new TrackerItemReference().id(trackerItemId.id()));
		return this;
	}

	public AssociationBuilder to(TrackerItemId trackerItemId) {
		association.setTo(new TrackerItemReference().id(trackerItemId.id()));
		return this;
	}

	public AssociationBuilder type(AssociationType associationType) {
		AssociationTypeReference associationTypeReference = new AssociationTypeReference();
		associationTypeReference.setId(associationType.getIdValue());
		associationTypeReference.setName(associationType.name().toLowerCase());

		association.setType(associationTypeReference);
		return this;
	}

	public AssociationBuilder propagateSuspects() {
		association.setPropagatingSuspects(Boolean.TRUE);
		return this;
	}

	public AssociationBuilder reversePropagation() {
		association.setReversePropagation(Boolean.TRUE);
		return this;
	}

	public AssociationBuilder bidirectionalPropagation() {
		association.setBiDirectionalPropagation(Boolean.TRUE);
		return this;
	}

	public AssociationBuilder propagateDependencies() {
		association.setPropagatingDependencies(Boolean.TRUE);
		return this;
	}

	public Association build() {
		return association;
	}
}
