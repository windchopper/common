package name.wind.common.search;

public interface SearchContinuation<C> {

    C newContext();
    C deriveContext(C context, Object tail);

    void found(C context, Object found) throws SearchStoppedException;

}
