package com.intland.codebeamer.integration.api.builder.statetransition;

import com.intland.codebeamer.integration.api.service.Member;
import com.intland.swagger.client.model.ActionParameter;
import com.intland.swagger.client.model.ArtifactOpInfo;
import com.intland.swagger.client.model.ArtifactParamInfo;
import com.intland.swagger.client.model.ReviewOpInfo;
import com.intland.swagger.client.model.ReviewParamValue;

public class CreateReviewParamsBuilder {

	private final ActionParameter actionParameter = new ActionParameter();

	public CreateReviewParamsBuilder reviewParams(int approvals, int rejects) {
		ReviewParamValue value = new ReviewParamValue();
		value.setSignature(0);
		value.setPlusRole(Boolean.FALSE);
		value.setApprovals(approvals);
		value.setRejects(rejects);

		ReviewOpInfo reviewOpInfo = new ReviewOpInfo();
		reviewOpInfo.setValue(value);
		actionParameter.review(reviewOpInfo);
		return this;
	}

	public CreateReviewParamsBuilder reviewer(Member member) {
		ArtifactParamInfo artifactParamInfo = new ArtifactParamInfo();
		artifactParamInfo.setId(encodeMemberValue(member));
		artifactParamInfo.setValue(encodeMemberValue(member));
		artifactParamInfo.setDisabled(Boolean.FALSE);

		ArtifactOpInfo artifactOpInfo = new ArtifactOpInfo();
		artifactOpInfo.setOp(Operation.SET.getValue());
		artifactOpInfo.addValueItem(artifactParamInfo);
		actionParameter.reviewers(artifactOpInfo);
		return this;
	}

	ActionParameter build() {
		return actionParameter;
	}

	private String encodeMemberValue(Member member) {
		return member.getArtifactType().getValue() + "-" + member.getId();
	}

}
