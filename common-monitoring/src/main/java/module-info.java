module windchopper.common.monitoring {

    exports com.github.windchopper.common.monitoring;
    opens com.github.windchopper.common.monitoring;

    requires java.management;
    requires windchopper.common.jmx;

}