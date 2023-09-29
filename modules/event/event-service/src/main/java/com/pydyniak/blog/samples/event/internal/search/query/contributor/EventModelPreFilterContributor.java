package com.pydyniak.blog.samples.event.internal.search.query.contributor;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true,
        property = "indexer.class.name=com.pydyniak.blog.samples.event.model.Event",
        service = ModelPreFilterContributor.class)
public class EventModelPreFilterContributor implements ModelPreFilterContributor {
    @Reference(target = "(model.pre.filter.contributor.id=WorkflowStatus)")
    protected ModelPreFilterContributor workflowStatusModelPreFilterContributor;

    @Override
    public void contribute(BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings, SearchContext searchContext) {
        workflowStatusModelPreFilterContributor.contribute(booleanFilter, modelSearchSettings, searchContext);
    }
}
