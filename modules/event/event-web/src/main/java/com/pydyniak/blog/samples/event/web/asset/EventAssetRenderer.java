package com.pydyniak.blog.samples.event.web.asset;

import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.pydyniak.blog.samples.event.model.Event;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class EventAssetRenderer extends BaseJSPAssetRenderer<Event> {
    private Event event;
    private ModelResourcePermission<Event> eventModelResourcePermission;

    public EventAssetRenderer(Event event, ModelResourcePermission<Event> eventModelResourcePermission) {
        this.event = event;
        this.eventModelResourcePermission = eventModelResourcePermission;
    }

    @Override
    public boolean hasViewPermission(PermissionChecker permissionChecker) throws PortalException {
        //TODO register model resource I guess?
        return true;
//        return eventModelResourcePermission.contains(permissionChecker, event, ActionKeys.VIEW);
    }

    @Override
    public boolean hasEditPermission(PermissionChecker permissionChecker) throws PortalException {
        //TODO same as above?
        return true;
//        return eventModelResourcePermission.contains(permissionChecker, event, ActionKeys.UPDATE);
    }

    @Override
    public String getJspPath(HttpServletRequest httpServletRequest, String template) {
        return null;
//        if (template.equals(TEMPLATE_FULL_CONTENT)) {
//            httpServletRequest.setAttribute("event", event);
//
//            return "/asset/" + template + ".jsp";
//        } else {
//            return null;
//        }
    }

    @Override
    public Event getAssetObject() {
        return event;
    }

    @Override
    public long getGroupId() {
        return event.getGroupId();
    }

    @Override
    public long getUserId() {
        return event.getUserId();
    }

    @Override
    public String getUserName() {
        return event.getUserName();
    }

    @Override
    public String getUuid() {
        return event.getUuid();
    }

    @Override
    public String getClassName() {
        return Event.class.getName();
    }

    @Override
    public long getClassPK() {
        return event.getEventId();
    }

    @Override
    public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
        Locale locale = getLocale(portletRequest);
        String content = event.getContent(locale);
        return StringUtil.shorten(HtmlUtil.extractText(content));
    }

    @Override
    public String getTitle(Locale locale) {
        String title = event.getTitle(locale);
        return HtmlUtil.extractText(title);
    }

    @Override
    public boolean include(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String template) throws Exception {
        httpServletRequest.setAttribute("event", event);
        return super.include(httpServletRequest, httpServletResponse, template);
    }
}
