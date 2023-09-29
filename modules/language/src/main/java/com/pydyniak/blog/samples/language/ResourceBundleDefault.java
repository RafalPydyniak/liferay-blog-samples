package com.pydyniak.blog.samples.language;

import com.liferay.portal.kernel.language.UTF8Control;
import org.osgi.service.component.annotations.Component;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Resource bundle for all languages (default)
 */
@Component(
        immediate = true,
        property = {"language.id="},
        service = ResourceBundle.class
)
public class ResourceBundleDefault extends ResourceBundle {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("content.Language", UTF8Control.INSTANCE);

    @Override
    protected Object handleGetObject(String key) {
        return resourceBundle.getObject(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return resourceBundle.getKeys();
    }
}