package com.github.windchopper.common.monitoring;

import com.github.windchopper.common.jmx.annotations.Description;

import javax.management.MXBean;

@MXBean @Description(
    resourceKey = ExpensesCollectorMXBean.BUNDLE_KEY__DESCRIPTION,
    resourceBundleName = ExpensesCollectorMXBean.BUNDLE)
public interface ExpensesCollectorMXBean extends AnyCollectorMXBean {

    String TYPE = "ExpensesCollector";

    String BUNDLE_KEY__DESCRIPTION = "com.github.windchopper.common.monitoring.ExpensesCollector.description";
    String BUNDLE_KEY__OPERATION_STATISTICS_TIME__DESCRIPTION = "com.github.windchopper.common.monitoring.ExpensesCollector.operationStatisticsTimeNanoseconds.description";
    String BUNDLE_KEY__OPERATION_COUNT__DESCRIPTION = "com.github.windchopper.common.monitoring.ExpensesCollector.operationCount.description";

    @Description(
        resourceKey = BUNDLE_KEY__OPERATION_STATISTICS_TIME__DESCRIPTION,
        resourceBundleName = BUNDLE)
    long getOperationStatisticsTimeNanoseconds();

    @Description(
        resourceKey = BUNDLE_KEY__OPERATION_COUNT__DESCRIPTION,
        resourceBundleName = BUNDLE)
    long getOperationCount();

}
