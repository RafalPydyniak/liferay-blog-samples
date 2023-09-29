package com.pydyniak.blog.samples.event.email.sender;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.pydyniak.blog.samples.event.constants.EventConstants;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = "destination.name=" + EventConstants.MESSAGE_BUS_DESTINATION_NAME_EVENT_CREATED,
        service = MessageListener.class
)
/**
 * Class showing how messages are delivered depending on message destination type (parallel, serial, synchronous)
 * Along with second class in this package different destination types gives different results with logs
 */
public class FakeEventCreatedMessageListener implements MessageListener {
    private static final Log LOG = LogFactoryUtil.getLog(FakeEventCreatedMessageListener.class);

    @Override
    public void receive(Message message) throws MessageListenerException {
        Object payload = message.getPayload();
        LOG.info("Received the payload: " + payload);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            LOG.error(e);
        }
        LOG.info("Finished processing");
    }

    //This makes sure our service gets reloaded on destination configuration change
    @Reference(target = "(destination.name="+EventConstants.MESSAGE_BUS_DESTINATION_NAME_EVENT_CREATED+")")
    private Destination destination;
}
