package name.wind.common.search;

public interface SearchContextBuilder<ContextType> {

    ContextType newContext(ContextType previousContext, Object object);

}
