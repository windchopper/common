package com.github.windchopper.common.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class SystemPropertyProducer extends Producer {

    @Produces
    @ApplicationScoped
    @SystemProperty("any")
    public String produceStringSystemProperty(InjectionPoint injectionPoint) {
        return System.getProperty(findQualifier(injectionPoint, SystemProperty.class).value());
    }

}
