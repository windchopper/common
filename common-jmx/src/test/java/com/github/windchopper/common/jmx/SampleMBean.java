package com.github.windchopper.common.jmx;

import com.github.windchopper.common.jmx.annotations.*;

@Description(
    resourceKey = "sample.bean.description",
    resourceBundleName = "com.github.windchopper.common.jmx.i18n.messages")
public interface SampleMBean {

    @Description(
        resourceKey = "sample.bean.number.description",
        resourceBundleName = "com.github.windchopper.common.jmx.i18n.messages")
    Integer getNumber();
    void setNumber(Integer number);

    @Impact(Impact.ACTION)
    @Description(
        resourceKey = "sample.bean.test.description",
        resourceBundleName = "com.github.windchopper.common.jmx.i18n.messages")
    void test(
        @Name(value = "value")
        @Description(
            resourceKey = "sample.bean.test.description",
            resourceBundleName = "com.github.windchopper.common.jmx.i18n.messages")
            String value);

}
