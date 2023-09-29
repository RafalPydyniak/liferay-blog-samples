/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.pydyniak.blog.samples.event.service.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.pydyniak.blog.samples.event.constants.EventConstants;
import com.pydyniak.blog.samples.event.model.Event;
import com.pydyniak.blog.samples.event.service.base.EventLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafał Pydyniak
 */
@Component(
	property = "model.class.name=com.pydyniak.blog.samples.event.model.Event",
	service = AopService.class
)
public class EventLocalServiceImpl extends EventLocalServiceBaseImpl {

	private static final Log LOG = LogFactoryUtil.getLog(EventLocalServiceImpl.class);

	@Reference
	private MessageBus messageBus;

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Event addEvent(Map<Locale, String> title, Map<Locale, String> content, Date date, ServiceContext serviceContext) throws PortalException {
		Event event = createEvent(counterLocalService.increment(Event.class.getName()));
		long groupId = serviceContext.getScopeGroupId();
		User user = userLocalService.getUser(serviceContext.getUserId());
		for (Map.Entry<Locale, String> entry : content.entrySet()) {
			String sanitizedDescription = SanitizerUtil.sanitize(
					serviceContext.getCompanyId(), groupId, serviceContext.getUserId(),
					Event.class.getName(), event.getEventId(),
					ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL, entry.getValue(),
					null);

			content.put(entry.getKey(), sanitizedDescription);
		}
		for(Map.Entry<Locale, String> entry : title.entrySet()) {
			String sanitizedTitle = SanitizerUtil.sanitize(
					serviceContext.getCompanyId(), groupId, serviceContext.getUserId(),
					Event.class.getName(), event.getEventId(),
					ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL, entry.getValue(),
					null);
			title.put(entry.getKey(), sanitizedTitle);
		}
		//Event fields
		event.setTitleMap(title);
		event.setContentMap(content);
		event.setDate(date);

		//Audit fields
		event.setCompanyId(user.getCompanyId());
		event.setUserId(user.getUserId());
		event.setUserName(user.getFullName());
		event.setCreateDate(new Date());
		event.setModifiedDate(new Date());
		event.setUuid(serviceContext.getUuid());
		event.setGroupId(groupId);

		//Workflow fields
		int status = WorkflowConstants.STATUS_APPROVED;
		event.setStatus(status);
		event.setStatusByUserId(user.getUserId());
		event.setStatusByUserName(user.getFullName());
		event.setStatusDate(serviceContext.getModifiedDate(null));
		event = eventPersistence.update(event);

		resourceLocalService.addResources(user.getCompanyId(), groupId, user.getUserId(),
				Event.class.getName(), event.getEventId(), false, true, true);

		String summary = HtmlUtil.extractText(
				StringUtil.shorten(event.getContent(), 500));

		AssetEntry assetEntry = assetEntryLocalService.updateEntry(
				event.getUserId(), event.getGroupId(),
				event.getCreateDate(), event.getModifiedDate(),
				Event.class.getName(), event.getEventId(),
				event.getUuid(), 0, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(), true, true, null, null, new Date(),
				null, ContentTypes.TEXT_HTML, event.getTitle(),
				event.getContent(), summary, null, null,
				0, 0, 1.0);

		assetLinkLocalService.updateLinks(user.getUserId(), assetEntry.getEntryId(),
				serviceContext.getAssetLinkEntryIds(),
				AssetLinkConstants.TYPE_RELATED);

		sendEventCreatedMessage(event);

		return event;
	}

	/**
	 * Dummy method for message bus showcase
	 * @param event
	 */
	private void sendEventCreatedMessage(Event event) {
		Message message = new Message();
		HashMap<String, String> messagePayload = new HashMap<>();
		messagePayload.put("eventId", Long.toString(event.getEventId()));
		message.setPayload(JSONFactoryUtil.createJSONObject(messagePayload).toJSONString());
		messageBus.sendMessage(EventConstants.MESSAGE_BUS_DESTINATION_NAME_EVENT_CREATED, message);
		LOG.info("Sent message to the message bus");
	}

	@Override
	public Event deleteEvent(long eventId) throws PortalException {
		return super.deleteEvent(eventId);
	}
}