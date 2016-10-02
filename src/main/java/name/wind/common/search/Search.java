package name.wind.common.search;

import java.util.function.Predicate;

public class Search {

    private final SearchModel model;

    public Search(SearchModel model) {
        this.model = model;
    }

    public <ContextType> void search(SearchContinuation<ContextType> continuation, Predicate<Object> matcher, ContextType context, Object object) {
        try {
            if (matcher.test(object))
                continuation.found(context, object);

            ContextType newContext = continuation.deriveContext(context, object);

            model.partsOf(object).forEach(
                part -> search(
                    continuation,
                    matcher,
                    newContext,
                    part));
        } catch (SearchStoppedException ignored) {
        }
    }

}
