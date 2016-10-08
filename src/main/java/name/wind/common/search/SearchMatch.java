package name.wind.common.search;

public class SearchMatch {

    private final Object context;
    private final Object matchedObject;

    public SearchMatch(Object context, Object matchedObject) {
        this.context = context;
        this.matchedObject = matchedObject;
    }

    public Object context() {
        return context;
    }

    public Object matchedObject() {
        return matchedObject;
    }

}
