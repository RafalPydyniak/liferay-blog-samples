package com.pydyniak.blog.samples.event.hooks.filters;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.pydyniak.blog.samples.event.constants.EventWebPortletKeys;
import org.osgi.service.component.annotations.Component;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import java.io.IOException;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + EventWebPortletKeys.EVENTWEB,
                "service.ranking:Integer=100"
        },
        service = PortletFilter.class
)
public class AuditAddEventActionFilter implements ActionFilter {
    private static final Log LOG = LogFactoryUtil.getLog(AuditAddEventActionFilter.class);

    @Override
    public void doFilter(ActionRequest request, ActionResponse response, FilterChain chain) throws IOException, PortletException {
        String actionName = ParamUtil.getString(request, ActionRequest.ACTION_NAME);
        if(Validator.isNotNull(actionName) && actionName.equals(EventWebPortletKeys.MVC_COMMAND_ADD_EVENT)) {
            try {
                User guestOrUser = GuestOrUserUtil.getGuestOrUser();
                LOG.info("Adding event by: " + guestOrUser.getScreenName());
            }catch (Exception ex) {
                LOG.error("ERROR while auditing add event action");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws PortletException {

    }

    @Override
    public void destroy() {

    }


}
