package com.pydyniak.blog.samples.event.web.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.pydyniak.blog.samples.event.model.Event;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = {})
public class EventModelPermission {
    private static ModelResourcePermission<Event> eventModelResourcePermission;

    public static boolean contains(PermissionChecker permissionChecker, Event event, String actionId) throws PortalException {
        return eventModelResourcePermission.contains(permissionChecker, event, actionId);
    }

    public static boolean contains(PermissionChecker permissionChecker, long eventId, String actionId) throws PortalException {
        return eventModelResourcePermission.contains(permissionChecker, eventId, actionId);
    }

    @Reference(target = "(model.class.name=com.pydyniak.blog.samples.event.model.Event)",
    unbind = "-")
    public void setEventModelResourcePermission(ModelResourcePermission<Event> eventModelResourcePermission) {
        EventModelPermission.eventModelResourcePermission = eventModelResourcePermission;
    }
}
