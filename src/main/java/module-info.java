module windchopper.common {

    exports com.github.windchopper.common.cdi;
    exports com.github.windchopper.common.cdi.temporary;
    exports com.github.windchopper.common.fx;
    exports com.github.windchopper.common.fx.annotation;
    exports com.github.windchopper.common.fx.application;
    exports com.github.windchopper.common.fx.behavior;
    exports com.github.windchopper.common.fx.binding;
    exports com.github.windchopper.common.fx.dialog;
    exports com.github.windchopper.common.fx.event;
    exports com.github.windchopper.common.fx.preferences;
    exports com.github.windchopper.common.fx.search;
    exports com.github.windchopper.common.fx.spinner;
    exports com.github.windchopper.common.fx.util;
    exports com.github.windchopper.common.jmx;
    exports com.github.windchopper.common.jmx.annotations;
    exports com.github.windchopper.common.monitoring;
    exports com.github.windchopper.common.ng;
    exports com.github.windchopper.common.preferences;
    exports com.github.windchopper.common.preferences.types;
    exports com.github.windchopper.common.util;
    exports com.github.windchopper.common.util.bean;
    exports com.github.windchopper.common.util.concurrent;
    exports com.github.windchopper.common.util.stream;

    requires java.naming;
    requires java.annotation;
    requires java.json;
    requires java.prefs;
    requires java.management;
    requires java.logging;
    requires java.desktop;

    requires javax.inject;
    requires javax.interceptor.api;

    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires cdi.api;

    provides javax.enterprise.inject.spi.Extension
        with com.github.windchopper.common.cdi.temporary.TemporaryExtension;

}