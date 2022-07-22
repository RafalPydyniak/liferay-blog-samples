package com.pydyniak.blog.samples.not.resolved.bundles.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import java.util.Arrays;

public class UnresolvedBundlesUtil {

    public static String listUnresolvedBundles(BundleContext bundleContext) {
        StringBuilder message = new StringBuilder();
        Arrays.stream(bundleContext.getBundles())
                .filter(UnresolvedBundlesUtil::isInInstalledState)
                .forEach(bundle -> appendBundleToResultMessage(message, bundle));

        return message.toString();
    }

    /**
     * We only look for installed bundles. This is the states in which bundles might have unresolved requirements
     * @param bundle
     * @return
     */
    private static boolean isInInstalledState(Bundle bundle) {
        return Bundle.INSTALLED == bundle.getState();
    }

    private static void appendBundleToResultMessage(StringBuilder message, Bundle bundle) {
        message.append("\n\tBundle {id: ");
        message.append(bundle.getBundleId());
        message.append(", symbolicName: ");
        message.append(bundle.getSymbolicName());
        message.append("} is unresolved");
    }
}
