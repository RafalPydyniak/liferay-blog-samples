package com.pydyniak.blog.samples.event.web.portlet.commands;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.pydyniak.blog.samples.event.constants.EventWebPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

@Component(
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	property = "javax.portlet.name=" + EventWebPortletKeys.EVENTWEB,
	service = ConfigurationAction.class
)
public class EventPortletConfigurationAction extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		boolean displayTermsOfService = ParamUtil.getBoolean(actionRequest, "displayTermsOfService");
		setPreference(
			actionRequest, "displayTermsOfService",
			Boolean.toString(displayTermsOfService));

		SessionMessages.add(actionRequest, "configuration-saved");

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

}