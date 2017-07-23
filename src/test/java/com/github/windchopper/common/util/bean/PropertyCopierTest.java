package com.github.windchopper.common.util.bean;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class) public class PropertyCopierTest {

    static class A {

        private C c;
        private List<B> b = new ArrayList<>();
        private String s;

        public A(String s) {
            this.s = s;
        }

        public C getC() {
            return c;
        }

        public void setC(C c) {
            this.c = c;
        }

        public List<B> getB() {
            return b;
        }

        public void setB(List<B> b) {
            this.b = b;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

    }

    static class B {

        private C c;
        private List<A> a = new ArrayList<>();
        private double d;

        public B(double d) {
            this.d = d;
        }

        public C getC() {
            return c;
        }

        public void setC(C c) {
            this.c = c;
        }

        public List<A> getA() {
            return a;
        }

        public void setA(List<A> a) {
            this.a = a;
        }

        public double getD() {
            return d;
        }

        public void setD(double d) {
            this.d = d;
        }

    }

    static class C {

        private int i;

        public C(Integer i) {
            this.i = i;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

    }

    @Test public void testAtomic() throws ReflectiveOperationException {
        A a = new A("1");
        B b = new B(2.0);
        a.getB().add(b);
        a.setC(new C(3));
        b.getA().add(a);
        a.setC(new C(4));

        PropertyCopier.copy(a, b, PropertyCopier.of(
            AtomicSimplePropertyDescriptor.functional(A::getS),
            AtomicSimplePropertyDescriptor.functional(B::setD))
                .convert(BigDecimal::new)
                .convert(BigDecimal::doubleValue)
                .replace());

        PropertyCopier.copy(a, b, PropertyCopier.of(
            AtomicSimplePropertyDescriptor.reflective(A.class, "s", String.class),
            AtomicSimplePropertyDescriptor.reflective(B.class, "d", double.class))
                .convert(BigDecimal::new)
                .convert(BigDecimal::doubleValue)
                .replace());

        Assert.assertEquals(1.0, b.getD(), 0.0);
    }

}
