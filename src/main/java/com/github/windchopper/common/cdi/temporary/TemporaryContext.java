package com.github.windchopper.common.cdi.temporary;

import com.github.windchopper.common.util.stream.FailableRunnable;
import com.github.windchopper.common.util.stream.FailableSupplier;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.spi.AlterableContext;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TemporaryContext implements AlterableContext {

    private final static ThreadLocal<Map<Contextual<?>, TemporaryInstance<?>>> instancesReference = new InheritableThreadLocal<>();

    /*
     *
     */

    @SuppressWarnings("unchecked") @Override public <T> T get(Contextual<T> contextual) {
        return (T) Optional.ofNullable(Optional.ofNullable(instancesReference.get())
            .orElseThrow(ContextNotActiveException::new)
            .get(contextual))
            .map(TemporaryInstance::getObject)
            .orElse(null);
    }

    @SuppressWarnings("unchecked") @Override public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        return (T) Optional.ofNullable(instancesReference.get())
            .orElseThrow(ContextNotActiveException::new)
            .computeIfAbsent(contextual, key -> new TemporaryInstance<>(contextual, creationalContext))
            .getObject();
    }

    @Override public void destroy(Contextual<?> contextual) {
        Optional.ofNullable(instancesReference.get())
            .orElseThrow(ContextNotActiveException::new)
            .values()
            .forEach(TemporaryInstance::destroyObject);
    }

    @Override public boolean isActive() {
        return instancesReference.get() != null;
    }

    @Override public Class<? extends Annotation> getScope() {
        return TemporaryScoped.class;
    }

    /*
     *
     */

    public static <E extends Throwable> void executeWithTemporaryScope(FailableRunnable<E> runnable) throws E {
        var instances = instancesReference.get();
        var clean = instances == null;

        if (clean) {
            instancesReference.set(instances = new HashMap<>());
        }

        try {
            runnable.run();
        } finally {
            if (clean) {
                instances.values().forEach(TemporaryInstance::destroyObject);
                instancesReference.remove();
            }
        }
    }

    public static <T, E extends Throwable> T extractFromTemporaryScope(FailableSupplier<T, E> supplier) throws E {
        var instances = instancesReference.get();
        var clean = instances == null;

        if (clean) {
            instancesReference.set(instances = new HashMap<>());
        }

        try {
            return supplier.get();
        } finally {
            if (clean) {
                instances.values().forEach(TemporaryInstance::destroyObject);
                instancesReference.remove();
            }
        }
    }

}
