module windchopper.common.fx {

    exports com.github.windchopper.common.fx;
    opens com.github.windchopper.common.fx;

    requires java.logging;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires cdi.api;
    requires java.json;
    requires windchopper.common.cdi;
    requires windchopper.common.preferences;
    requires windchopper.common.util;

}