package com.pydyniak.blog.samples.event.web.portlet.commands;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.pydyniak.blog.samples.event.constants.EventWebPortletKeys;
import org.osgi.service.component.annotations.Component;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

@Component(
        property = {
                "javax.portlet.name="+ EventWebPortletKeys.EVENTWEB,
                "mvc.command.name=" + EventWebPortletKeys.MVC_COMMAND_RENDER_ADD_EVENT,
        },
        service = MVCRenderCommand.class
)
public class AddEventRenderCommand implements MVCRenderCommand{

    @Override
    public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
        return "/add_event.jsp";
    }
}
