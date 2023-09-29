package com.pydyniak.blog.samples.event.internal.security.permission.resource;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.*;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;
import com.pydyniak.blog.samples.event.constants.EventConstants;
import com.pydyniak.blog.samples.event.constants.EventWebPortletKeys;
import com.pydyniak.blog.samples.event.model.Event;
import com.pydyniak.blog.samples.event.service.EventLocalService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.util.Dictionary;

@Component(immediate = true)
public class EventModelResourcePermissionRegistrar {
    @Reference
    private EventLocalService eventLocalService;

    @Reference(target = "(resource.name=" + EventConstants.RESOURCE_NAME + ")")
    private PortletResourcePermission portletResourcePermission;

    @Reference
    private StagingPermission stagingPermission;

    @Reference
    private WorkflowPermission workflowPermission;

    @Reference
    private GroupLocalService groupLocalService;

    private ServiceRegistration<ModelResourcePermission> serviceRegistration;

    @Activate
    public void activate(BundleContext bundleContext) {
        Dictionary<String, Object> properties =
                HashMapDictionaryBuilder.<String, Object>put(
                        "model.class.name", Event.class.getName()
                ).build();

        serviceRegistration = bundleContext.registerService(ModelResourcePermission.class,
                ModelResourcePermissionFactory.create(
                        Event.class,
                        Event::getEventId,
                        eventLocalService::getEvent,
                        portletResourcePermission,
                        (modelResourcePermission, consumer) -> {
                            consumer.accept(new StagedModelPermissionLogic<>(
                                    stagingPermission, EventWebPortletKeys.EVENTWEB,
                                    Event::getEventId
                            ));
                            consumer.accept(new WorkflowedModelPermissionLogic<>(
                                    workflowPermission, modelResourcePermission, groupLocalService,
                                    Event::getEventId));
                        }), properties
        );
    }

    @Deactivate
    public void deactivate() {
        serviceRegistration.unregister();
    }
}
