package name.wind.common.fx.spinner;

import java.text.Format;
import java.util.Comparator;

public interface SpinnableType<T> extends Comparator<T> {

    T add(T value1st, T value2nd);
    T subtract(T value1st, T value2nd);
    T steps(int steps);

    Format format();

}
