package name.wind.common.search;

public interface SearchContextModel<ContextType> {

    Iterable<Object> iterate(ContextType context);

}
