package name.wind.common.fx.behavior;

@FunctionalInterface public interface Behavior<T> {

    void apply(T target);

}
