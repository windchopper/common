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

        AtomicSimplePropertyDescriptor<SourceBean, String> sp = AtomicSimplePropertyDescriptor.of(SourceBean::getNumberAsString, SourceBean::setNumberAsString);
        AtomicSimplePropertyDescriptor<TargetBean, Double> tp = AtomicSimplePropertyDescriptor.of(TargetBean::getNumberAsDouble, TargetBean::setNumberAsDouble);

        PropertyHandler<SourceBean, String, TargetBean, Double, String, String> p1 = PropertyHandler.of(sp, tp);
        PropertyHandler<SourceBean, String, TargetBean, Double, String, BigDecimal> p2 = p1.convert(BigDecimal::new);
        PropertyHandler<SourceBean, String, TargetBean, Double, BigDecimal, Double> p3 = p2.convert(BigDecimal::doubleValue);
        PropertyHandler<SourceBean, String, TargetBean, Double, Double, Double> p4 = p3.replace();

        tp.setPropertyState(targetBean, p4.apply(sourceBean, targetBean));

        System.out.println(targetBean.getNumberAsDouble());
    }

}
