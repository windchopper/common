package name.wind.common.search;

import java.util.stream.Stream;

public interface SearchDataModel {

    Stream<Object> partsOf(Object object);

}
