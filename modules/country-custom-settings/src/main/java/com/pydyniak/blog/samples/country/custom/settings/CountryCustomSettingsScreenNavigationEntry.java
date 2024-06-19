package com.pydyniak.blog.samples.country.custom.settings;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.pydyniak.blog.samples.country.custom.settings.constants.CountryCustomSettingsConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(
	property = "screen.navigation.entry.order:Integer=10",
	service = ScreenNavigationEntry.class
)
public class CountryCustomSettingsScreenNavigationEntry
	extends CountryCustomSettingsScreenNavigationCategory
	implements ScreenNavigationEntry<Country> {
	private static final Log LOG = LogFactoryUtil.getLog(CountryCustomSettingsScreenNavigationEntry.class);

	@Reference
	private JSPRenderer jspRenderer;

	@Reference(
			target = "(osgi.web.symbolicname=com.pydyniak.blog.samples.country.custom.settings)"
	)
	private ServletContext servletContext;

	@Reference
	private CountryService countryService;

	@Reference
	private ExpandoBridgeFactory expandoBridgeFactory;

	@Reference
	private Portal portal;

	@Override
	public String getEntryKey() {
		return getCategoryKey();
	}

	@Override
	public boolean isVisible(User user, Country country) {
		return country != null;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		long countryId = ParamUtil.get(httpServletRequest, "countryId", 0L);
		Country country = countryService.fetchCountry(countryId);
		long companyId = portal.getCompanyId(httpServletRequest);
		setupEuCountryExpando(companyId);
		boolean euCountry = (boolean) country.getExpandoBridge().getAttribute(CountryCustomSettingsConstants.EU_COUNTRY_EXPANDO_KEY);

		httpServletRequest.setAttribute(CountryCustomSettingsConstants.PARAM_COUNTRY_ID, countryId);
		httpServletRequest.setAttribute(CountryCustomSettingsConstants.EU_COUNTRY_EXPANDO_KEY, euCountry);
		jspRenderer.renderJSP(servletContext, httpServletRequest, httpServletResponse, "/pydyniak/country_custom_settings_view.jsp");
	}

	/**
	 * If euCountry expando has not been yet set up for Country then this method initializes it
	 * @param companyId companyId for which custom field should be initialized
	 */
	private void setupEuCountryExpando(long companyId) {
		try {
			ExpandoBridge expandoBridge = expandoBridgeFactory.getExpandoBridge(companyId, Country.class.getName());
			if (!expandoBridge.hasAttribute(CountryCustomSettingsConstants.EU_COUNTRY_EXPANDO_KEY)) {
				expandoBridge.addAttribute(CountryCustomSettingsConstants.EU_COUNTRY_EXPANDO_KEY, ExpandoColumnConstants.BOOLEAN,
						false, false);
			}
		}catch (PortalException ex) {
			LOG.error("Error while setting up eu country expando", ex);
		}
	}

}
