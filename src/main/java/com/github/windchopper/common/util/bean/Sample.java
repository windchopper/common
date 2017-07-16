package com.github.windchopper.common.util.bean;

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

        private Double numberAsDouble;

        public Double getNumberAsDouble() {
            return numberAsDouble;
        }

        public void setNumberAsDouble(Double numberAsDouble) {
            this.numberAsDouble = numberAsDouble;
        }

    }

    public static void main(String... args) {
        SourceBean sourceBean = new SourceBean();
        sourceBean.setNumberAsString("1");
        TargetBean targetBean = new TargetBean();
        targetBean.setNumberAsDouble(2.0);

        PropertyHandler<SourceBean, String, TargetBean, Double, String, String> p1 = PropertyHandler.of(AtomicSimplePropertyDescriptor.of(SourceBean::getNumberAsString), AtomicSimplePropertyDescriptor.of(TargetBean::setNumberAsDouble));
        PropertyHandler<SourceBean, String, TargetBean, Double, String, BigDecimal> p2 = p1.convert(BigDecimal::new);
        PropertyHandler<SourceBean, String, TargetBean, Double, BigDecimal, Double> p3 = p2.convert(BigDecimal::doubleValue);
        PropertyHandler<SourceBean, String, TargetBean, Double, Double, Double> replace = p3.replace();

        BeanCopier.of(sourceBean, targetBean)
            .copy(PropertyHandler.of(AtomicSimplePropertyDescriptor.of(SourceBean::getNumberAsString, SourceBean::setNumberAsString), AtomicSimplePropertyDescriptor.of(TargetBean::getNumberAsDouble, TargetBean::setNumberAsDouble))
                .convert(BigDecimal::new)
                .convert(BigDecimal::doubleValue)
                .replace());
        ;

        System.out.println(targetBean.getNumberAsDouble());
    }

}
