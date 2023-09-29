package com.pydyniak.blog.samples.event.web.internal.security.permission.resource;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.pydyniak.blog.samples.event.constants.EventConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = {})
public class EventPermission {
    private static PortletResourcePermission portletResourcePermission;

    public static boolean contains(PermissionChecker permissionChecker, long groupId, String actionId) {
        return portletResourcePermission.contains(permissionChecker, groupId, actionId);
    }

    @Reference(target = "(resource.name=" + EventConstants.RESOURCE_NAME + ")", unbind = "-")
    public void setPortletResourcePermission(PortletResourcePermission portletResourcePermission) {
        EventPermission.portletResourcePermission = portletResourcePermission;
    }
}
