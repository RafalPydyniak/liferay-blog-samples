package com.pydyniak.custom.configurations;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@Meta.OCD(
        id = "com.pydyniak.custom.configurations.CustomConfiguration",
        name = "My cool custom configuration"
)
@ExtendedObjectClassDefinition(
        category = "pydyniak",
        scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
public interface CustomConfiguration {
    @Meta.AD(name = "My custom Config", description = "This is just an example configuration")
    String myCustomConfig();
}