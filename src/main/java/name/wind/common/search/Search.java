package name.wind.common.search;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class Search {

    private final Function<Object, Collection<?>> exposer;

    public Search(Function<Object, Collection<?>> exposer) {
        this.exposer = exposer;
    }

    public <C> void search(SearchContinuation<C> continuation, Predicate<Object> matcher, Object where) {
        try {
            search(continuation, matcher, continuation.newContext(), where);
        } catch (SearchStoppedException ignored) {
        }
    }

    /*
     *
     */

    private <C> void search(SearchContinuation<C> continuation, Predicate<Object> matcher, C context, Object where) throws SearchStoppedException {
        if (where == null) {
            return;
        }

        if (matcher.test(where)) {
            continuation.found(context, where);
        }

        C derivedContext = continuation.deriveContext(context, where);
        exposer.apply(where).forEach(item -> search(continuation, matcher, derivedContext, item));
    }

}
