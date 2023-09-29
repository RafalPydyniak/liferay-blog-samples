package com.pydyniak.blog.samples.event.web.portlet.commands;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.pydyniak.blog.samples.event.constants.EventWebPortletKeys;
import com.pydyniak.blog.samples.event.exception.EventDateException;
import com.pydyniak.blog.samples.event.model.Event;
import com.pydyniak.blog.samples.event.service.EventLocalService;
import com.pydyniak.blog.samples.event.service.EventService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Component(immediate = true, service = MVCActionCommand.class,
property = {
        "javax.portlet.name=" + EventWebPortletKeys.EVENTWEB,
        "mvc.command.name=" + EventWebPortletKeys.MVC_COMMAND_ADD_EVENT
})
public class AddEventEntryMvcActionCommand extends BaseMVCActionCommand {
    @Reference
    private EventService eventService;

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(actionRequest, "title");
        Map<Locale, String> contentMap = LocalizationUtil.getLocalizationMap(actionRequest, "content");
        Date eventDate = getEventDate(actionRequest);
        ServiceContext serviceContext = ServiceContextFactory.getInstance(Event.class.getName(), actionRequest);
        eventService.addEvent(titleMap, contentMap, eventDate, serviceContext);
    }

    private Date getEventDate(ActionRequest actionRequest) throws PortalException {
        int year = ParamUtil.getInteger(actionRequest, "dateYear");
        int month = ParamUtil.getInteger(actionRequest, "dateMonth");
        int day = ParamUtil.getInteger(actionRequest, "dateDay");
        int hour = ParamUtil.getInteger(actionRequest, "dateHour");
        int minute = ParamUtil.getInteger(actionRequest, "dateMinute");
        int ampm = ParamUtil.getInteger(actionRequest, "dateAmPm");
        if (ampm == Calendar.PM) {
            hour += 12;
        }

        return PortalUtil.getDate(month, day, year, hour, minute, EventDateException.class);
    }
}
