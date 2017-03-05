package com.github.windchopper.common.annotations;

import java.lang.annotation.Annotation;

@SuppressWarnings("ClassExplicitlyAnnotation")
public class BundleStringLiteral implements BundleString {

    private String bundleKey;
    private String bundleLocation;

    public BundleStringLiteral(String bundleKey, String bundleLocation) {
        this.bundleKey = bundleKey;
        this.bundleLocation = bundleLocation;
    }

    public BundleStringLiteral(BundleString bundleString) {
        this(
            bundleString.bundleKey(),
            bundleString.bundleLocation());
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return BundleString.class;
    }

    @Override
    public String bundleKey() {
        return bundleKey;
    }

    public String bundleKey(String newBundleKey) {
        String oldBundleKey = bundleKey;
        bundleKey = newBundleKey;
        return oldBundleKey;
    }

    @Override
    public String bundleLocation() {
        return bundleLocation;
    }

    public String bundleLocation(String newBundleLocation) {
        String oldBundleLocation = bundleLocation;
        bundleLocation = newBundleLocation;
        return oldBundleLocation;
    }

}
