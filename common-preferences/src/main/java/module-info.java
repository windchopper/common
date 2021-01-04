module windchopper.common.preferences {

    exports com.github.windchopper.common.preferences;
    exports com.github.windchopper.common.preferences.entries;
    exports com.github.windchopper.common.preferences.types;
    exports com.github.windchopper.common.preferences.storages;

    opens com.github.windchopper.common.preferences;
    opens com.github.windchopper.common.preferences.entries;
    opens com.github.windchopper.common.preferences.types;
    opens com.github.windchopper.common.preferences.storages;
    opens com.github.windchopper.common.preferences.i18n;

    requires java.json;
    requires java.logging;
    requires java.naming;
    requires java.prefs;
    requires windchopper.common.util;

}