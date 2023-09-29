<%@ taglib prefix="commerce-ui" uri="http://liferay.com/tld/commerce-ui" %>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>
<%@ page import="com.pydyniak.blog.samples.country.custom.settings.constants.CountryCustomSettingsConstants" %>
<%@include file="init.jsp" %>

<%
    long countryId = (long) request.getAttribute(CountryCustomSettingsConstants.PARAM_COUNTRY_ID);
    boolean euCountry = (boolean) request.getAttribute(CountryCustomSettingsConstants.EU_COUNTRY_EXPANDO_KEY);
%>
<portlet:actionURL name="/commerce_country/edit_country_custom_settings"
                   var="editCountryCustomSettingsActionURL"/>

<aui:form action="<%= editCountryCustomSettingsActionURL %>" cssClass="container-fluid container-fluid-max-xl"
          method="post" name="fm">
    <div class="lfr-form-content">
        <div class="sheet">
            <div class="panel-group panel-group-flush">
                <aui:fieldset>
                    <aui:input name="redirect" type="hidden" value="<%= PortalUtil.getCurrentURL(request) %>"/>
                    <aui:input name="countryId" type="hidden" value="<%= countryId %>"/>
                    <aui:input checked='<%=euCountry %>'
                               inlineLabel="right" labelCssClass="simple-toggle-switch" type="toggle-switch"
                               name="<%=CountryCustomSettingsConstants.EU_COUNTRY_EXPANDO_KEY%>"/>

                    <aui:button-row>
                        <aui:button cssClass="btn-lg" type="submit"/>
                    </aui:button-row>
                </aui:fieldset>
            </div>
        </div>
    </div>
</aui:form>