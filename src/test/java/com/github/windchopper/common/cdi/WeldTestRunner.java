package com.github.windchopper.common.cdi;

import org.jboss.weld.environment.se.Weld;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import javax.enterprise.inject.spi.CDI;

public class WeldTestRunner extends BlockJUnit4ClassRunner {

    public WeldTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);

        try {
            CDI.current();
        } catch (IllegalStateException thrown) {
            Weld weld = new Weld();
            weld.initialize();
        }
    }

    @Override protected Object createTest() {
        return CDI.current().select(
            getTestClass().getJavaClass()).get();
    }

}
