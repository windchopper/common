package name.wind.common.cdi.method;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import java.io.Serializable;

public class TemporaryInstance<T> implements Serializable {

    private final Contextual<T> contextual;
    private final CreationalContext<T> creationalContext;

    private transient T object;

    public TemporaryInstance(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        this.contextual = contextual;
        this.creationalContext = creationalContext;
    }

    public Contextual<T> getContextual() {
        return contextual;
    }

    public CreationalContext<T> getCreationalContext() {
        return creationalContext;
    }

    public T getObject() {
        if (object == null) {
            object = contextual.create(creationalContext);
        }

        return object;
    }

    public void destroyObject() {
        if (object != null) {
            contextual.destroy(object, creationalContext);
        }
    }

}
