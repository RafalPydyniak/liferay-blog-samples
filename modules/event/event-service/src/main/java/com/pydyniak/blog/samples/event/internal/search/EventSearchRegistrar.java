package com.pydyniak.blog.samples.event.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.pydyniak.blog.samples.event.model.Event;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class EventSearchRegistrar {

    private ServiceRegistration<?> _serviceRegistration;

    @Reference
    private ModelSearchRegistrarHelper _modelSearchRegistrarHelper;

    @Reference(
            target = "(indexer.class.name=com.pydyniak.blog.samples.event.model.Event)"
    )
    private ModelIndexerWriterContributor<Event>
            _modelIndexWriterContributor;

    @Reference(
            target = "(indexer.class.name=com.pydyniak.blog.samples.event.model.Event)"
    )
    private ModelSummaryContributor _modelSummaryContributor;

    @Activate
    protected void activate(BundleContext bundleContext) {
        _serviceRegistration = _modelSearchRegistrarHelper.register(Event.class, bundleContext, modelSearchDefinition -> {
            modelSearchDefinition.setDefaultSelectedFieldNames(Field.COMPANY_ID,
                    Field.ENTRY_CLASS_PK, Field.ENTRY_CLASS_NAME, Field.UID);
            modelSearchDefinition.setDefaultSelectedLocalizedFieldNames(Field.CONTENT, Field.TITLE);
            modelSearchDefinition.setModelIndexWriteContributor(_modelIndexWriterContributor);
            modelSearchDefinition.setModelSummaryContributor(_modelSummaryContributor);
            modelSearchDefinition.setSelectAllLocales(true);
        });
    }

    @Deactivate
    protected void deactivate() {
        _serviceRegistration.unregister();
    }
}
