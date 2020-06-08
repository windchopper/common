module windchopper.common.fx {

    exports com.github.windchopper.common.fx;
    exports com.github.windchopper.common.fx.behavior;
    exports com.github.windchopper.common.fx.binding;
    exports com.github.windchopper.common.fx.preferences;
    exports com.github.windchopper.common.fx.search;
    exports com.github.windchopper.common.fx.spinner;

    opens com.github.windchopper.common.fx;
    opens com.github.windchopper.common.fx.behavior;
    opens com.github.windchopper.common.fx.binding;
    opens com.github.windchopper.common.fx.preferences;
    opens com.github.windchopper.common.fx.search;
    opens com.github.windchopper.common.fx.spinner;

    requires java.logging;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.json;
    requires java.prefs;
    requires windchopper.common.preferences;
    requires windchopper.common.util;

}