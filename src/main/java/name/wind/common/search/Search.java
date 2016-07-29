package name.wind.common.search;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

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

        search(
            continuation,
            predicate,
            continuation.deriveContext(context, where),
            exposers.entrySet().stream()
                .filter(entry -> entry.getKey().test(where))
                .map(entry -> entry.getValue().apply(where))
                .flatMap(Collection::stream)
                .collect(
                    toList()));
    }

    private <C> void search(SearchContinuation<T, C> continuation, Predicate<T> predicate, C context, Collection<? extends T> where) throws SearchStoppedException {
        for (T item : where) {
            search(continuation, predicate, context, item);
        }
    }

}
