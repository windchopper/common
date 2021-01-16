package com.github.windchopper.common.monitoring;

import com.github.windchopper.common.jmx.annotations.*;

import javax.management.MXBean;

@MXBean @Description(
    resourceKey = StatisticsCollectorMXBean.BUNDLE_KEY__DESCRIPTION,
    resourceBundleName = StatisticsCollectorMXBean.BUNDLE)
public interface StatisticsCollectorMXBean {

    String KEY__TYPE = "type";
    String KEY__APPLICATION = "application";
    String KEY__UNIQUE_IDENTIFIER = "uniqueIdentifier";

    String BUNDLE = "com.github.windchopper.common.monitoring.i18n.messages";

    String TYPE = "StatisticsCollector";

    String BUNDLE_KEY__DESCRIPTION = "com.github.windchopper.common.monitoring.StatisticsCollector.description";
    String BUNDLE_KEY__ENABLED__DESCRIPTION = "com.github.windchopper.common.monitoring.StatisticsCollector.enabled.description";
    String BUNDLE_KEY__GATHER_STATISTICS__DESCRIPTION = "com.github.windchopper.common.monitoring.StatisticsCollector.gatherStatics.description";
    String BUNDLE_KEY__GATHER_STATISTICS__START_TIME__DESCRIPTION = "com.github.windchopper.common.monitoring.StatisticsCollector.gatherStatics.startTimeSeconds";
    String BUNDLE_KEY__GATHER_STATISTICS__END_TIME__DESCRIPTION = "com.github.windchopper.common.monitoring.StatisticsCollector.gatherStatics.endTimeSeconds";

    @Description(
        resourceKey = BUNDLE_KEY__ENABLED__DESCRIPTION,
        resourceBundleName = BUNDLE)
    boolean isEnabled();
    void setEnabled(boolean enabled);

    @Impact(Impact.INFO) @Description(
        resourceKey = BUNDLE_KEY__GATHER_STATISTICS__DESCRIPTION,
        resourceBundleName = BUNDLE)
    Statistics[] gatherStatistics(
        @Name("startTimeSeconds") @Description(
            resourceKey = BUNDLE_KEY__GATHER_STATISTICS__START_TIME__DESCRIPTION,
            resourceBundleName = BUNDLE)
            long startTimeSeconds,
        @Name("endTimeSeconds") @Description(
            resourceKey = BUNDLE_KEY__GATHER_STATISTICS__END_TIME__DESCRIPTION,
            resourceBundleName = BUNDLE)
            long endTimeSeconds);

}
