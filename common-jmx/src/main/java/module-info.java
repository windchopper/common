module windchopper.common.jmx {

    exports com.github.windchopper.common.jmx;
    exports com.github.windchopper.common.jmx.annotations;

    opens com.github.windchopper.common.jmx;
    opens com.github.windchopper.common.jmx.annotations;

    requires java.management;

}