package com.pydyniak.blog.samples.not.resolved.bundles.service;

import com.liferay.portal.osgi.debug.SystemChecker;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(
    immediate = true,
    property = {},
    service = SystemChecker.class
)
public class UnresolvedBundlesSystemChecker implements SystemChecker{
    private BundleContext bundleContext;

    @Activate
    protected void activate(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public String check() {
        return UnresolvedBundlesUtil.listUnresolvedBundles(bundleContext);
    }


    @Override
    public String getName() {
        return "Unresolved Requirement System Checker";
    }

    @Override
    public String getOSGiCommand() {
        return "pydyniak:unresolved";
    }
}
