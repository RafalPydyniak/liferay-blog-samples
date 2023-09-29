package com.pydyniak.blog.samples.event.internal.search.index.contributor;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;
import com.pydyniak.blog.samples.event.model.Event;
import com.pydyniak.blog.samples.event.service.EventLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true,
        property = "indexer.class.name=com.pydyniak.blog.samples.event.model.Event",
        service = ModelIndexerWriterContributor.class
)
public class EventModelIndexerWriterContributor implements ModelIndexerWriterContributor<Event> {
    @Reference
    protected DynamicQueryBatchIndexingActionableFactory
            dynamicQueryBatchIndexingActionableFactory;

    @Reference
    private EventLocalService eventLocalService;
    @Override
    public void customize(BatchIndexingActionable batchIndexingActionable, ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {
        batchIndexingActionable.setPerformActionMethod((Event event) -> {
            Document document = modelIndexerWriterDocumentHelper.getDocument(
                    event);

            batchIndexingActionable.addDocuments(document);

        });
    }

    @Override
    public BatchIndexingActionable getBatchIndexingActionable() {
        return dynamicQueryBatchIndexingActionableFactory.getBatchIndexingActionable(eventLocalService.getIndexableActionableDynamicQuery());
    }

    @Override
    public long getCompanyId(Event event) {
        return event.getCompanyId();
    }
}
