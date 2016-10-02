package name.wind.common.search;

public interface SearchContextExposer<ContextType> {

    Iterable<? extends Object> expose(ContextType context);

}
