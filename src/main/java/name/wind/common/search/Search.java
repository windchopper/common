package name.wind.common.search;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class Search<T> {

    private final Map<Predicate<T>, Function<T, Collection<? extends T>>> exposers = new HashMap<>();

    public Search<T> addInteriorExposer(Predicate<T> matcher, Function<T, Collection<? extends T>> exposer) {
        exposers.put(matcher, exposer);
        return this;
    }

    public <C> void search(SearchContinuation<T, C> continuation, Predicate<T> predicate, T where) {
        try {
            search(continuation, predicate, continuation.newContext(), where);
        } catch (SearchStoppedException ignored) {
        }
    }

    /*
     *
     */

    private <C> void search(SearchContinuation<T, C> continuation, Predicate<T> predicate, C context, T where) throws SearchStoppedException {
        if (where == null) {
            return;
        }

        if (predicate.test(where)) {
            continuation.found(context, where);
        }

        C derivedContext = continuation.deriveContext(context, where);

        exposers.entrySet().stream()
            .filter(entry -> entry.getKey().test(where))
            .map(entry -> entry.getValue().apply(where))
            .flatMap(Collection::stream)
            .forEach(item -> search(continuation, predicate, derivedContext, item));
    }

}
