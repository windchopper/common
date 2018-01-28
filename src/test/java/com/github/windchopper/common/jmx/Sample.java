package com.github.windchopper.common.jmx;

public class Sample implements SampleMBean {

    private Integer number;

    @Override
    public Integer getNumber() {
        return number;
    }

    @Override
    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public void test(String value) {
        System.out.println(value);
    }

}
