package name.wind.common.util;

public abstract class Value<T> {

    protected final T value;

    protected Value(T value) {
        this.value = value;
    }

}
