module windchopper.common.preferences {

    exports com.github.windchopper.common.preferences;
    exports com.github.windchopper.common.preferences.types;

    opens com.github.windchopper.common.preferences;
    opens com.github.windchopper.common.preferences.types;

    requires java.json;
    requires java.logging;
    requires java.naming;
    requires java.prefs;
    requires windchopper.common.util;

}