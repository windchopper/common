package com.github.windchopper.common.cdi.temporary;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class TemporaryExtension implements Extension {

    void addScope(@Observes BeforeBeanDiscovery beforeBeanDiscovery) {
        beforeBeanDiscovery.addScope(
            TemporaryScoped.class,
            true,
            false);
    }

    void addContext(@Observes AfterBeanDiscovery afterBeanDiscovery) {
        afterBeanDiscovery.addContext(
            new TemporaryContext());
    }

}
