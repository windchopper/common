module windchopper.common.fx.cdi {

    exports com.github.windchopper.common.fx.cdi;
    exports com.github.windchopper.common.fx.cdi.form;

    opens com.github.windchopper.common.fx.cdi;
    opens com.github.windchopper.common.fx.cdi.form;

    requires java.logging;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.inject.api;
    requires jakarta.enterprise.cdi.api;
    requires windchopper.common.cdi;
    requires windchopper.common.fx;
    requires windchopper.common.util;

}