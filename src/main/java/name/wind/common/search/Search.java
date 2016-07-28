package name.wind.common.search;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;

public class Search<T> {

    private final Map<Predicate<T>, Function<T, Iterable<T>>> exposers = new HashMap<>();

    public Search<T> addInteriorExposer(Predicate<T> matcher, Function<T, Iterable<T>> exposer) {
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
        if (where != null) {
            if (predicate.test(where)) {
                continuation.found(context, where);
            }

            search(
                continuation,
                predicate,
                continuation.deriveContext(context, where),
                exposers.entrySet().stream()
                    .filter(entry -> entry.getKey().test(where))
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .map(exposer -> exposer.apply(where))
                    .orElse(
                        emptyList()));
        }
    }

    private <C> void search(SearchContinuation<T, C> continuation, Predicate<T> predicate, C context, Iterable<T> where) throws SearchStoppedException {
        for (T item : where) {
            search(continuation, predicate, continuation.deriveContext(context, item), item);
        }
    }

}
