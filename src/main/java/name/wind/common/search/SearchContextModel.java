package name.wind.common.search;

public interface SearchContextModel<ContextType> {

    Iterable<? extends Object> iterate(ContextType context);

}
