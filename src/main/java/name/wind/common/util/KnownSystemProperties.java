package name.wind.common.util;

public interface KnownSystemProperties {

    String NAME__OS_ARCHITECTURE = "os.arch";
    String NAME__OS_VERSION = "os.version";
    String NAME__OS_NAME = "os.name";

    SystemProperty<String> PROPERTY__OS_ARCHITECTURE = new SystemProperty<>(NAME__OS_ARCHITECTURE, stringValue -> stringValue);
    SystemProperty<String> PROPERTY__OS_VERSION = new SystemProperty<>(NAME__OS_VERSION, stringValue -> stringValue);
    SystemProperty<String> PROPERTY__OS_NAME = new SystemProperty<>(NAME__OS_NAME, stringValue -> stringValue);

}
