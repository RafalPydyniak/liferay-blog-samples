package com.pydyniak.blog.samples.event.internal.search.result.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import org.osgi.service.component.annotations.Component;

import java.util.Locale;


@Component(immediate = true,
        property = "indexer.class.name=com.pydyniak.blog.samples.event.model.Event",
        service = ModelSummaryContributor.class)
public class EventModelSummaryContributor implements ModelSummaryContributor {
    @Override
    public Summary getSummary(Document document, Locale locale, String snippet) {
        String languageId = LocaleUtil.toLanguageId(locale);
        return createSummary(document, LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
                LocalizationUtil.getLocalizedName(Field.CONTENT, languageId));
    }

    protected Summary createSummary(
            Document document, String titleField, String contentField) {

        String prefix = Field.SNIPPET + StringPool.UNDERLINE;

        String title = document.get(prefix + titleField, titleField);
        String content = document.get(prefix + contentField, contentField);

        Summary summary = new Summary(title, content);
        summary.setMaxContentLength(200);
        return summary;
    }
}
