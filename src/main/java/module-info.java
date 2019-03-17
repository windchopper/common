module common {

    requires java.naming;
    requires java.annotation;
    requires java.json;
    requires java.prefs;
    requires java.management;
    requires java.logging;
    requires java.desktop;

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javax.inject;
    requires javax.interceptor.api;

    requires cdi.api;

    provides javax.enterprise.inject.spi.Extension with com.github.windchopper.common.cdi.temporary.TemporaryExtension;

}