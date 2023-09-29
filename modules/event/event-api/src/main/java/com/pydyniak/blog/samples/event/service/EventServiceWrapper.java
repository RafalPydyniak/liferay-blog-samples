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

package com.pydyniak.blog.samples.event.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link EventService}.
 *
 * @author Rafał Pydyniak
 * @see EventService
 * @generated
 */
public class EventServiceWrapper
	implements EventService, ServiceWrapper<EventService> {

	public EventServiceWrapper() {
		this(null);
	}

	public EventServiceWrapper(EventService eventService) {
		_eventService = eventService;
	}

	@Override
	public com.pydyniak.blog.samples.event.model.Event addEvent(
			java.util.Map<java.util.Locale, String> title,
			java.util.Map<java.util.Locale, String> content,
			java.util.Date date,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _eventService.addEvent(title, content, date, serviceContext);
	}

	@Override
	public void deleteEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_eventService.deleteEvent(eventId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _eventService.getOSGiServiceIdentifier();
	}

	@Override
	public EventService getWrappedService() {
		return _eventService;
	}

	@Override
	public void setWrappedService(EventService eventService) {
		_eventService = eventService;
	}

	private EventService _eventService;

}