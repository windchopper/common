package com.github.windchopper.common.util.bean;

import javax.swing.*;
import java.math.BigDecimal;

public class Sample {

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

        private BigDecimal numberAsBigDecimal;

        public BigDecimal getNumberAsBigDecimal() {
            return numberAsBigDecimal;
        }

        public void setNumberAsBigDecimal(BigDecimal numberAsBigDecimal) {
            this.numberAsBigDecimal = numberAsBigDecimal;
        }

    }

    public static void main(String... args) {
        SourceBean sourceBean = new SourceBean();
        TargetBean targetBean = new TargetBean();

        /*
        PropertyCopier.of(sourceBean, targetBean)
            .copying(AtomicSimplePropertyDescriptor.of(SourceBean::getNumberAsString), AtomicSimplePropertyDescriptor.of(TargetBean::setNumberAsBigDecimal))
            .use(PropertyCopyStrategy.convert(BigDecimal::new))
            .use(PropertyCopyStrategy.replace())
            .perform();
         */

        /*
        BeanCopier.of(xmlBean, entityBean)
            .copy(
                PropertyCopier.of(xmlRegistryStatusCode, entityRegistryStatus)
                    .apply(stringToEnumConversion)
                    .apply(replacing),
                PropertyCopier.of(xmlRegistryCurrencyCode, entityRegistryCurrency)
                    .apply(anotherStringToEnumConversion)
                    .apply(replacing)));
         */
    }

}
