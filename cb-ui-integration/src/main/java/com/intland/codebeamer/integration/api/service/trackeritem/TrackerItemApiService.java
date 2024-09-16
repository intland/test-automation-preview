package com.intland.codebeamer.integration.api.service.trackeritem;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.reviewhub.model.Review;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ReviewId;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.AbstractTrackerItemCommentBuilder;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.Attachment;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.AttachmentId;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.Comment;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.CommentId;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.CommentItem;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.CommentType;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.TrackerItemAddedComment;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.TrackerItemCommentFactory;
import com.intland.codebeamer.integration.api.service.trackeritem.template.TrackerItemTemplate;
import com.intland.codebeamer.integration.api.service.trackeritem.template.TrackerItemTemplateId;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.IntegrationApi;
import com.intland.swagger.client.internal.api.TrackerItemApi;
import com.intland.swagger.client.internal.api.TrackerItemsAttachmentApi;
import com.intland.swagger.client.internal.api.TrackerItemsCommentApi;
import com.intland.swagger.client.model.AddTrackerItemCommentRequest;
import com.intland.swagger.client.model.CreateTrackerItem;
import com.intland.swagger.client.model.Identifier;
import com.intland.swagger.client.model.TrackerItemAddCommentResult;
import com.intland.swagger.client.model.TrackerItemCreateTemplate;
import com.intland.swagger.client.model.TrackerItemSearchRequest;
import com.intland.swagger.client.model.TrackerItemSearchResult;

public class TrackerItemApiService extends AbstractApiService {

	private static final Logger logger = LogManager.getLogger();

	private TrackerItemApi trackerItemApi;

	private ProjectApiService projectApiService;

	private UserApiService userApiService;

	private RoleApiService roleApiService;

	private TrackerFieldApiService trackerFieldApiService;

	private TrackerItemsCommentApi trackerItemsCommentApi;

	private TrackerItemsAttachmentApi trackerItemsAttachmentApi;

	private IntegrationApi integrationApi;

	public TrackerItemApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public TrackerItemApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public TrackerItemApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}

	public TrackerItemApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		this.trackerItemApi = new TrackerItemApi(getUserApiClient());

		this.trackerFieldApiService = dataManagerService.getTrackerFieldApiService(apiUser);
		this.projectApiService = dataManagerService.getProjectApiService(apiUser);
		this.userApiService = dataManagerService.getUserApiService(apiUser);
		this.roleApiService = dataManagerService.getRoleApiService(apiUser);
		this.trackerItemsAttachmentApi = new TrackerItemsAttachmentApi(getUserApiClient());
		this.trackerItemsCommentApi = new TrackerItemsCommentApi(getUserApiClient());
		this.integrationApi = new IntegrationApi(getUserApiClient());
	}

	public TrackerItemId createTrackerItem(Project project, String trackerName, Function<CreateTrackerItemBuilder, CreateTrackerItemBuilder> builder) {
		return createTrackerItem(projectApiService.findTrackerByName(project, trackerName), builder);
	}

	public TrackerItemId createTrackerItem(Tracker tracker, Function<CreateTrackerItemBuilder, CreateTrackerItemBuilder> builder) {
		return createTrackerItem(tracker.id(), builder);
	}

	public TrackerItemId createTrackerItem(TrackerId trackerId, Function<CreateTrackerItemBuilder, CreateTrackerItemBuilder> builder) {
		return builder.apply(new CreateTrackerItemBuilder(trackerId, this.trackerFieldApiService, this.trackerItemApi, this.projectApiService, this.userApiService, this.roleApiService, this)).create();
	}

	public void createTrackerItems(Function<CreateTrackerItemBuilder, CreateTrackerItemBuilder>... builders) {
		try {
			List<CreateTrackerItem> createTrackerItems = new ArrayList<>();
			for (Function<CreateTrackerItemBuilder, CreateTrackerItemBuilder> builder : builders) {
				CreateTrackerItemBuilder createTrackerItemBuilder = builder.apply(
						new CreateTrackerItemBuilder(this.trackerFieldApiService, this.trackerItemApi,
								this.projectApiService, this.userApiService, this.roleApiService, this));
				if (createTrackerItemBuilder.trackerId == null) {
					throw new IllegalStateException("Tracker id cannot be null");
				}
				CreateTrackerItem createTrackerItem = new CreateTrackerItem()
						.trackerId(Integer.valueOf(createTrackerItemBuilder.trackerId.id()))
						.trackerItemModel(createTrackerItemBuilder.trackerItem);
				createTrackerItems.add(createTrackerItem);
			}
			trackerItemApi.createTrackerItems(createTrackerItems);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public TrackerItemId createDocumentItem(TrackerId trackerId, File file) {
		try {
			Identifier identifier = integrationApi.createDocumentItem(trackerId.id(), file, null, null, null);
			return new TrackerItemId(identifier.getId());
		} catch (ApiException e) {
			throw new IllegalStateException(e);
		}
	}

	public void updateTrackerItem(TrackerItemId trackerItemId, Function<UpdateTrackerItemBuilder, UpdateTrackerItemBuilder> builder) {
		try {
			com.intland.swagger.client.model.TrackerItem trackerItem = trackerItemApi.getTrackerItem(Integer.valueOf(trackerItemId.id()), null, null);
			builder.apply(new UpdateTrackerItemBuilder(new TrackerId(trackerItem.getTracker().getId().intValue()), trackerItem,
					this.trackerFieldApiService, this.trackerItemApi, this.projectApiService, this.userApiService, this.roleApiService, this)).update();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void createTrackerItemComment(TrackerItemId trackerItemId,
			Function<TrackerItemCommentFactory, AbstractTrackerItemCommentBuilder>... builders) {
		for (Function<TrackerItemCommentFactory, AbstractTrackerItemCommentBuilder> function : builders) {
			try {
				CommentItem commentItem = function.apply(new TrackerItemCommentFactory()).build();
				trackerItemsCommentApi.commentOnTrackerItem(Integer.valueOf(trackerItemId.id()), commentItem.comment(),
						commentItem.attachments(), commentItem.commentType().getValue());
			} catch (Exception e) {
				throw new IllegalStateException(e.getMessage(), e);
			}
		}
	}

	public List<Comment> getTrackerItemComments(TrackerItemId trackerItemId) {
		try {
			List<com.intland.swagger.client.model.Comment> comments = trackerItemsCommentApi.getTrackerItemComments(Integer.valueOf(trackerItemId.id()));
			return comments.stream()
					.map(comment -> new Comment(new CommentId(comment.getId().intValue()), comment.getComment(),
							comment.getAttachments()
									.stream()
									.map(attachment -> new AttachmentId(attachment.getId().intValue()))
									.toList()
					)).toList();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public Attachment getTrackerItemAttachment(TrackerItemId trackerItem, AttachmentId attachmentId) {
		try {
			com.intland.swagger.client.model.Attachment attachment = trackerItemsAttachmentApi.getTrackerItemAttachment(
					Integer.valueOf(trackerItem.id()), Integer.valueOf(attachmentId.id()));
			return new Attachment(new AttachmentId(attachment.getId().intValue()), attachment.getName(), attachment.getSize().longValue());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void deleteTrackerItemByName(Tracker tracker, String trackerItemName) {
		deleteTrackerItemByName(tracker.id(), trackerItemName);
	}

	public void deleteTrackerItemByName(TrackerId trackerId, String trackerItemName) {
		deleteTrackerItem(findTrackerItemByName(trackerId, trackerItemName));
	}

	public void deleteTrackerItem(TrackerItem trackerItem) {
		deleteTrackerItem(trackerItem.id());
	}

	public void deleteTrackerItem(TrackerItemId trackerItemId) {
		deleteTrackerItem(Integer.valueOf(trackerItemId.id()));
	}

	public void deleteTrackerItem(Integer trackerItemId) {
		try {
			trackerItemApi.deleteTrackerItem(trackerItemId);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public TrackerItem findTrackerItemByName(Tracker tracker, String trackerItemName) {
		return findTrackerItemByName(tracker.id(), trackerItemName);
	}

	public TrackerItem findTrackerItemByName(TrackerId trackerId, String trackerItemName) {
		List<TrackerItem> trackerItemsByNames = findTrackerItemByNames(trackerId, trackerItemName);
		if (CollectionUtils.size(trackerItemsByNames) > 1) {
			throw new IllegalStateException("More than one tracker item is found by name: '%s'".formatted(trackerItemName));
		}

		return trackerItemsByNames.getFirst();
	}

	public List<TrackerItem> findTrackerItemByNames(TrackerId trackerId, String... trackerItemName) {
		Objects.requireNonNull(trackerItemName, "TrackerItemName cannot be null");

		try {
			String trackerItemNames = Arrays.stream(trackerItemName)
					.map(name -> "summary = '%s'".formatted(name)) // TODO Escape name
					.collect(Collectors.joining(" OR "));

			String queryString = "tracker.id IN (%s) AND (%s)".formatted(Integer.valueOf(trackerId.id()), trackerItemNames);
			logger.debug("Query string: {}", queryString);

			TrackerItemSearchResult result = this.trackerItemApi.findTrackerItemsByCbQL(new TrackerItemSearchRequest().queryString(queryString));

			List<com.intland.swagger.client.model.TrackerItem> items = result.getItems();
			if (CollectionUtils.isEmpty(items)) {
				throw new IllegalStateException("Tracker item is not found by name: '%s'".formatted(trackerItemNames));
			}

			return items.stream()
					.sorted((t1, t2) -> indexOf(t1, trackerItemName).compareTo(indexOf(t2, trackerItemName))) // keep the original order
					.map(trackerItem -> new TrackerItem(new TrackerItemId(trackerItem.getId().intValue()), trackerItem.getName()))
					.toList();
		} catch (ApiException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}

	}

	public TrackerItemTemplate createTrackerItemTemplate(TrackerItemId trackerItemId, String title, String description, boolean publicTemplate) {
		try {
			com.intland.swagger.client.model.TrackerItemTemplate createdTemplate = trackerItemApi.createTrackerItemTemplate(
					Integer.valueOf(trackerItemId.id()),
					new TrackerItemCreateTemplate().title(title).description(description)
							.publicTemplate(Boolean.valueOf(publicTemplate)));
			return new TrackerItemTemplate(new TrackerItemTemplateId(
					Objects.requireNonNull(createdTemplate.getId()).intValue()),
					createdTemplate.getTitle(), createdTemplate.getDescription(),
					BooleanUtils.isTrue(createdTemplate.getPublicTemplate()));
		} catch (ApiException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public TrackerItemTemplate overwriteTrackerItemTemplate(TrackerItemId trackerItemId, TrackerItemTemplateId templateId) {
		try {
			com.intland.swagger.client.model.TrackerItemTemplate createdTemplate = trackerItemApi.overwriteTrackerItemTemplate(
					Integer.valueOf(trackerItemId.id()), Integer.valueOf(templateId.id()));
			return new TrackerItemTemplate(new TrackerItemTemplateId(
					Objects.requireNonNull(createdTemplate.getId()).intValue()),
					createdTemplate.getTitle(), createdTemplate.getDescription(),
					BooleanUtils.isTrue(createdTemplate.getPublicTemplate()));
		} catch (ApiException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public TrackerItemAddedComment addNewCommentOnReviewItem(Integer reviewId, Function<AddCommentBuilder, AddCommentBuilder> builder) {
		AddTrackerItemCommentRequest addTrackerItemCommentRequest = builder.apply(
				new AddCommentBuilder(new AddTrackerItemCommentRequest())).build();
		try {
			TrackerItemAddCommentResult trackerItemAddCommentResult = trackerItemApi.addComment(reviewId,
					addTrackerItemCommentRequest);

			assert trackerItemAddCommentResult.getCommentType() != null;
			assert trackerItemAddCommentResult.getTrackerItem() != null;

			return new TrackerItemAddedComment(new Review(new ReviewId(
					Objects.requireNonNull(trackerItemAddCommentResult.getTrackerItem().getId())),
					trackerItemAddCommentResult.getTrackerItem().getName()), trackerItemAddCommentResult.getComment(),
					CommentType.fromValue(trackerItemAddCommentResult.getCommentType().getValue()));
		} catch (ApiException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private Integer indexOf(com.intland.swagger.client.model.TrackerItem trackerItem, String... trackerItemName) {
		return Integer.valueOf(ArrayUtils.indexOf(trackerItemName, trackerItem.getName()));
	}

}
