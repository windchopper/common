package name.wind.common.search;

public interface SearchContinuation<T, C> {

    C newContext();
    C deriveContext(C context, T tail);

    void found(C context, T found) throws SearchStoppedException;

    class SimpleContinuation implements SearchContinuation<Object, Object> {

        private Object searchResult;

        @Override public Object newContext() {
            return null;
        }

        @Override public Object deriveContext(Object context, Object tail) {
            return null;
        }

        @Override public void found(Object context, Object found) throws SearchStoppedException {
            searchResult = found;
            throw new SearchStoppedException();
        }

        public Object searchResult() {
            return searchResult;
        }

    }

}
