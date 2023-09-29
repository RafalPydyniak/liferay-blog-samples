<%@ page import="com.pydyniak.blog.samples.event.constants.EventWebPortletKeys" %>
<%@ include file="/init.jsp" %>

<c:if test="<%=EventPermission.contains(permissionChecker, scopeGroupId, "ADD_ENTRY")%>">
    <h2>Add Event</h2>
    <portlet:actionURL name="<%=EventWebPortletKeys.MVC_COMMAND_ADD_EVENT%>" var="addEventUrl"/>
    <aui:form action="<%= addEventUrl%>">
        <aui:field-wrapper label="title">
            <liferay-ui:input-localized name="title" xml="" type="editor"/>
        </aui:field-wrapper>
        <aui:field-wrapper label="description">
            <liferay-ui:input-localized name="content" xml="" type="editor"/>
        </aui:field-wrapper>

        <%
            Calendar defaultCalendar = CalendarFactoryUtil.getCalendar();
            int year = defaultCalendar.get(Calendar.YEAR);
            int month = defaultCalendar.get(Calendar.MONTH);
            int day = defaultCalendar.get(Calendar.DATE);
        %>
        <aui:field-wrapper label="date">
            <liferay-ui:input-date name="date" nullable="<%=false%>"
                                   yearParam="dateYear"
                                   yearValue="<%=year%>"
                                   monthParam="dateMonth"
                                   monthValue="<%=month%>"
                                   dayParam="dateDay"
                                   dayValue="<%=day%>"
                                   showDisableCheckbox="<%=false%>"
                                   firstEnabledDate="<%= new Date() %>"/>
            <liferay-ui:icon
                    icon="calendar"
                    markupView="lexicon"
            />
            <liferay-ui:input-time amPmParam="dateAmPm" hourParam="dateHour" minuteParam="dateMinute"
                                   minuteInterval="15"/>
        </aui:field-wrapper>

        <aui:button type="submit" value="submit"/>
    </aui:form>
</c:if>