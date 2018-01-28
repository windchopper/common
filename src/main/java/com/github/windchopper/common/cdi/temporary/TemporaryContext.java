package com.github.windchopper.common.cdi.temporary;

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
        return (T) Optional.ofNullable(Optional.ofNullable(instancesReference.get()).orElseThrow(ContextNotActiveException::new).get(contextual)).map(TemporaryInstance::getObject).orElse(null);
    }

    @SuppressWarnings("unchecked") @Override public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        return (T) Optional.ofNullable(instancesReference.get()).orElseThrow(ContextNotActiveException::new).computeIfAbsent(contextual, key -> new TemporaryInstance<>(contextual, creationalContext)).getObject();
    }

    @Override public void destroy(Contextual<?> contextual) {
        Optional.ofNullable(instancesReference.get()).orElseThrow(ContextNotActiveException::new).values().forEach(TemporaryInstance::destroyObject);
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

    @FunctionalInterface public interface ExceptionExecutor<E extends Exception> {
        void execute() throws E;
    }

    public static <E extends Exception> void executeWithTemporaryScope(ExceptionExecutor<E> executor) throws E {
        Map<Contextual<?>, TemporaryInstance<?>> instances = instancesReference.get();
        boolean clean = instances == null;

        if (clean) {
            instancesReference.set(instances = new HashMap<>());
        }

        try {
            executor.execute();
        } finally {
            if (clean) {
                instances.values().forEach(TemporaryInstance::destroyObject);
                instancesReference.remove();
            }
        }
    }

    @FunctionalInterface public interface ExceptionExtractor<T, E extends Exception> {
        T extract() throws E;
    }

    public static <T, E extends Exception> T extractFromTemporaryScope(ExceptionExtractor<T, E> extractor) throws E {
        Map<Contextual<?>, TemporaryInstance<?>> instances = instancesReference.get();
        boolean clean = instances == null;

        if (clean) {
            instancesReference.set(instances = new HashMap<>());
        }

        try {
            return extractor.extract();
        } finally {
            if (clean) {
                instances.values().forEach(TemporaryInstance::destroyObject);
                instancesReference.remove();
            }
        }
    }

}
