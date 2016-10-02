package name.wind.common.search;

import java.util.function.Predicate;

public class Search {

    private final SearchDataModel dataModel;

    public Search(SearchDataModel dataModel) {
        this.dataModel = dataModel;
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

            C newContext = continuation.newContext(context, where);

            dataModel.partsOf(where).forEach(
                part -> search(continuation, matcher, newContext, part));
        }
    }

}
