package com.pydyniak.blog.samples.event.web.portlet.commands;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.pydyniak.blog.samples.event.constants.EventWebPortletKeys;
import com.pydyniak.blog.samples.event.service.EventService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

@Component(immediate = true,
property = {
        "javax.portlet.name=" + EventWebPortletKeys.EVENTWEB,
        "mvc.command.name=" + EventWebPortletKeys.MVC_COMMAND_DELETE_EVENT
},
service = MVCActionCommand.class
)
public class DeleteEventMvcActionCommand extends BaseMVCActionCommand {
    @Reference
    private EventService eventService;

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        long eventId = ParamUtil.getLong(actionRequest, "eventId");
        eventService.deleteEvent(eventId);
    }
}
