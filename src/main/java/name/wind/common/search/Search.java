package name.wind.common.search;

public interface Search {

    SearchMatch search(Object where) throws SearchMatchNotFoundException;
    SearchMatch searchNext(SearchMatch previousMatch) throws SearchMatchNotFoundException;

}
