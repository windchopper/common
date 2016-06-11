package name.wind.common.cdi.method;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

import static javax.interceptor.Interceptor.Priority.PLATFORM_BEFORE;

@WithTemporaryScope @Interceptor @Priority(PLATFORM_BEFORE) public class WithTemporaryScopeInterceptor implements Serializable {

    @AroundInvoke public Object intercept(InvocationContext invocationContext) throws Exception {
        return TemporaryContext.callWithinTemporaryScope(invocationContext::proceed);
    }

}
