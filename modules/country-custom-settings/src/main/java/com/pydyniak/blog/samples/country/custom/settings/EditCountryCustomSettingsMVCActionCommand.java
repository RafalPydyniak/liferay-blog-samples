package com.pydyniak.blog.samples.country.custom.settings;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.pydyniak.blog.samples.country.custom.settings.constants.CountryCustomSettingsConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

@Component(immediate = true,
        property = {
                "javax.portlet.name=" + CommercePortletKeys.COMMERCE_COUNTRY,
                "mvc.command.name=/commerce_country/edit_country_custom_settings"
        },
        service = MVCActionCommand.class
)
public class EditCountryCustomSettingsMVCActionCommand extends BaseMVCActionCommand {
    @Reference
    private CountryService countryService;

    @Override
    protected void doProcessAction(
            ActionRequest actionRequest, ActionResponse actionResponse) {
        long countryId = ParamUtil.getLong(actionRequest, CountryCustomSettingsConstants.PARAM_COUNTRY_ID);
        boolean euCountry = ParamUtil.getBoolean(actionRequest, CountryCustomSettingsConstants.EU_COUNTRY_EXPANDO_KEY);
        Country country = countryService.fetchCountry(countryId);
        country.getExpandoBridge().setAttribute(CountryCustomSettingsConstants.EU_COUNTRY_EXPANDO_KEY, euCountry);
    }
}