package name.wind.common.cdi.method;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.spi.AlterableContext;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TemporaryContext implements AlterableContext, Serializable {

    private final static ThreadLocal<Map<Contextual<?>, TemporaryInstance<?>>> instancesReference = new InheritableThreadLocal<>();

    /*
     *
     */

    @Override public <T> T get(Contextual<T> contextual) {
        return (T) Optional.ofNullable(Optional.ofNullable(instancesReference.get()).orElseThrow(ContextNotActiveException::new).get(contextual)).map(TemporaryInstance::getObject).orElse(null);
    }

    @Override public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
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

    @FunctionalInterface public interface Callable<T, E extends Exception> {
        T call() throws E;
    }

    public static <T, E extends Exception> T callWithinTemporaryScope(Callable<T, E> callable) throws E {
        Map<Contextual<?>, TemporaryInstance<?>> instances = instancesReference.get();
        boolean clean = instances == null;

        if (clean) {
            instancesReference.set(instances = new HashMap<>());
        }

        try {
            return callable.call();
        } finally {
            if (clean) {
                instances.values().forEach(TemporaryInstance::destroyObject);
                instancesReference.remove();
            }
        }
    }

}
