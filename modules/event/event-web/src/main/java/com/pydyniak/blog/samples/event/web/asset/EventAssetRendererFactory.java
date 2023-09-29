package com.pydyniak.blog.samples.event.web.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.pydyniak.blog.samples.event.model.Event;
import com.pydyniak.blog.samples.event.service.EventLocalService;
import com.pydyniak.blog.samples.event.constants.EventWebPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.ServletContext;

@Component(immediate = true,
        property = "javax.portlet.name=" + EventWebPortletKeys.EVENTWEB,
        service = AssetRendererFactory.class)
public class EventAssetRendererFactory extends BaseAssetRendererFactory<Event> {
    //What this type is?
    private static final String TYPE = "sample-event";

    @Reference
    private EventLocalService eventLocalService;

    @Reference
    private ModelResourcePermission<Event> eventModelResourcePermission;

    @Reference(target = "(osgi.web.symbolicname=com.pydyniak.blog.samples.event.web)")
    private ServletContext servletContext;

    public EventAssetRendererFactory() {
        setClassName(Event.class.getName());
        setLinkable(true);
        setPortletId(EventWebPortletKeys.EVENTWEB);
        setSearchable(true);
    }

    @Override
    public AssetRenderer getAssetRenderer(long classPK, int type) throws PortalException {
        Event event = eventLocalService.getEvent(classPK);
        EventAssetRenderer eventAssetRenderer = new EventAssetRenderer(event, eventModelResourcePermission);
        eventAssetRenderer.setAssetRendererType(type);
        eventAssetRenderer.setServletContext(servletContext);
        return eventAssetRenderer;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getClassName() {
        return Event.class.getName();
    }
}
