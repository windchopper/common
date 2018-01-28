package com.github.windchopper.common.cdi;

import org.jboss.weld.bean.proxy.CombinedInterceptorAndDecoratorStackMethodHandler;
import org.jboss.weld.bean.proxy.InterceptionDecorationContext;
import org.jboss.weld.bean.proxy.MethodHandler;
import org.jboss.weld.bean.proxy.ProxyObject;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class ProperInterceptorMethodHandler extends CombinedInterceptorAndDecoratorStackMethodHandler {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.cdi.i18n.messages");

    private ProperInterceptorMethodHandler(CombinedInterceptorAndDecoratorStackMethodHandler replacedMethodHandler) {
        setInterceptorMethodHandler(replacedMethodHandler.getInterceptorMethodHandler());
        setOuterDecorator(replacedMethodHandler.getOuterDecorator());
    }

    public static void install(Object probablyProxy) {
        if (probablyProxy instanceof ProxyObject) {
            MethodHandler methodHandler = ((ProxyObject) probablyProxy).getHandler();
            if (methodHandler instanceof CombinedInterceptorAndDecoratorStackMethodHandler) {
                ((ProxyObject) probablyProxy).setHandler(
                    new ProperInterceptorMethodHandler(
                        (CombinedInterceptorAndDecoratorStackMethodHandler) methodHandler));
            } else {
                throw new IllegalArgumentException(String.format(
                    bundle.getString("com.github.windchopper.common.cdi.ProperInterceptorMethodHandler.unexpectedHandler"),
                    methodHandler));
            }
        } else {
            throw new IllegalArgumentException(String.format(
                bundle.getString("com.github.windchopper.common.cdi.ProperInterceptorMethodHandler.notProxy"),
                probablyProxy));
        }
    }

    @Override public Object invoke(InterceptionDecorationContext.Stack stack, Object self, Method thisMethod, Method proceed, Object[] args, boolean intercept, boolean popStack) throws Throwable {
        return super.invoke(stack, self, thisMethod, proceed, args, true, popStack);
    }

    @Override public boolean isDisabledHandler(InterceptionDecorationContext.Stack stack) {
        return false;
    }

}
