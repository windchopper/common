package name.wind.common.cdi.method;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import java.io.Serializable;

public class TemporaryContextExtension implements Extension, Serializable {

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
