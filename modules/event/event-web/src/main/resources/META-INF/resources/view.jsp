<%@ include file="/init.jsp" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.pydyniak.blog.samples.event.model.Event" %>
<%@ page import="com.liferay.portal.kernel.search.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.pydyniak.blog.samples.event.service.EventLocalService" %>
<%@ page import="com.pydyniak.blog.samples.event.service.EventLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.dao.search.SearchContainer" %>
<%@ page import="com.liferay.portal.kernel.dao.search.SearchPaginationUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.pydyniak.blog.samples.event.constants.EventWebPortletKeys" %>
<%@include file="init.jsp" %>

<h2>Events</h2>

<portlet:renderURL var="searchURL">
    <portlet:param name="mvcPath" value="/view.jsp"/>
</portlet:renderURL>

<aui:form action="${searchURL}" name="fm">
    <div class="row">
        <div class="col-md-8">
            <aui:input inlineLabel="left" label="" name="keywords" placeholder="search-entries" size="256"/>
        </div>

        <div class="col-md-4">
            <aui:button type="submit" value="search"/>
        </div>
    </div>
</aui:form>

<c:if test="<%=EventPermission.contains(permissionChecker, scopeGroupId, "ADD_ENTRY")%>">
    <portlet:renderURL var="addEventRenderURL">
        <portlet:param name="mvcRenderCommandName" value="<%=EventWebPortletKeys.MVC_COMMAND_RENDER_ADD_EVENT%>"/>
    </portlet:renderURL>

    <a class="btn btn-primary" href="<%=addEventRenderURL%>">
        Add event
    </a>
</c:if>

<c:if test="<%=Boolean.parseBoolean((String)portletPreferences.getValue("displayTermsOfService", "true"))%>">
    <portlet:resourceURL id="<%=EventWebPortletKeys.MVC_COMMAND_DOWNLOAD_TERMS_OF_SERVICE%>" var="termsOfServiceDownloadURL"/>
    <aui:a target="_blank" href="<%=termsOfServiceDownloadURL%>" cssClass="btn btn-primary">
        Terms of service
    </aui:a>
</c:if>

<%
    String keywords = ParamUtil.getString(request, "keywords");
    if (keywords == null) {
        keywords = "";
    }

    int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM, SearchContainer.DEFAULT_DELTA);
    int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_CUR);
    int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(cur, delta);
    int start = startAndEnd[0];
    int end = startAndEnd[1];
%>

<%
    SearchContext searchContext = SearchContextFactory.getInstance(request);
    searchContext.setKeywords(keywords);
    searchContext.setAttribute("paginationType", "more");
    searchContext.setStart(start);
    searchContext.setEnd(end);


    Indexer<Event> indexer = IndexerRegistryUtil.getIndexer(Event.class);
    Hits hits = indexer.search(searchContext);
    List<Event> entries = new ArrayList<>();

    for (int i = 0; i < hits.getDocs().length; i++) {
        Document doc = hits.doc(i);
        long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));
        //EventDTO could be created instead so the database is not called at all
        Event event = EventLocalServiceUtil.getEvent(entryId);
        entries.add(event);
    }
%>

<liferay-ui:search-container delta="<%=delta%>"
                             emptyResultsMessage="no-entries-were-found"
                             total="<%= hits.getLength()%>">
    <liferay-ui:search-container-results results="<%=entries%>"/>

    <liferay-ui:search-container-row className="com.pydyniak.blog.samples.event.model.Event"
                                     keyProperty="eventId" modelVar="event" escapedModel="<%=true%>">
        <liferay-ui:search-container-column-text property="eventId"/>
        <%
            //            Need to make sure that title & description is properly sanitized when adding in LocalService
            String title = event.getTitle(locale);
            String unescapedTitle = HtmlUtil.render(title);

            String description = event.getContent(locale);
            String unescapedDescription = HtmlUtil.render(description);
        %>
        <liferay-ui:search-container-column-text name="title" value="<%=unescapedTitle%>"/>
        <liferay-ui:search-container-column-text name="content" value="<%=unescapedDescription%>"/>
        <liferay-ui:search-container-column-date property="date"/>
        <liferay-ui:search-container-column-jsp name="actions" path="/event_actions.jsp"/>
    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator/>
</liferay-ui:search-container>