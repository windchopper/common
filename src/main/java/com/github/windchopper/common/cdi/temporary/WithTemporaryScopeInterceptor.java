package com.github.windchopper.common.cdi.temporary;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import static javax.interceptor.Interceptor.Priority.PLATFORM_BEFORE;

@WithTemporaryScope @Interceptor @Priority(PLATFORM_BEFORE) public class WithTemporaryScopeInterceptor {

    @AroundInvoke public Object intercept(InvocationContext invocationContext) throws Exception {
        return TemporaryContext.extractFromTemporaryScope(invocationContext::proceed);
    }

}
