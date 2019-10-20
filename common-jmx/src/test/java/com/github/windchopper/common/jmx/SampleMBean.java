package com.github.windchopper.common.jmx;

import com.github.windchopper.common.jmx.annotations.Description;
import com.github.windchopper.common.jmx.annotations.Impact;
import com.github.windchopper.common.jmx.annotations.Name;

@Description(
    resourceKey = "sample.bean.description",
    resourceBundleName = "com.github.windchopper.common.jmx.i18n.testMessages")
public interface SampleMBean {

    @Description(
        resourceKey = "sample.bean.number.description",
        resourceBundleName = "com.github.windchopper.common.jmx.i18n.testMessages")
    Integer getNumber();
    void setNumber(Integer number);

    @Impact(Impact.ACTION)
    @Description(
        resourceKey = "sample.bean.test.description",
        resourceBundleName = "com.github.windchopper.common.jmx.i18n.testMessages")
    void test(
        @Name(value = "value")
        @Description(
            resourceKey = "sample.bean.test.description",
            resourceBundleName = "com.github.windchopper.common.jmx.i18n.testMessages")
            String value);

}
