package com.pydyniak.blog.samples.event.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.pydyniak.blog.samples.event.constants.EventConstants;
import com.pydyniak.blog.samples.event.constants.EventWebPortletKeys;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class EventPortletResourcePermissionRegistrar {

    private ServiceRegistration<PortletResourcePermission> serviceRegistration;

    @Reference
    private StagingPermission stagingPermission;

    @Activate
    public void activate(BundleContext bundleContext) {
        serviceRegistration = bundleContext.registerService(PortletResourcePermission.class,
                PortletResourcePermissionFactory.create(
                        EventConstants.RESOURCE_NAME,
                        new StagedPortletPermissionLogic(stagingPermission, EventWebPortletKeys.EVENTWEB)),
                        HashMapDictionaryBuilder.<String,Object>put(
                                "resource.name", EventConstants.RESOURCE_NAME
                        ).build()
                );
    }

    @Deactivate
    public void deActivate() {
        serviceRegistration.unregister();
    }
}
