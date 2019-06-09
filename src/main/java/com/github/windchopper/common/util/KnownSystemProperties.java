package com.github.windchopper.common.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public interface KnownSystemProperties {

    String NAME__OS_ARCHITECTURE = "os.arch";
    String NAME__OS_VERSION = "os.version";
    String NAME__OS_NAME = "os.name";
    String NAME__USER_HOME = "user.home";
    String NAME__USER_NAME = "user.name";

    SystemProperty<String> operationSystemArchitecture = new SystemProperty<>(NAME__OS_ARCHITECTURE, Function.identity());
    SystemProperty<String> operationSystemVersion = new SystemProperty<>(NAME__OS_VERSION, Function.identity());
    SystemProperty<String> operationSystemName = new SystemProperty<>(NAME__OS_NAME, Function.identity());
    SystemProperty<File> userHomeFile = new SystemProperty<>(NAME__USER_HOME, File::new);
    SystemProperty<Path> userHomePath = new SystemProperty<>(NAME__USER_HOME, Paths::get);
    SystemProperty<String> userName = new SystemProperty<>(NAME__USER_NAME, Function.identity());

}
