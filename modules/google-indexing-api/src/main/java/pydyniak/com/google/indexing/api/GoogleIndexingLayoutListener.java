package pydyniak.com.google.indexing.api;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.indexing.v3.Indexing;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import org.apache.http.HttpRequestFactory;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = ModelListener.class)
public class GoogleIndexingLayoutListener extends BaseModelListener<Layout> {
    @Override
    public void onAfterCreate(Layout model) throws ModelListenerException {
        super.onAfterCreate(model);
    }

    @Override
    public void onAfterRemove(Layout model) throws ModelListenerException {
        super.onAfterRemove(model);
    }

    @Override
    public void onAfterUpdate(Layout originalModel, Layout model) throws ModelListenerException {
        super.onAfterUpdate(originalModel, model);
//        Indexing.
    }
}
