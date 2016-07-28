package name.wind.common.search;

public interface SearchContinuation<T, C> {

    C newContext();
    C deriveContext(C context, T tail);

    void found(C context, T found) throws SearchStoppedException;

}
