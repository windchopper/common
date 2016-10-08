package name.wind.common.search;

import java.util.function.Predicate;

public class Search<ContextType> {

    private final SearchContextExposer<ContextType> contextExposer;
    private final SearchContextBuilder<ContextType> contextBuilder;

    public Search(SearchContextExposer<ContextType> contextExposer, SearchContextBuilder<ContextType> contextBuilder) {
        this.contextExposer = contextExposer;
        this.contextBuilder = contextBuilder;
    }

    public SearchResult<ContextType> searchNext(ContextType context, Predicate<Object> matcher) throws SearchFinishedException {
        for (Object object : contextExposer.expose(context)) {
            if (matcher.test(object)) {
                return new SearchResult<>(context, object);
            } else {
                return searchNext(contextBuilder.newContext(context, object), matcher);
            }
        }

        throw new SearchFinishedException();
    }

}
