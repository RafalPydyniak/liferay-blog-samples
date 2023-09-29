package com.pydyniak.blog.samples.event.internal.messaging;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.util.MapUtil;
import com.pydyniak.blog.samples.event.constants.EventConstants;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component
public class EventCreatedMessagingConfigurator {
    @Reference
    private DestinationFactory destinationFactory;

    private ServiceRegistration<Destination> destinationServiceRegistration;

    @Activate
    public void activate(BundleContext bundleContext) {
        Destination destination = destinationFactory.createDestination(
                DestinationConfiguration.createSerialDestinationConfiguration(
                        EventConstants.MESSAGE_BUS_DESTINATION_NAME_EVENT_CREATED));

        destinationServiceRegistration = bundleContext.registerService(Destination.class, destination,
                MapUtil.singletonDictionary("destination.name", destination.getName()));
    }

    @Deactivate
    public void deactivate() {
        if(destinationServiceRegistration != null) {
            destinationServiceRegistration.unregister();
        }
    }
}
