package name.wind.common.search;

import java.util.function.Predicate;

public class Search<ContextType> {

    private final SearchContextModel<ContextType> contextModel;

    public Search(SearchContextModel<ContextType> contextModel) {
        this.contextModel = contextModel;
    }

    public SearchResult<ContextType> searchNext(SearchContextBuilder<ContextType> contextBuilder,
                                                ContextType context,
                                                Predicate<Object> matcher) throws SearchFinishedException {
        for (Object object : contextModel.iterate(context)) {
            if (matcher.test(object)) {
                return new SearchResult<>(context, object);
            } else {
                return searchNext(contextBuilder, contextBuilder.newContext(context, object), matcher);
            }
        }

        throw new SearchFinishedException();
    }

}
