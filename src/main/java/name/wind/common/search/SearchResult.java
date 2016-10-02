package name.wind.common.search;

public class SearchResult<ContextType> {

    private final ContextType context;
    private final Object found;

    public SearchResult(ContextType context, Object found) {
        this.context = context;
        this.found = found;
    }

    public ContextType context() {
        return context;
    }

    public Object found() {
        return found;
    }

}
