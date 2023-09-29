package com.pydyniak.blog.samples.event.internal.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.*;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.Queries;
import com.pydyniak.blog.samples.event.model.Event;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;

@Component(immediate = true,
        property = {
                "indexer.class.name=com.pydyniak.blog.samples.event.model.Event"
        },
        service = IndexerPostProcessor.class
)
//It's kind of depracated? Contributors should be used instead of IndexerPostProcessor? Same as *Indexer is depracated?
public class EventIndexerPostProcessor implements IndexerPostProcessor {
    private static final Log LOG = LogFactoryUtil.getLog(EventIndexerPostProcessor.class);
    @Reference
    private UserLocalService userLocalService;

    @Reference
    private Queries queries;

    @Override
    public void postProcessContextBooleanFilter(BooleanFilter booleanFilter, SearchContext searchContext) throws Exception {
        LOG.debug("postProcessContextBooleanFilter");
    }

    @Override
    public void postProcessDocument(Document document, Object object) throws Exception {
        LOG.debug("postProcessDocument");
        //Indexing can be updated - for example new field can be indexed
        Event event = (Event) object;
        User user = userLocalService.getUser(event.getUserId());
        document.addText("AuthorScreenName", user.getScreenName());

        //Could be replaced by *ModelDocumentContributor?
    }

    @Override
    public void postProcessFullQuery(BooleanQuery fullQuery, SearchContext searchContext) throws Exception {
        LOG.debug("postProcessFullQuery");
    }

    @Override
    public void postProcessSearchQuery(BooleanQuery searchQuery, BooleanFilter booleanFilter, SearchContext searchContext) throws Exception {
        LOG.debug("postProcessSearchQuery");
//        MatchQuery authorScreenNameQuery = queries.match("AuthorScreenName", searchContext.getKeywords());
//        searchQuery.add(authorScreenNameQuery, BooleanClauseOccur.SHOULD);
    }

    @Override
    public void postProcessSummary(Summary summary, Document document, Locale locale, String snippet) {
        LOG.debug("postProcessSummary");
    }


}
