package name.wind.common.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SearchContinuation<ContextType> {

    private final List<SearchResult<ContextType>> searchResults = new ArrayList<>();

    public abstract ContextType deriveContext(ContextType context, Object found);

    public void clearSearchResults() {
        searchResults.clear();
    }

    public List<SearchResult<ContextType>> searchResults() {
        return Collections.unmodifiableList(searchResults);
    }

    public void found(ContextType context, Object found) throws SearchStoppedException {
        searchResults.add(
            new SearchResult<>(context, found));
    }

}
