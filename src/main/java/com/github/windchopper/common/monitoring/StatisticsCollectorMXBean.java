package com.github.windchopper.common.monitoring;

import com.github.windchopper.common.jmx.annotations.Description;
import com.github.windchopper.common.jmx.annotations.Impact;
import com.github.windchopper.common.jmx.annotations.Name;

import javax.management.MXBean;

@MXBean @Description(
    resourceKey = StatisticsCollectorMXBean.BUNDLE_KEY__DESCRIPTION,
    resourceBundleName = StatisticsCollectorMXBean.BUNDLE)
public interface StatisticsCollectorMXBean extends AnyCollectorMXBean {

    String TYPE = "StatisticsCollector";

    String BUNDLE_KEY__DESCRIPTION = "com.github.windchopper.common.monitoring.StatisticsCollector.description";
    String BUNDLE_KEY__GATHER_STATISTICS__DESCRIPTION = "com.github.windchopper.common.monitoring.StatisticsCollector.gatherStatics.description";
    String BUNDLE_KEY__GATHER_STATISTICS__START_TIME__DESCRIPTION = "com.github.windchopper.common.monitoring.StatisticsCollector.gatherStatics.startTimeSeconds";
    String BUNDLE_KEY__GATHER_STATISTICS__END_TIME__DESCRIPTION = "com.github.windchopper.common.monitoring.StatisticsCollector.gatherStatics.endTimeSeconds";

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
