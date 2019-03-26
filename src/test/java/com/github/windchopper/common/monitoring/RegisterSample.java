package com.github.windchopper.common.monitoring;

import com.github.windchopper.common.jmx.AnnotatedMBean;
import com.github.windchopper.common.util.Pipeliner;

import javax.management.JMException;
import javax.management.ObjectName;
import javax.swing.*;
import java.lang.management.ManagementFactory;
import java.util.Hashtable;
import java.util.UUID;

public class RegisterSample {

    public static void main(String... args) throws JMException {
        var domain = "testDomain";
        var application = "testApplication";
        var uniqueIdentifier = UUID.randomUUID().toString();

        var server = ManagementFactory.getPlatformMBeanServer();

        System.out.println(
            server.registerMBean(
                new AnnotatedMBean(
                    ExpensesCollector.Holder.instance,
                    ExpensesCollectorMXBean.class,
                    true),
                new ObjectName(
                    domain, Pipeliner.of(Hashtable<String, String>::new)
                        .set(map -> value -> map.put(ExpensesCollector.KEY__TYPE, value), ExpensesCollector.TYPE)
                        .set(map -> value -> map.put(ExpensesCollector.KEY__APPLICATION, value), application)
                        .set(map -> value -> map.put(ExpensesCollector.KEY__UNIQUE_IDENTIFIER, value), uniqueIdentifier)
                        .get())));

        System.out.println(
            server.registerMBean(
                new AnnotatedMBean(
                    StatisticsCollector.Holder.instance,
                    StatisticsCollectorMXBean.class,
                    true),
                new ObjectName(
                    domain, Pipeliner.of(Hashtable<String, String>::new)
                    .set(map -> value -> map.put(StatisticsCollector.KEY__TYPE, value), StatisticsCollector.TYPE)
                    .set(map -> value -> map.put(StatisticsCollector.KEY__APPLICATION, value), application)
                    .set(map -> value -> map.put(StatisticsCollector.KEY__UNIQUE_IDENTIFIER, value), uniqueIdentifier)
                    .get())));

        JOptionPane.showMessageDialog(null, "Click for terminate");
        System.exit(0);
    }

}
