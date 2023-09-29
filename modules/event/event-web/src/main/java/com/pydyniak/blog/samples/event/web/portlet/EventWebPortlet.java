package com.pydyniak.blog.samples.event.web.portlet;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.*;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.pydyniak.blog.samples.event.model.Event;
import com.pydyniak.blog.samples.event.service.EventLocalService;
import com.pydyniak.blog.samples.event.constants.EventWebPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rafal
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=EventWeb",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + EventWebPortletKeys.EVENTWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.init-param.config-template=/configuration.jsp",
	},
	service = Portlet.class
)
public class EventWebPortlet extends MVCPortlet {

	@Reference
	private SearchRequestBuilderFactory searchRequestBuilderFactory;

	@Reference
	private Searcher searcher;

	@Reference
	private Queries queries;

	@Reference
	private EventLocalService eventLocalService;

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		SearchRequestBuilder builder = searchRequestBuilderFactory.builder();
		builder.emptySearchEnabled(true);
		builder.withSearchContext(searchContext -> {
			searchContext.setCompanyId(themeDisplay.getCompanyId());
			String keywords = ParamUtil.getString(renderRequest, "keywords");
			searchContext.setKeywords(keywords);
		});
		MatchQuery eventClassNameQuery = queries.match(Field.ENTRY_CLASS_NAME, Event.class.getName());

		SearchRequest searchRequest = builder.query(eventClassNameQuery).build();
		SearchResponse search = searcher.search(searchRequest);
		SearchHits searchHits = search.getSearchHits();
		int totalHits = search.getTotalHits();

		List<Event> events = searchHits.getSearchHits().stream().map(searchHit -> {
			Document document = searchHit.getDocument();
			Long eventId = document.getLong(Field.ENTRY_CLASS_PK);
			Event event = eventLocalService.fetchEvent(eventId);
			return event;
		}).collect(Collectors.toList());
//
//		SearchContainer<Event> searchContainer = new SearchContainer<>(renderRequest,
//				PortletURLUtil.getCurrent(renderRequest, renderResponse),
//				null, "no-entries-were-found");
//		searchContainer.setId("eventsSearchContainer");
//		searchContainer.setResultsAndTotal(() -> events, totalHits);
//
//		renderRequest.setAttribute("searchContainer", searchContainer);
		super.render(renderRequest, renderResponse);
	}
}