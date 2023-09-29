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

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.pydyniak.blog.samples.event.constants.EventConstants;
import com.pydyniak.blog.samples.event.model.Event;
import com.pydyniak.blog.samples.event.service.base.EventServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafał Pydyniak
 */
@Component(
	property = {
		"json.web.service.context.name=event",
		"json.web.service.context.path=Event"
	},
	service = AopService.class
)
public class EventServiceImpl extends EventServiceBaseImpl {
	@Reference(policy = ReferencePolicy.DYNAMIC,
	policyOption = ReferencePolicyOption.GREEDY,
	target = "(model.class.name=com.pydyniak.blog.samples.event.model.Event)")
	private volatile ModelResourcePermission<Event> eventModelResourcePermission;

	@Reference(policy = ReferencePolicy.DYNAMIC,
	policyOption = ReferencePolicyOption.GREEDY,
	target = "(resource.name=" + EventConstants.RESOURCE_NAME + ")")
	private volatile PortletResourcePermission portletResourcePermission;


	public void deleteEvent(long eventId) throws PortalException {
		eventModelResourcePermission.check(getPermissionChecker(), eventId, ActionKeys.DELETE);
		eventLocalService.deleteEvent(eventId);
	}

	public Event addEvent(Map<Locale, String> title, Map<Locale, String> content, Date date, ServiceContext serviceContext) throws PortalException {
		portletResourcePermission.check(getPermissionChecker(), serviceContext.getScopeGroupId(),
				ActionKeys.ADD_ENTRY);
		return eventLocalService.addEvent(title, content, date, serviceContext);
	}
}