package name.wind.common.search;

import java.util.stream.Stream;

public interface SearchModel {

    Stream<Object> partsOf(Object object);

}
