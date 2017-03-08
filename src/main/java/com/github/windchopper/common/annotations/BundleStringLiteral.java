package com.github.windchopper.common.annotations;

import java.lang.annotation.Annotation;
import java.util.Objects;

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

    public void merge(BundleString bundleString) {
        if (bundleKey.length() == 0) {
            bundleKey = bundleString.bundleKey();
        }

        if (bundleLocation.length() == 0) {
            bundleLocation = bundleString.bundleLocation();
        }
    }

    @Override
    public String bundleKey() {
        return bundleKey;
    }

    public void setBundleKey(String bundleKey) {
        this.bundleKey = bundleKey;
    }

    @Override
    public String bundleLocation() {
        return bundleLocation;
    }

    public void setBundleLocation(String bundleLocation) {
        this.bundleLocation = bundleLocation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bundleKey, bundleLocation);
    }

    @Override
    public boolean equals(Object that) {
        if (that == this) return true;
        if (that == null || getClass() != that.getClass()) return false;

        BundleStringLiteral thatLiteral = (BundleStringLiteral) that;

        return Objects.equals(bundleKey, thatLiteral.bundleKey)
            && Objects.equals(bundleLocation, thatLiteral.bundleLocation);
    }

}
