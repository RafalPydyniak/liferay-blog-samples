<%@ taglib prefix="liferay-security" uri="http://liferay.com/tld/security" %>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %>
<%@ page import="com.pydyniak.blog.samples.event.model.Event" %>
<%@ page import="com.liferay.taglib.search.ResultRow" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.pydyniak.blog.samples.event.web.internal.security.permission.resource.EventModelPermission" %>
<%@ page import="com.liferay.portal.kernel.security.permission.ActionKeys" %>
<%@ page import="com.pydyniak.blog.samples.event.constants.EventWebPortletKeys" %>
<%@include file="init.jsp" %>

<%
    ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
    Event event = (Event) row.getObject();
%>
<liferay-ui:icon-menu>
    <c:if test="<%= EventModelPermission.contains(permissionChecker, event, ActionKeys.DELETE) %>">
        <portlet:actionURL name="<%=EventWebPortletKeys.MVC_COMMAND_DELETE_EVENT%>" var="deleteEventURL">
            <portlet:param name="eventId" value="<%=Long.toString(event.getEventId())%>"/>
        </portlet:actionURL>
        <liferay-ui:icon message="delete" url="<%=deleteEventURL%>"/>
    </c:if>

    <c:if test="<%= EventModelPermission.contains(permissionChecker, event, ActionKeys.PERMISSIONS)%>">
        <liferay-security:permissionsURL
                modelResource="<%= Event.class.getName() %>"
                modelResourceDescription="<%= event.getTitle(locale) %>"
                resourceGroupId="<%= String.valueOf(event.getGroupId()) %>"
                resourcePrimKey="<%= String.valueOf(event.getEventId()) %>"
                var="permissionsEntryURL"
                windowState="<%= LiferayWindowState.POP_UP.toString() %>"
        />
        <liferay-ui:icon message="permissions" method="get"
                         url="<%=permissionsEntryURL%>"
                         useDialog="<%=true%>"
                         label="<%=true%>"/>
    </c:if>
</liferay-ui:icon-menu>