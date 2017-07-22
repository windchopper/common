package com.github.windchopper.common.util.bean;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

@RunWith(JUnit4.class) public class PropertyCopierTest {

    static class SourceBean {

        private String numberAsString;

        public String getNumberAsString() {
            return numberAsString;
        }

        public void setNumberAsString(String numberAsString) {
            this.numberAsString = numberAsString;
        }

    }

    static class TargetBean {

        private Double numberAsDouble;

        public Double getNumberAsDouble() {
            return numberAsDouble;
        }

        public void setNumberAsDouble(Double numberAsDouble) {
            this.numberAsDouble = numberAsDouble;
        }

    }

    @Test public void testAtomic() {
        SourceBean sourceBean = new SourceBean();
        sourceBean.setNumberAsString("1");

        TargetBean targetBean = new TargetBean();
        targetBean.setNumberAsDouble(2.0);

        PropertyCopier.copy(sourceBean, targetBean,
            PropertyCopier.of(PropertyDescriptor.atomic(SourceBean::getNumberAsString), PropertyDescriptor.atomic(TargetBean::setNumberAsDouble))
                .convert(BigDecimal::new)
                .convert(BigDecimal::doubleValue)
                .replace());

        Assert.assertEquals(Double.valueOf(1.0), targetBean.getNumberAsDouble());
    }

}
