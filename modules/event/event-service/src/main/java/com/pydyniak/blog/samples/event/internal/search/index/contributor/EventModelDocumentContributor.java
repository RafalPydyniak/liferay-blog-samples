package com.pydyniak.blog.samples.event.internal.search.index.contributor;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.pydyniak.blog.samples.event.model.Event;
import org.osgi.service.component.annotations.Component;

import java.util.Locale;

@Component(immediate = true,
        property = "indexer.class.name=com.pydyniak.blog.samples.event.model.Event",
        service = ModelDocumentContributor.class)
public class EventModelDocumentContributor implements ModelDocumentContributor<Event> {
    @Override
    public void contribute(Document document, Event event) {
        document.addDate("date", event.getDate());
        for (Locale locale :
                LanguageUtil.getAvailableLocales(event.getGroupId())) {

            String languageId = LocaleUtil.toLanguageId(locale);

            document.addText(
                    LocalizationUtil.getLocalizedName(Field.CONTENT, languageId),
                    event.getContent(locale));
            document.addText(
                    LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
                    event.getTitle(locale));
        }
    }
}
