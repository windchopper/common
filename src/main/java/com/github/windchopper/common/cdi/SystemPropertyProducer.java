package com.github.windchopper.common.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class SystemPropertyProducer extends Producer {

    @Produces @Dependent @SystemProperty("any") public String produceStringSystemProperty(InjectionPoint injectionPoint) {
        return System.getProperty(findQualifier(injectionPoint, SystemProperty.class).value());
    }

    @Produces @Dependent @SystemProperty("any") public Path producePathSystemProperty(InjectionPoint injectionPoint) {
        return Paths.get(System.getProperty(findQualifier(injectionPoint, SystemProperty.class).value()));
    }

    @Produces @Dependent @SystemProperty("any") public File produceFileSystemProperty(InjectionPoint injectionPoint) {
        return new File(System.getProperty(findQualifier(injectionPoint, SystemProperty.class).value()));
    }

}
