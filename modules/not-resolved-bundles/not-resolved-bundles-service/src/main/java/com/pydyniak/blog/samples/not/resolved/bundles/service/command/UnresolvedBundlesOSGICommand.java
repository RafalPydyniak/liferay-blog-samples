package com.pydyniak.blog.samples.not.resolved.bundles.service.command;

import com.pydyniak.blog.samples.not.resolved.bundles.service.UnresolvedBundlesUtil;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(
    immediate = true,
    property = {"osgi.command.function=unresolved", "osgi.command.scope=pydyniak"},
    service = UnresolvedBundlesOSGICommand.class
)
public class UnresolvedBundlesOSGICommand {
    private BundleContext bundleContext;

    @Activate
    protected void activate(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }


    public void unresolved() {
        System.out.println(UnresolvedBundlesUtil.listUnresolvedBundles(bundleContext));
    }
}
