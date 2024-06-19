package com.pydyniak.custom.configurations;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@Meta.OCD(
        factory = true,
        id = "com.pydyniak.custom.configurations.SSOPartnerConfiguration",
        name = "SSO Partners"
)

@ExtendedObjectClassDefinition(
        category = "pydyniak",
        factoryInstanceLabelAttribute = "ssoPartnerName",
        scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
public interface SSOPartnerConfiguration {

    @Meta.AD(required = false, name = "Enabled", deflt = "false",
            description = "Should SSO be enabled for this partner")
    boolean enabled();

    @Meta.AD(name = "SSO Partner Name")
    String ssoPartnerName();
}