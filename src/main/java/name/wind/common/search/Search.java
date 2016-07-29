package name.wind.common.search;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Search {

    private final List<Function<Object, Collection<?>>> exposers;

    public Search(List<Function<Object, Collection<?>>> exposers) {
        this.exposers = exposers;
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
        if (where != null) {
            if (matcher.test(where)) {
                continuation.found(context, where);
            }

            C derivedContext = continuation.deriveContext(context, where);

            exposers.stream()
                .flatMap(exposer -> exposer.apply(where).stream())
                .forEach(item -> search(continuation, matcher, derivedContext, item));
        }
    }

}
