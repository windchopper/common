package name.wind.common.search;

public interface SearchContinuation<ContextType> {

    ContextType newContext();
    ContextType newContext(ContextType previousContext, Object lastFound);

    void found(ContextType context, Object found) throws SearchStoppedException;

}
