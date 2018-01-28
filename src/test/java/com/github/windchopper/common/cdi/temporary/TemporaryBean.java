package com.github.windchopper.common.cdi.temporary;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TemporaryScoped public class TemporaryBean {

    public static boolean postConstructInvoked;
    public static boolean preDestroyInvoked;

    public void test() {
    }

    @PostConstruct private void constructed() {
        postConstructInvoked = true;
    }

    @PreDestroy private void destroying() {
        preDestroyInvoked = true;
    }

}
