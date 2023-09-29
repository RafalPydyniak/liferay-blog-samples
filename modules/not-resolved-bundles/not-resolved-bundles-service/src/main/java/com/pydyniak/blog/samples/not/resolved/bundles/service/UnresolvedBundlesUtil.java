package com.pydyniak.blog.samples.not.resolved.bundles.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UnresolvedBundlesUtil {
    private UnresolvedBundlesUtil(){}

    public static String listUnresolvedBundles(BundleContext bundleContext) {
        return Arrays.stream(bundleContext.getBundles())
                .filter(UnresolvedBundlesUtil::isInInstalledState)
                .map(UnresolvedBundlesUtil::getMessage)
                .collect(Collectors.joining());
    }

    /**
     * We only look for installed bundles. This is the states in which bundles might have unresolved requirements
     * @param bundle
     * @return
     */
    private static boolean isInInstalledState(Bundle bundle) {
        return Bundle.INSTALLED == bundle.getState();
    }

    private static String getMessage(Bundle bundle) {
        StringBuilder message = new StringBuilder();
        message.append("\n\tBundle {id: ");
        message.append(bundle.getBundleId());
        message.append(", symbolicName: ");
        message.append(bundle.getSymbolicName());
        message.append("} is unresolved");
        return message.toString();
    }
}
