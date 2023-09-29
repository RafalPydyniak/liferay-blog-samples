package com.pydyniak.blog.samples.country.custom.settings;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.LanguageUtil;
import org.osgi.service.component.annotations.Component;

import java.util.Locale;

@Component(
        property = "screen.navigation.category.order:Integer=100",
        service = ScreenNavigationCategory.class
)
public class CountryCustomSettingsScreenNavigationCategory
        implements ScreenNavigationCategory {
    @Override
    public String getCategoryKey() {
        return "pydyniak-country-custom-settings";
    }

    @Override
    public String getLabel(Locale locale) {
        return LanguageUtil.get(locale, "custom-settings");
    }

    @Override
    public String getScreenNavigationKey() {
        return "commerce.country.general";
    }
}