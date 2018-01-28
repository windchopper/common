package com.github.windchopper.common.monitoring;

import com.github.windchopper.common.jmx.annotations.Description;

import javax.management.MXBean;

@MXBean
@Description(
    resourceKey = AnyCollectorMXBean.BUNDLE_KEY__DESCRIPTION,
    resourceBundleName = AnyCollectorMXBean.BUNDLE)
public interface AnyCollectorMXBean {

    String KEY__TYPE = "type";
    String KEY__APPLICATION = "application";
    String KEY__UNIQUE_IDENTIFIER = "uniqueIdentifier";

    String BUNDLE = "com.github.windchopper.common.monitoring.i18n.messages";

    String BUNDLE_KEY__DESCRIPTION = "com.github.windchopper.common.monitoring.AnyCollector.description";
    String BUNDLE_KEY__ENABLED__DESCRIPTION = "com.github.windchopper.common.monitoring.AnyCollector.enabled.description";

    @Description(
        resourceKey = BUNDLE_KEY__ENABLED__DESCRIPTION,
        resourceBundleName = BUNDLE)
    boolean isEnabled();
    void setEnabled(boolean enabled);

}
