package com.github.windchopper.common.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface KnownSystemProperties {

    String NAME__OS_ARCHITECTURE = "os.arch";
    String NAME__OS_VERSION = "os.version";
    String NAME__OS_NAME = "os.name";
    String NAME__USER_HOME = "user.home";

    SystemProperty<String> operationSystemArchitecture = new SystemProperty<>(NAME__OS_ARCHITECTURE, stringValue -> stringValue);
    SystemProperty<String> operationSystemVersion = new SystemProperty<>(NAME__OS_VERSION, stringValue -> stringValue);
    SystemProperty<String> operationSystemName = new SystemProperty<>(NAME__OS_NAME, stringValue -> stringValue);
    SystemProperty<File> userHomeFile = new SystemProperty<>(NAME__USER_HOME, File::new);
    SystemProperty<Path> userHomePath = new SystemProperty<>(NAME__USER_HOME, Paths::get);

}
