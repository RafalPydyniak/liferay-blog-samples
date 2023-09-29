<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@include file="init.jsp"%>


<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
    <liferay-ui:success key="configuration-saved" message="configuration-saved"/>
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

    <liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

    <aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

    <aui:fieldset>
        <aui:input name="displayTermsOfService" type="checkbox" label="configuration.display-terms-of-service"
                   value="<%= Boolean.parseBoolean((String)portletPreferences.getValue("displayTermsOfService", "true")) %>"/>
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>